package sample.prac.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Component
public class DateUtil {


    /**
     * true if the first date day is after the second date day.
     * @param date1
     * @param date2
     * @return	boolean
     */
    public static boolean isAfterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isAfterDay(cal1, cal2);
    }

    /**
     * true if cal1 date is after cal2 date ignoring time
     * @param cal1
     * @param cal2
     * @return	boolean
     */
    public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * ####-##-## 형식으로 변환
     * @param value
     * @return	String
     */
    public static String getYmd(String value) {

        StringBuilder sb = new StringBuilder();
        String pattern = "####-##";

        if (!StringUtils.hasText(value)) {
            return null;
        } else if (value.length() < 6) {
            return value;
        } else if (value.length() > 6) {
            pattern = "####-##-##";
        }

        char[] values = value.toCharArray();
        char[] patterns = pattern.toCharArray();
        int valueLength = value.length();
        int patternLength = StringUtils.countOccurrencesOf(pattern, "#");

        if (valueLength > 0 && valueLength == patternLength) {
            int index = 0;
            for (char charcter : patterns) {
                sb.append(charcter == '#' ? values[index++] : charcter);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    public static String getYmd(Date date, String format) {
        LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    public static String getYmd(Date date) {
        LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static String getYmd(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    /**
     * 현재 날짜를 format 형식으로 리턴
     * @param value
     * @return	String
     */
    public static String getNowYmd(String format) {
        return getYmd(new Date(), format);
    }
    
	public static String getNowFormat(String dateFormat) {
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter formatter = null;
		String strDate = "";
		try {
			formatter = DateTimeFormatter.ofPattern(dateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		strDate = nowDate.format(formatter);
		return strDate;
	}


    /**
     * 현재 날짜 조회
     * @param value
     * @return	String
     */
    public static String getNowDateFormat(String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            result = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 날짜간 차이 조회
     * @param start		시작일자
     * @param end		종료일자
     * @return	int
     */
    public static int getDiffOfDate(String start, String end){
    	int dayCnt = 0;
    	
    	if(start == null || end == null || start.equals("") || end.equals(""))
    		return dayCnt;
    	
        try {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate	= formatter.parse(start);
            Date endDate	= formatter.parse(end);
            
            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - startDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            dayCnt	= (int) diffDays;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dayCnt;
    }

    /**
     * 날짜간 주말 수 조회
     * @param start		시작일자
     * @param end		종료일자
     * @return	int
     */
    public static int getWeekendCnt(String start, String end){
    	int weenkendCnt	= 0;
    	
    	if(start == null || end == null || start.equals("") || end.equals(""))
    		return weenkendCnt;
    	
    	try {
    		String dateFormat	= "yyyy-MM-dd";
	    	SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	        Date startDate	= formatter.parse(start);
	        Date endDate 	= formatter.parse(end);
    		        
	        while(isAfterDay(endDate, startDate)) {
	        	
	        	if(isWeekEnd(startDate))
	        		weenkendCnt++;
	        	
	        	startDate	= getAddDate(startDate, "yyyy-MM-dd", 1);
	        }
    	} catch (Exception e) {
            e.printStackTrace();
        }
        
        
    	return weenkendCnt;
    }

    /**
     * 날짜간 주말 수 조회
     * @param startDate		시작일자
     * @param endDate		종료일자
     * @return	int
     */
    public static int getWeekendDateCnt(Date startDate, Date endDate){
        int weenkendCnt	= 0;

        if(startDate == null || endDate == null || startDate.equals("") || endDate.equals(""))
            return weenkendCnt;

        try {


            while(isAfterDay(endDate, startDate)) {

                if(isWeekEnd(startDate))
                    weenkendCnt++;

                startDate	= getAddDate(startDate, "yyyy-MM-dd", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return weenkendCnt;
    }


    /**
     * 주말여부
     * @param day		조회일자
     * @return	boolean
     */
    private static boolean isWeekEnd(Date day) { 
    	Calendar cal = Calendar.getInstance() ;
        cal.setTime(day);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) ;
   
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY; 
    }
    
    /**
     * 날짜 더하기(일자로 더하기)
     * @param day		조회일자
     * @param fmt		출력형식 ex) yyyyMMdd
     * @param iDay		더할 날짜 ex) 1 : 1일후, -1 : 1일전
     * @return	Date
     */
	public static Date getAddDate(Date day, String fmt, int iDay) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(day);
		calendar.add(Calendar.DAY_OF_MONTH, iDay);

		return calendar.getTime();
	}

    /**
     * 날짜 더하기(시간으로 더하기)
     * @param day		조회일자
     * @param iHour		더할 시간 ex) 1 : 1시간, -1 : 1시간 전
     * @return	Date
     */
    public static Date getAddDateHour(Date day, int iHour) throws ParseException {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(day);
        calendar.add(Calendar.HOUR, iHour);

        return calendar.getTime();
    }
}
