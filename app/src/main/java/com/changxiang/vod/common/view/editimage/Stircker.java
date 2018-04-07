//package com.changxiang.vod.common.view.editimage;
//
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 用户实体类
// * @author admin
// *方式一：Code-->Generate
//
//方式二：通过快捷键Alt+Insert
// */
//public class Stircker {
//
//
//    private List<DttzBean> dttz;
//    private List<TzBean> tz;
//    private List<ZmBean> zm;
//
//
//
//    public List<DttzBean> getDttz() {
//        return dttz;
//    }
//
//    public void setDttz(List<DttzBean> dttz) {
//        this.dttz = dttz;
//    }
//
//    public List<TzBean> getTz() {
//        return tz;
//    }
//
//    public void setTz(List<TzBean> tz) {
//        this.tz = tz;
//    }
//
//    public List<ZmBean> getZm() {
//        return zm;
//    }
//
//    public void setZm(List<ZmBean> zm) {
//        this.zm = zm;
//    }
//
//    public static class DttzBean {
//        /**
//         * name : 動態貼紙
//         * list : []
//         * type : 1
//         */
//
//        private String name;
//        private String type;
//        private List<?> list;
//
//
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public List<?> getList() {
//            return list;
//        }
//
//        public void setList(List<?> list) {
//            this.list = list;
//        }
//    }
//
//    public static class TzBean {
//        /**
//         * name : 卡通
//         * list : [{"id":"1","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/01.png","y2":"","x2":"","x1":""},{"id":"12","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/02.png","y2":"","x2":"","x1":""},{"id":"13","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/03.png","y2":"","x2":"","x1":""},{"id":"14","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/04.png","y2":"","x2":"","x1":""},{"id":"15","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/05.png","y2":"","x2":"","x1":""},{"id":"16","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/06.png","y2":"","x2":"","x1":""},{"id":"17","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/07.png","y2":"","x2":"","x1":""},{"id":"18","y1":"","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/08.png","y2":"","x2":"","x1":""}]
//         * type : 1
//         */
//
//        private String name;
//        private String type;
//        private List<ListBean> list;
//
//
//
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public static class ListBean {
//            /**
//             * id : 1
//             * y1 :
//             * img : http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/tz/katong/01.png
//             * y2 :
//             * x2 :
//             * x1 :
//             */
//
//            private String id;
//            private String y1;
//            private String img;
//            private String y2;
//            private String x2;
//            private String x1;
//
//            public static ListBean objectFromData(String str) {
//
//                return new Gson().fromJson(str, ListBean.class);
//            }
//
//            public static ListBean objectFromData(String str, String key) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(str);
//
//                    return new Gson().fromJson(jsonObject.getString(key), ListBean.class);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            public static List<ListBean> arrayListBeanFromData(String str) {
//
//                Type listType = new TypeToken<ArrayList<ListBean>>() {
//                }.getType();
//
//                return new Gson().fromJson(str, listType);
//            }
//
//            public static List<ListBean> arrayListBeanFromData(String str, String key) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(str);
//                    Type listType = new TypeToken<ArrayList<ListBean>>() {
//                    }.getType();
//
//                    return new Gson().fromJson(jsonObject.getString(key), listType);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return new ArrayList();
//
//
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getY1() {
//                return y1;
//            }
//
//            public void setY1(String y1) {
//                this.y1 = y1;
//            }
//
//            public String getImg() {
//                return img;
//            }
//
//            public void setImg(String img) {
//                this.img = img;
//            }
//
//            public String getY2() {
//                return y2;
//            }
//
//            public void setY2(String y2) {
//                this.y2 = y2;
//            }
//
//            public String getX2() {
//                return x2;
//            }
//
//            public void setX2(String x2) {
//                this.x2 = x2;
//            }
//
//            public String getX1() {
//                return x1;
//            }
//
//            public void setX1(String x1) {
//                this.x1 = x1;
//            }
//        }
//    }
//
//    public static class ZmBean {
//        /**
//         * name : 字幕
//         * list : [{"id":"4","y1":"97","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/01.png","y2":"133","x2":"134","x1":"87"},{"id":"5","y1":"72","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/11.png","y2":"102","x2":"136","x1":"82"},{"id":"41","y1":"67","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/02.png","y2":"101","x2":"105","x1":"58"},{"id":"42","y1":"60","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/03.png","y2":"91","x2":"107","x1":"59"},{"id":"43","y1":"74","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/04.png","y2":"105","x2":"114","x1":"56"},{"id":"44","y1":"47","img":"http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/05.png","y2":"82","x2":"123","x1":"51"}]
//         * type : 4
//         */
//
//        private String name;
//        private String type;
//        private List<ListBeanX> list;
//
//        public static ZmBean objectFromData(String str) {
//
//            return new Gson().fromJson(str, ZmBean.class);
//        }
//
//        public static ZmBean objectFromData(String str, String key) {
//
//            try {
//                JSONObject jsonObject = new JSONObject(str);
//
//                return new Gson().fromJson(jsonObject.getString(key), ZmBean.class);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        public static List<ZmBean> arrayZmBeanFromData(String str) {
//
//            Type listType = new TypeToken<ArrayList<ZmBean>>() {
//            }.getType();
//
//            return new Gson().fromJson(str, listType);
//        }
//
//        public static List<ZmBean> arrayZmBeanFromData(String str, String key) {
//
//            try {
//                JSONObject jsonObject = new JSONObject(str);
//                Type listType = new TypeToken<ArrayList<ZmBean>>() {
//                }.getType();
//
//                return new Gson().fromJson(jsonObject.getString(key), listType);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return new ArrayList();
//
//
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public List<ListBeanX> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBeanX> list) {
//            this.list = list;
//        }
//
//        public static class ListBeanX {
//            /**
//             * id : 4
//             * y1 : 97
//             * img : http://test.app.srv.quchangkeji.com:8083/tsAPI/files/tz/zm/01.png
//             * y2 : 133
//             * x2 : 134
//             * x1 : 87
//             */
//
//            private String id;
//            private String y1;
//            private String img;
//            private String y2;
//            private String x2;
//            private String x1;
//
//            public static ListBeanX objectFromData(String str) {
//
//                return new Gson().fromJson(str, ListBeanX.class);
//            }
//
//            public static ListBeanX objectFromData(String str, String key) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(str);
//
//                    return new Gson().fromJson(jsonObject.getString(key), ListBeanX.class);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            public static List<ListBeanX> arrayListBeanXFromData(String str) {
//
//                Type listType = new TypeToken<ArrayList<ListBeanX>>() {
//                }.getType();
//
//                return new Gson().fromJson(str, listType);
//            }
//
//            public static List<ListBeanX> arrayListBeanXFromData(String str, String key) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(str);
//                    Type listType = new TypeToken<ArrayList<ListBeanX>>() {
//                    }.getType();
//
//                    return new Gson().fromJson(jsonObject.getString(key), listType);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return new ArrayList();
//
//
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getY1() {
//                return y1;
//            }
//
//            public void setY1(String y1) {
//                this.y1 = y1;
//            }
//
//            public String getImg() {
//                return img;
//            }
//
//            public void setImg(String img) {
//                this.img = img;
//            }
//
//            public String getY2() {
//                return y2;
//            }
//
//            public void setY2(String y2) {
//                this.y2 = y2;
//            }
//
//            public String getX2() {
//                return x2;
//            }
//
//            public void setX2(String x2) {
//                this.x2 = x2;
//            }
//
//            public String getX1() {
//                return x1;
//            }
//
//            public void setX1(String x1) {
//                this.x1 = x1;
//            }
//        }
//    }
//}
