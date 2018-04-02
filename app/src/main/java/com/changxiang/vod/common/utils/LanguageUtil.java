//package com.changxiang.vod.common.utils;
//
//import java.util.Locale;
//
//import static com.changxiang.VodMedia.common.utils.SharedPrefManager.getInstance;
//
//
///**
// * 判断当前使用的语言
// * Created by 15976 on 2017/6/9.
// */
//
//public class LanguageUtil {
//
//    public static String getLanguage() {
//
//        String language = getInstance().getCacheApiLanguage();
//        if (language.equals("language")) {
//            String country = Locale.getDefault().getCountry();
//
//            switch (country){
//                case "US"://英文
//                    language = "3";
//                    break;
//                case "TW"://中文繁体
//                    language = "2";
//                    break;
//                case "CN"://中文简体
//                    language = "1";
//                    break;
//                default:
//                    language = "1";
//                    break;
//            }
//        }else {
//            switch (language){
//                case "en_US":
//                    language = "3";
//                    break;
//                case "zh_TW":
//                    language = "2";
//                    break;
//                case "zh_CN":
//                    language = "1";
//                    break;
//                default:
//                    language = "1";
//                    break;
//            }
//        }
//        return language;
//    }
//}
