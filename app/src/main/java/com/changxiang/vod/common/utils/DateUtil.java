package com.changxiang.vod.common.utils;

import android.text.format.Time;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 对于时间的帮助类
 *
 * @author admin
 */
public class DateUtil {

    public static String getDateByFormat(Date date, String reg) {
        SimpleDateFormat dateFormat = new SimpleDateFormat( reg );

        return dateFormat.format( date );
    }

    public static String getCurrentDate() {
        return getDateByFormat( new Date(), "yyyy年MM月dd日 HH:mm" );
    }

    public static String getCurrentDate1() {
        return getDateByFormat( new Date(), "yyyy-MM-dd HH:mm:ss" );
    }

    public static String getDateToStr() {
        return getDateByFormat( new Date(), "yyyy-MM-dd" );
    }

    public static String getDate(String reg) {
        return getDateByFormat( new Date(), reg );
    }

    public static Date getDate(String reg, String datestr) {
        try {
            return new SimpleDateFormat( reg ).parse( datestr );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 只是比较到日
     *
     * @param date
     * @param date2
     * @return
     */
    public static int compareDate(Date date, Date date2) {
        Calendar cl = Calendar.getInstance();
        cl.setTime( date );
        Calendar cl2 = Calendar.getInstance();
        cl2.setTime( date2 );
        if (cl.get( Calendar.YEAR ) > cl2.get( Calendar.YEAR )) {
            return 1;
        } else if (cl.get( Calendar.YEAR ) < cl2.get( Calendar.YEAR )) {
            return -1;
        } else {
            if (cl.get( Calendar.MONTH ) > cl2.get( Calendar.MONTH )) {
                return 1;
            } else if (cl.get( Calendar.MONTH ) < cl2.get( Calendar.MONTH )) {
                return -1;
            } else {
                if (cl.get( Calendar.DAY_OF_MONTH ) > cl2
                        .get( Calendar.DAY_OF_MONTH )) {
                    return 1;
                } else if (cl.get( Calendar.DAY_OF_MONTH ) < cl2
                        .get( Calendar.DAY_OF_MONTH )) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    //日期减去用户的生日来得到用户的年龄有多少年月日
    public static String getQuChangData(String birthday) {
//        birthday =  "2016-11-07 08:55";
        String y, m, d;
        SimpleDateFormat formatBirthday = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault() );
        int yearBirthday = 0, monthBirthday = 0, dayBirthday = 0,hourBirthday = 0,minuteBirthday = 0,secondBirthday = 0;
        try {
            Date dateBirthday = formatBirthday.parse( birthday );
//            Date dateBirthday = formatBirthday.parse( "2016-11-07 08:55" );
            Calendar calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTime( dateBirthday );
            yearBirthday = calendarBirthday.get( Calendar.YEAR );
            monthBirthday = calendarBirthday.get( Calendar.MONTH )+1;
            dayBirthday = calendarBirthday.get( Calendar.DAY_OF_MONTH );
            hourBirthday = calendarBirthday.get( Calendar.HOUR_OF_DAY );
            minuteBirthday = calendarBirthday.get( Calendar.MINUTE);
            secondBirthday = calendarBirthday.get( Calendar.SECOND);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatMoment = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault() );
        Calendar calendarMoment = Calendar.getInstance();
        int yearMoment = 0, monthMoment = 0, dayMoment = 0,hourMoment = 0,minuteMoment = 0,secondMoment = 0;;
        try {
            Time time = new Time();
            time.setToNow();
            yearMoment = time.year ;
            monthMoment = time.month+1 ;
            dayMoment =time.monthDay ;
            hourMoment = time.hour;
            minuteMoment = time.minute;
            secondMoment = time.second;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        LogUtils.sysout( "**********************************************************" );
//        LogUtils.sysout( "+++++yearMoment ="+yearMoment+"monthMoment="+monthMoment+"dayMoment="+dayMoment+"hourMoment="+hourMoment +"minuteMoment="+minuteMoment);
//        LogUtils.sysout( "+++++yearBirthday ="+yearBirthday+"monthBirthday="+monthBirthday+"dayBirthday="+dayBirthday+"hourBirthday="+hourBirthday +"minuteBirthday="+minuteBirthday);

        if (yearBirthday > yearMoment || yearBirthday == yearMoment && monthBirthday > monthMoment || yearBirthday == yearMoment && monthBirthday == monthMoment && dayBirthday > dayMoment) {

//            y = "??年";
//            m = "??月";
//            d = "??日";
//            LogUtils.sysout( "999999999999999999" );
//            LogUtils.sysout( "yearBirthday > yearMoment" );
            return birthday;
//            return "??年-??月-??日";
        } else {
//            LogUtils.sysout( "yearMoment ="+yearMoment+"monthMoment="+monthMoment+"dayMoment="+dayMoment+"hourMoment="+hourMoment +"minuteMoment="+minuteMoment);
//            LogUtils.sysout( "yearBirthday ="+yearBirthday+"monthBirthday="+monthBirthday+"dayBirthday="+dayBirthday+"hourBirthday="+hourBirthday +"minuteBirthday="+minuteBirthday);
            int yearAge = yearMoment - yearBirthday;
            int monthAge = monthMoment - monthBirthday;
            int dayAge = dayMoment - dayBirthday;
            int hourAge = hourMoment - hourBirthday;
            int minuteAge = minuteMoment - minuteBirthday;
            //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减
            //按照减法原理，先year相减，
            if (yearAge > 0) {//不是同一年
                return birthday;
            } else if (monthAge > 0) {//是同一年，但不是同一个月
                return (monthBirthday<10? "0"+monthBirthday:monthBirthday ) + "月" + (dayBirthday<10? "0"+dayBirthday:dayBirthday ) + "日 " + (hourBirthday<10? "0"+hourBirthday:hourBirthday ) + ":" + (minuteBirthday<10? "0"+minuteBirthday:minuteBirthday ) + ":" + (secondBirthday<10? "0"+secondBirthday:secondBirthday );
            } else if (dayAge > 0) {//不是同一天
                if(dayAge == 1){
                    return "昨天" + (hourBirthday<10? "0"+hourBirthday:hourBirthday )+ ":" + (minuteBirthday<10? "0"+minuteBirthday:minuteBirthday ) + ":" + (secondBirthday<10? "0"+secondBirthday:secondBirthday );
                }else{
                    return (monthBirthday<10? "0"+monthBirthday:monthBirthday )+"月"+(dayBirthday<10? "0"+dayBirthday:dayBirthday ) + "日 " + (hourBirthday<10? "0"+hourBirthday:hourBirthday ) + ":" + (minuteBirthday<10? "0"+minuteBirthday:minuteBirthday ) + ":" + (secondBirthday<10? "0"+secondBirthday:secondBirthday );
                }

            } else if (hourAge > 0) {//不是同一个小时内
//                hourBirthday = 0,minuteBirthday = 0,secondBirthday = 0;
                return "今天" + (hourBirthday<10? "0"+hourBirthday:hourBirthday ) + ":" + (minuteBirthday<10? "0"+minuteBirthday:minuteBirthday ) + ":" + (secondBirthday<10? "0"+secondBirthday:secondBirthday );
            } else if (Math.abs(minuteAge) > 0) {//不是同意分钟内
//                return minuteBirthday+ ":"+secondBirthday;
                return Math.abs(minuteAge)+"分钟前";
            } else {//错误
//                return birthday;
                return "刚刚";
            }

        }
    }

    //日期减去用户的生日来得到用户的年龄有多少年月日
    private void getAge(String birthday, String time, TextView tvAgeYear, TextView tvAgeMonth, TextView tvAgeDay) {
        SimpleDateFormat formatBirthday = new SimpleDateFormat( "yyyy-MM-dd", Locale.getDefault() );
        int yearBirthday = 0, monthBirthday = 0, dayBirthday = 0;
        try {
            Date dateBirthday = formatBirthday.parse( birthday );
            Calendar calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTime( dateBirthday );
            yearBirthday = calendarBirthday.get( Calendar.YEAR );
            monthBirthday = calendarBirthday.get( Calendar.MONTH );
            dayBirthday = calendarBirthday.get( Calendar.DAY_OF_MONTH );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatMoment = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault() );
        Calendar calendarMoment = Calendar.getInstance();
        int yearMoment = 0, monthMoment = 0, dayMoment = 0;
        try {
            Date dateMoment = formatMoment.parse( time );
            calendarMoment.setTime( dateMoment );
            yearMoment = calendarMoment.get( Calendar.YEAR );
            monthMoment = calendarMoment.get( Calendar.MONTH );
            dayMoment = calendarMoment.get( Calendar.DAY_OF_MONTH );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (yearBirthday > yearMoment || yearBirthday == yearMoment && monthBirthday > monthMoment || yearBirthday == yearMoment && monthBirthday == monthMoment && dayBirthday > dayMoment) {
            tvAgeYear.setText( "??年" );
            tvAgeMonth.setText( "??月" );
            tvAgeDay.setText( "??天" );
        } else {
            int yearAge = yearMoment - yearBirthday;
            int monthAge = monthMoment - monthBirthday;
            int dayAge = dayMoment - dayBirthday;
            //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减
            if (dayAge < 0) {
                monthAge -= 1;
                calendarMoment.add( Calendar.MONTH, -1 );//得到上一个月，用来得到上个月的天数
                dayAge = dayAge + calendarMoment.getActualMaximum( Calendar.DAY_OF_MONTH );
            }
            if (monthAge < 0) {
                monthAge = (monthAge + 12) % 12;
                yearAge--;
            }
            String year, month, day;
            if (yearAge < 10) {
                year = "0" + yearAge + "年";
            } else {
                year = yearAge + "年";
            }
            if (monthAge < 10) {
                month = "0" + monthAge + "月";
            } else {
                month = monthAge + "月";
            }
            if (dayAge < 10) {
                day = "0" + dayAge + "天";
            } else {
                day = dayAge + "天";
            }
            tvAgeYear.setText( year );
            tvAgeMonth.setText( month );
            tvAgeDay.setText( day );
        }
    }

}
