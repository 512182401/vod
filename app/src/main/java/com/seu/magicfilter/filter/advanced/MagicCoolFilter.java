package com.seu.magicfilter.filter.advanced;

import android.opengl.GLES20;

import com.changxiang.vod.R;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGlUtils;

import java.nio.ByteBuffer;

public class MagicCoolFilter extends GPUImageFilter {
	private int[] mToneCurveTexture = {-1};
	private int mToneCurveTextureUniformLocation;
	  
	public MagicCoolFilter(){
		super(NO_FILTER_VERTEX_SHADER, OpenGlUtils.readShaderFromRawResource(R.raw.cool));
	}
	
	public void onDestroy(){
		super.onDestroy();
	    GLES20.glDeleteTextures(1, mToneCurveTexture, 0);
	    this.mToneCurveTexture[0] = -1;
	}
	  
	protected void onDrawArraysAfter(){
		if (this.mToneCurveTexture[0] != -1){
			GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	    }
	}
	  
	protected void onDrawArraysPre(){
		if (this.mToneCurveTexture[0] != -1){
			GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mToneCurveTexture[0]);
			GLES20.glUniform1i(this.mToneCurveTextureUniformLocation, 3);
	    }
	}
	  
	public void onInit(){
		super.onInit();
	    mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(mGLProgId, "curve");
	}
	  
	public void onInitialized(){
		super.onInitialized();
	    runOnDraw(new Runnable(){
		    public void run(){
			    GLES20.glGenTextures(1, mToneCurveTexture, 0);
			    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mToneCurveTexture[0]);
			    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
		                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
		                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
		                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
		                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		        byte[] arrayOfByte = new byte[2048];
		        int[] arrayOfInt1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9, 10, 11, 12, 12, 13, 14, 15, 16, 16, 17, 18, 19, 20, 20, 21, 22, 23, 24, 24, 25, 26, 27, 28, 28, 29, 30, 31, 32, 33, 33, 34, 35, 36, 37, 38, 39, 39, 40, 41, 42, 43, 44, 45, 46, 47, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 82, 83, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95, 97, 98, 99, 100, 102, 103, 104, 105, 107, 108, 109, 111, 112, 113, 115, 116, 117, 119, 120, 121, 123, 124, 126, 127, 128, 130, 131, 133, 134, 136, 137, 139, 140, 142, 143, 145, 146, 148, 149, 151, 152, 154, 155, 157, 158, 160, 161, 163, 165, 166, 168, 169, 171, 173, 174, 176, 177, 179, 181, 182, 184, 185, 187, 189, 190, 192, 194, 195, 197, 199, 200, 202, 204, 205, 207, 209, 210, 212, 214, 216, 217, 219, 221, 222, 224, 226, 228, 229, 231, 233, 234, 236, 238, 240, 241, 243, 245, 246, 248, 250, 252, 253, 255 };
		        int[] arrayOfInt2 = { 0, 1, 2, 3, 4, 5, 6, 7, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 250, 251, 252, 253, 254, 255 };
		        int[] arrayOfInt3 = { 0, 3, 6, 9, 11, 14, 17, 20, 23, 26, 28, 31, 34, 37, 40, 43, 45, 48, 51, 54, 57, 59, 62, 65, 68, 70, 73, 76, 79, 81, 84, 87, 89, 92, 95, 97, 100, 102, 105, 108, 110, 113, 115, 118, 120, 123, 125, 128, 130, 133, 135, 137, 140, 142, 144, 147, 149, 151, 153, 156, 158, 160, 162, 164, 166, 168, 171, 173, 175, 177, 179, 180, 182, 184, 186, 188, 190, 191, 193, 195, 197, 198, 200, 201, 203, 205, 206, 207, 209, 210, 212, 213, 214, 216, 217, 218, 219, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 234, 235, 236, 237, 237, 238, 239, 240, 240, 241, 242, 242, 243, 243, 244, 244, 245, 245, 246, 246, 247, 247, 248, 248, 248, 249, 249, 249, 250, 250, 250, 251, 251, 251, 251, 252, 252, 252, 252, 252, 253, 253, 253, 253, 253, 253, 253, 254, 254, 254, 254, 254, 254, 254, 254, 254, 254, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255 };
		        int[] arrayOfInt4 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 13, 17, 21, 24, 32, 36, 39, 46, 50, 53, 56, 62, 65, 68, 73, 75, 78, 80, 85, 87, 88, 92, 94, 95, 96, 99, 100, 102, 104, 106, 107, 109, 110, 112, 113, 115, 116, 117, 120, 121, 122, 123, 125, 126, 127, 129, 130, 131, 132, 134, 135, 136, 138, 139, 140, 141, 142, 143, 144, 146, 147, 148, 149, 150, 151, 152, 154, 154, 155, 156, 158, 159, 159, 161, 162, 163, 163, 165, 166, 166, 168, 169, 169, 170, 172, 172, 173, 175, 175, 176, 177, 178, 179, 180, 181, 182, 182, 184, 184, 185, 186, 187, 188, 188, 190, 190, 191, 192, 193, 194, 194, 196, 196, 197, 197, 199, 199, 200, 201, 202, 202, 203, 204, 205, 205, 207, 207, 208, 208, 210, 210, 211, 212, 213, 213, 214, 215, 215, 216, 217, 218, 218, 219, 220, 221, 221, 222, 223, 223, 224, 225, 226, 226, 227, 228, 228, 229, 230, 230, 231, 232, 232, 233, 234, 235, 235, 236, 237, 237, 238, 239, 239, 240, 240, 241, 242, 242, 243, 244, 244, 245, 246, 246, 247, 248, 248, 249, 249, 250, 251, 251, 252, 253, 253, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255 };
		        for (int i = 0; i < 256; i++){
		          arrayOfByte[(i * 4)] = ((byte)arrayOfInt1[i]);
		          arrayOfByte[(1 + i * 4)] = ((byte)arrayOfInt2[i]);
		          arrayOfByte[(2 + i * 4)] = ((byte)arrayOfInt3[i]);
		          arrayOfByte[(3 + i * 4)] = ((byte)arrayOfInt4[i]);
		        }
		        int[] arrayOfInt5 = { 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 28, 28, 29, 29, 30, 29, 31, 31, 31, 31, 32, 32, 33, 33, 34, 34, 34, 34, 35, 35, 36, 36, 37, 37, 37, 38, 38, 39, 39, 39, 40, 40, 40, 41, 42, 42, 43, 43, 44, 44, 45, 45, 45, 46, 47, 47, 48, 48, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 59, 60, 60, 61, 62, 63, 63, 64, 65, 66, 67, 68, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, 101, 103, 104, 105, 107, 108, 110, 111, 113, 115, 116, 118, 119, 120, 122, 123, 125, 127, 128, 130, 132, 134, 135, 137, 139, 141, 143, 144, 146, 148, 150, 152, 154, 156, 158, 160, 163, 165, 167, 169, 171, 173, 175, 178, 180, 182, 185, 187, 189, 192, 194, 197, 199, 201, 204, 206, 209, 211, 214, 216, 219, 221, 224, 226, 229, 232, 234, 236, 239, 241, 245, 247, 250, 252, 255 };
		        for (int j = 0; j < 256; j++){
		          arrayOfByte[(1024 + j * 4)] = ((byte)arrayOfInt5[j]);
		          arrayOfByte[(1 + (1024 + j * 4))] = ((byte)arrayOfInt5[j]);
		          arrayOfByte[(2 + (1024 + j * 4))] = ((byte)arrayOfInt5[j]);
		          arrayOfByte[(3 + (1024 + j * 4))] = -1;
		        }
		        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 256, 2, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ByteBuffer.wrap(arrayOfByte));
		    }
	    });
	}
}
