/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.changxiang.vod.module.img_select.crop;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.LogUtils;


/*
 * Modified from original in AOSP.
 */
public class CropImageActivity extends MonitoredActivity {

    private static final boolean IN_MEMORY_CROP = Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1;
    private static final int SIZE_DEFAULT = 2048;
    private static final int SIZE_LIMIT = 4096;

    private final Handler handler = new Handler();

    private int aspectX;
    private int aspectY;

    // Output image size 100
    private int maxX; 
    private int outSize; 
    private int maxY;
    private int exifRotation;

    private int outSizeX;
    private int outSizeY;

    private Uri sourceUri;
    private Uri saveUri;

    private boolean isSaving;

    private int sampleSize;
    private RotateBitmap rotateBitmap;
    private CropImageView imageView;
    private HighlightView cropView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.crop__activity_crop);
        initViews();

        setupFromIntent();
        if (rotateBitmap == null) {
            finish();
            return;
        }
        startCrop();
    }

    private void initViews() {
        imageView = (CropImageView) findViewById(R.id.crop_image);
        imageView.context = this;
        imageView.setRecycler(new ImageViewTouchBase.Recycler() {
            @Override
            public void recycle(Bitmap b) {
                b.recycle();
                System.gc();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSaveClicked();
            }
        });
    }

    private void setupFromIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            aspectX = extras.getInt(Crop.Extra.ASPECT_X);
            aspectY = extras.getInt(Crop.Extra.ASPECT_Y);
            maxX = extras.getInt(Crop.Extra.MAX_X);
            maxY = extras.getInt(Crop.Extra.MAX_Y);
            outSize = extras.getInt(Crop.Extra.OUTPUT_SIZE);
            outSizeX = extras.getInt(Crop.Extra.OUTPUT_SIZE);
            outSizeY = extras.getInt(Crop.Extra.OUTPUT_SIZE);
            //TODO
//            outSizeX = extras.getInt("1600");
//            outSizeY = extras.getInt("900");
            saveUri = extras.getParcelable(MediaStore.EXTRA_OUTPUT);
        }

        sourceUri = intent.getData();
        if (sourceUri != null) {
            exifRotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(getContentResolver(), sourceUri));

            InputStream is = null;
            try {
                sampleSize = calculateBitmapSampleSize(sourceUri);
                is = getContentResolver().openInputStream(sourceUri);
                Options option = new Options();
                option.inSampleSize = sampleSize;
                rotateBitmap = new RotateBitmap(BitmapFactory.decodeStream(is, null, option), exifRotation);
            } catch (IOException e) {
            	LogUtils.log("CROP","Error reading image: " + e.getMessage());
                setResultException(e);
            } catch (OutOfMemoryError e) {
                LogUtils.log("CROP","OOM reading image: " + e.getMessage());
                setResultException(e);
            } finally {
                CropUtil.closeSilently(is);
            }
        }
    }

    private int calculateBitmapSampleSize(Uri bitmapUri) throws IOException {
        InputStream is = null;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            is = getContentResolver().openInputStream(bitmapUri);
            BitmapFactory.decodeStream(is, null, options); // Just get image size
        } finally {
            CropUtil.closeSilently(is);
        }

        int maxSize = getMaxImageSize();
        int sampleSize = 1;
        while (options.outHeight / sampleSize > maxSize || options.outWidth / sampleSize > maxSize) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    private int getMaxImageSize() {
        int textureLimit = getMaxTextureSize();
        if (textureLimit == 0) {
            return SIZE_DEFAULT;
        } else {
            return Math.min(textureLimit, SIZE_LIMIT);
        }
    }

    private int getMaxTextureSize() {
        // The OpenGL texture size is the maximum size that can be drawn in an ImageView
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
        return maxSize[0];
    }

    private void startCrop() {
        if (isFinishing()) {
            return;
        }
        imageView.setImageRotateBitmapResetBase(rotateBitmap, true);
        CropUtil.startBackgroundJob(this, null, getResources().getString(R.string.crop_wait),
                new Runnable() {
                    public void run() {
                        final CountDownLatch latch = new CountDownLatch(1);
                        handler.post(new Runnable() {
                            public void run() {
                                if (imageView.getScale() == 1F) {
                                    imageView.center(true, true);
                                }
                                latch.countDown();
                            }
                        });
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        new Cropper().crop();
                    }
                }, handler
        );
    }

    private class Cropper {

        private void makeDefault() {
            if (rotateBitmap == null) {
                return;
            }

            HighlightView hv = new HighlightView(imageView);
            final int width = rotateBitmap.getWidth();
            final int height = rotateBitmap.getHeight();

            Rect imageRect = new Rect(0, 0, width, height);

            // Make the default size about 4/5 of the width or height
            int cropWidth = Math.min(width, height) * 4 / 5;
            @SuppressWarnings("SuspiciousNameCombination")
            int cropHeight = cropWidth;

            if (aspectX != 0 && aspectY != 0) {
                if (aspectX > aspectY) {
                    cropHeight = cropWidth * aspectY / aspectX;
                } else {
                    cropWidth = cropHeight * aspectX / aspectY;
                }
            }

            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;

            RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
            hv.setup(imageView.getUnrotatedMatrix(), imageRect, cropRect, aspectX != 0 && aspectY != 0);
            imageView.add(hv);
        }

        public void crop() {
            handler.post(new Runnable() {
                public void run() {
                    makeDefault();
                    imageView.invalidate();
                    if (imageView.highlightViews.size() == 1) {
                        cropView = imageView.highlightViews.get(0);
                        cropView.setFocus(true);
                    }
                }
            });
        }
    }

    /*
     * TODO
     * This should use the decode/crop/encode single step API so that the whole
     * (possibly large) Bitmap doesn't need to be read into memory
     */
    private void onSaveClicked() {
        if (cropView == null || isSaving) {
            return;
        }
        isSaving = true;

        Bitmap croppedImage = null;
        Rect r = cropView.getScaledCropRect(sampleSize);
        int width = r.width();
        int height = r.height();

        int outWidth = width, outHeight = height;
        if (maxX > 0 && maxY > 0 && (width > maxX || height > maxY)) {
            float ratio = (float) width / (float) height;
            if ((float) maxX / (float) maxY > ratio) {
                outHeight = maxY;
                outWidth = (int) ((float) maxY * ratio + .5f);
            } else {
                outWidth = maxX;
                outHeight = (int) ((float) maxX / ratio + .5f);
            }
        }

        if (IN_MEMORY_CROP && rotateBitmap != null) {
            croppedImage = inMemoryCrop(rotateBitmap, croppedImage, r, width, height, outWidth, outHeight);
            if (croppedImage != null) {
                imageView.setImageBitmapResetBase(croppedImage, true);
                imageView.center(true, true);
                imageView.highlightViews.clear();
            }
        } else {
            try {
                croppedImage = decodeRegionCrop(croppedImage, r);
            } catch (IllegalArgumentException e) {
                setResultException(e);
                finish();
                return;
            }

            if (croppedImage != null) {
                imageView.setImageRotateBitmapResetBase(new RotateBitmap(croppedImage, exifRotation), true);
                imageView.center(true, true);
                imageView.highlightViews.clear();
            }
        }
        saveImage(croppedImage);
    }

    private void saveImage(Bitmap croppedImage) {
        if (croppedImage != null) {
            final Bitmap b = croppedImage;
            CropUtil.startBackgroundJob(this, null, getResources().getString(R.string.crop__saving),
                    new Runnable() {
                        public void run() {
                        	byte bit[]=getBitmapByteArray(b);
                        	double simple=(b.getWidth()+0.5)/outSize;
                        	Options opt=new Options();
                        	opt.inSampleSize=(int)simple;
                        	Bitmap bitmap= BitmapFactory.decodeByteArray(bit, 0, bit.length, opt);
                        	bitmap=zoomImg(bitmap,outSizeX,outSizeY);
                            saveOutput(bitmap);
                        }
                    }, handler
            );
        } else {
            finish();
        }
    }
    
 // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth , int newHeight){
       // 获得图片的宽高
       int width = bm.getWidth();
       int height = bm.getHeight();
       // 计算缩放比例
       float scaleWidth = ((float) newWidth) / width;
       float scaleHeight = ((float) newHeight) / height;
       // 取得想要缩放的matrix参数
       Matrix matrix = new Matrix();
//       matrix.postScale(scaleWidth, scaleHeight);
        //TODO
       matrix.postScale(1, 1);
       // 得到新的图片
       Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
    
    private byte[] getBitmapByteArray(Bitmap bmp){
    	ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        bmp.compress(CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
        bmp.recycle();//自由选择是否进行回收
        byte[] result = output.toByteArray();//转换成功了
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    


    @TargetApi(10)
    private Bitmap decodeRegionCrop(Bitmap croppedImage, Rect rect) {
        // Release memory now
        clearImageView();

        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(sourceUri);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
            final int width = decoder.getWidth();
            final int height = decoder.getHeight();

            if (exifRotation != 0) {
                // Adjust crop area to account for image rotation
                Matrix matrix = new Matrix();
                matrix.setRotate(-exifRotation);

                RectF adjusted = new RectF();
                matrix.mapRect(adjusted, new RectF(rect));

                // Adjust to account for origin at 0,0
                adjusted.offset(adjusted.left < 0 ? width : 0, adjusted.top < 0 ? height : 0);
                rect = new Rect((int) adjusted.left, (int) adjusted.top, (int) adjusted.right, (int) adjusted.bottom);
            }

            try {
                croppedImage = decoder.decodeRegion(rect, new Options());

            } catch (IllegalArgumentException e) {
                // Rethrow with some extra information
                throw new IllegalArgumentException("Rectangle " + rect + " is outside of the image ("
                        + width + "," + height + "," + exifRotation + ")", e);
            }

        } catch (IOException e) {
            LogUtils.log("CROP","Error cropping image: " + e.getMessage());
            finish();
        } catch (OutOfMemoryError e) {
            LogUtils.log("CROP","OOM cropping image: " + e.getMessage());
            setResultException(e);
        } finally {
            CropUtil.closeSilently(is);
        }
        return croppedImage;
    }

    private Bitmap inMemoryCrop(RotateBitmap rotateBitmap, Bitmap croppedImage, Rect r,
                                int width, int height, int outWidth, int outHeight) {
        // In-memory crop means potential OOM errors,
        // but we have no choice as we can't selectively decode a bitmap with this API level
        System.gc();

        try {
            croppedImage = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(croppedImage);
            RectF dstRect = new RectF(0, 0, width, height);

            Matrix m = new Matrix();
            m.setRectToRect(new RectF(r), dstRect, Matrix.ScaleToFit.FILL);
            m.preConcat(rotateBitmap.getRotateMatrix());
            canvas.drawBitmap(rotateBitmap.getBitmap(), m, null);
        } catch (OutOfMemoryError e) {
            LogUtils.log("CROP","OOM cropping image: " + e.getMessage());
            setResultException(e);
            System.gc();
        }

        // Release bitmap memory as soon as possible
        clearImageView();
        return croppedImage;
    }

    private void clearImageView() {
        imageView.clear();
        if (rotateBitmap != null) {
            rotateBitmap.recycle();
        }
        System.gc();
    }

    private void saveOutput(Bitmap croppedImage) {
    	
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(saveUri);
               
                if (outputStream != null) {
                    croppedImage.compress( CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {
                setResultException(e);
                LogUtils.log("CROP","Cannot open file: " + saveUri);
            } finally {
                CropUtil.closeSilently(outputStream);
            }

            if (!IN_MEMORY_CROP) {
                // In-memory crop negates the rotation
                CropUtil.copyExifRotation(
                        CropUtil.getFromMediaUri(getContentResolver(), sourceUri),
                        CropUtil.getFromMediaUri(getContentResolver(), saveUri)
                );
            }

            setResultUri(saveUri);//没有问题了
        }

        final Bitmap b = croppedImage;
        handler.post(new Runnable() {
            public void run() {
                imageView.clear();
                b.recycle();
                finish();
            }
        });

//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rotateBitmap != null) {
            rotateBitmap.recycle();
        }
    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    public boolean isSaving() {
        return isSaving;
    }

    private void setResultUri(Uri uri) {
        setResult(RESULT_OK, new Intent().putExtra(MediaStore.EXTRA_OUTPUT, uri));

    }

    private void setResultException(Throwable throwable) {
        setResult(Crop.RESULT_ERROR, new Intent().putExtra(Crop.Extra.ERROR, throwable));
    }

}

