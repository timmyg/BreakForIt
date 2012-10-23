package com.bfi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Tour;
import com.bfi.jdo.Venue;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {
    Artist dmb = null;
    Venue alpine = null;
    Venue gi = null;
    Venue bader = null;
    static final String CATEGORY_YEAR = "y";
    static final String CATEGORY_DATE = "d";
    static final String CATEGORY_VENUE = "v";
    static final String CATEGORY_TOUR = "t";
    static final String CATEGORY_TIMEFRAME = "tf";
    PersistenceManager pm = null;
    Collection<Event> filteredEvents = null;

    @SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        System.out.println("in SearchServlet");
        
        boolean isRangeDts = false;

        String value = req.getParameter("value");
        String category = req.getParameter("category");
        final Calendar thisMoment = Calendar.getInstance();

        Calendar exactDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try{
	        pm = PMF.get().getPersistenceManager();
	
	        if(category.equals(CATEGORY_YEAR)){//year, range of dates
	        	isRangeDts = true;
	            Calendar calBOY = Calendar.getInstance();
	            calBOY.set(Calendar.YEAR, Integer.valueOf(value));
	            calBOY.set(Calendar.MONTH, Calendar.JANUARY);
	            calBOY.set(Calendar.DAY_OF_MONTH, 1);
	            startDate = calBOY;
	
	            if(thisMoment.get(Calendar.YEAR)==Integer.valueOf(value)){
	                //dont do anything
	            }else{
	                Calendar calEOY = Calendar.getInstance();
	                calEOY.set(Calendar.YEAR, Integer.valueOf(value));
	                calEOY.set(Calendar.MONTH, Calendar.DECEMBER);
	                calEOY.set(Calendar.DAY_OF_MONTH, 31);
	                endDate = calEOY;
	            }
	        }else if(category.equals(CATEGORY_TOUR)){//tour, range of dates
	        	isRangeDts = true;
	        	Tour t = pm.getObjectById(Tour.class, Long.valueOf(value));
	        	Calendar c = Calendar.getInstance();
	        	c.setTime(t.getStartDate());
	        	startDate = c;
	        	Calendar c2 = Calendar.getInstance();
	        	c2.setTime(t.getEndDate());
	        	endDate = c2;
	        }else if(category.equals(CATEGORY_TIMEFRAME)){//timeframe, exact or range of dates
	            String TODAY = "0";
	            String YESTERDAY = "1";
	            String THIS_WEEK = "7";
	            String LAST_WEEK = "L7";
	            String THIS_MONTH = "30";
	            String LAST_MONTH = "L30";
	            String THIS_YEAR = "365";
	            String LAST_YEAR = "L365";
	            String ALL = "A";
		        if(value.equals(TODAY)){
		        	exactDate = thisMoment;
		        }else if(value.equals(YESTERDAY)){
		        	exactDate.add(Calendar.DATE, -1);
		        }else if(value.equals(THIS_WEEK)){
		        	isRangeDts = true;
		        	//endDate ok
		        	startDate.add(Calendar.DATE, -7);
		        }else if(value.equals(LAST_WEEK)){
		        	isRangeDts = true;
		        	endDate.add(Calendar.DATE, -7);
		        	startDate.add(Calendar.DATE, -14);
		        }else if(value.equals(THIS_MONTH)){
		        	isRangeDts = true;
		//        	endDate ok
		        	startDate.add(Calendar.DATE, -31);
		        }else if(value.equals(LAST_MONTH)){
		        	isRangeDts = true;
		        	endDate.add(Calendar.DATE, -31);
		        	startDate.add(Calendar.DATE, -62);
		        }else if(value.equals(THIS_YEAR)){
		        	isRangeDts = true;
		        	//endDate ok
		        	startDate.set(Calendar.MONTH, Calendar.JANUARY);
		        	startDate.set(Calendar.DATE, 1);
		        }else if(value.equals(LAST_YEAR)){
		        	isRangeDts = true;
		        	endDate.set(Calendar.MONTH, Calendar.DECEMBER);
		        	endDate.set(Calendar.DATE, 31);
		        	endDate.set(Calendar.YEAR, thisMoment.get(Calendar.YEAR) -1);
		        	startDate.set(Calendar.MONTH, Calendar.JANUARY);
		        	startDate.set(Calendar.DATE, 1);
		        	startDate.set(Calendar.YEAR, thisMoment.get(Calendar.YEAR) -1);
		        }else if(value.equals(ALL)){
		        }
		    }
		      
	        //zero dates
	        exactDate = zeroTime(exactDate);
	        startDate = zeroTime(startDate);
	        endDate = zeroTime(endDate);
	
	      //filter events
	        filteredEvents = new ArrayList<Event>();
	        
	        if(isRangeDts){
		        Query q = pm.newQuery(Event.class);
				q.setFilter("date >= startParam && date <= endParam");
				q.setOrdering("date desc");
				q.declareParameters("String startParam,String endParam");
				filteredEvents = (Collection<Event>) q.execute(startDate.getTime(), endDate.getTime());
	        }else if(category.equals(CATEGORY_VENUE)){
	        	Query q = pm.newQuery(Event.class);
				q.setFilter("venueID == venueIdVal");
				q.setOrdering("date desc");
				q.declareParameters("String venueIdVal");
				filteredEvents = (Collection<Event>) q.execute(value);
	        }else if(category.equals(CATEGORY_DATE)){
	        	Event e = pm.getObjectById(Event.class, Long.valueOf(value));
				filteredEvents.add(e);
	        }
        }finally{
        	pm.close();
        }


        req.setAttribute("events", filteredEvents);
        req.setAttribute("eventsPopulated", filteredEvents.size() > 0);
        try {
            req.getRequestDispatcher("/FilteredAccordian.jsp").forward(req,resp);
        } catch (ServletException e1) {
            e1.printStackTrace();
        }

    }

    boolean isCorrectDate(String videoTitle, Date eventDate){
        //this only works for 3 nighters
        boolean correctDate = true;
        Calendar dayBefore = Calendar.getInstance();
        Calendar dayAfter = Calendar.getInstance();
        dayBefore.setTime(eventDate);
        dayAfter.setTime(eventDate);
        dayBefore.add(Calendar.DATE, -1);
        dayAfter.add(Calendar.DATE, 1);

        //day before
        String yrB = Integer.toString(dayBefore.get(Calendar.YEAR));
        String yrShortB = yrB.substring(2,3);
        String monthB = Integer.toString(dayBefore.get(Calendar.MONTH));
        String monthNameB = new SimpleDateFormat("MMMM").format(dayBefore);
        String dateB = Integer.toString(dayBefore.get(Calendar.DAY_OF_MONTH)-1);
        //date after
        String yrA = Integer.toString(dayBefore.get(Calendar.YEAR));
        String yrShortA = yrA.substring(2,3);
        String monthA = Integer.toString(dayBefore.get(Calendar.MONTH));
        String monthNameA = new SimpleDateFormat("MMMM").format(dayBefore);
        String dateA = Integer.toString(dayBefore.get(Calendar.DAY_OF_MONTH)-1);

        String[] possibleDates = new String[]{
                //day before
                monthB + " " + dateB + " " + yrB,
                monthB + " " + dateB + " " + yrShortB,
                monthB + "/" + dateB + "/" + yrB,
                monthB + "/" + dateB + "/" + yrShortB,
                monthB + "." + dateB + "." + yrB,
                monthB + "." + dateB + "." + yrShortB,
                monthB + "-" + dateB + "-" + yrB,
                monthB + "-" + dateB + "-" + yrShortB,
                monthNameB + " " + dateB + " " + yrB,
                monthNameB + " " + dateB + " " + yrShortB,
                monthNameB + " " + dateB + ", " + yrB,
                monthNameB + " " + dateB + ", " + yrShortB,
                //days after
                monthA + " " + dateA + " " + yrA,
                monthA + " " + dateA + " " + yrShortA,
                monthA + "/" + dateA + "/" + yrA,
                monthA + "/" + dateA + "/" + yrShortA,
                monthA + "." + dateA + "." + yrA,
                monthA + "." + dateA + "." + yrShortA,
                monthA + "-" + dateA + "-" + yrA,
                monthA + "-" + dateA + "-" + yrShortA,
                monthNameA + " " + dateA + " " + yrA,
                monthNameA + " " + dateA + " " + yrShortA,
                monthNameA + " " + dateA + ", " + yrA,
                monthNameA + " " + dateA + ", " + yrShortA,
        };

        for(String beforeOrAfterDate: possibleDates){
            if(videoTitle.contains(beforeOrAfterDate)){
                correctDate = false;
                break;
            }
        }
        return correctDate;

    }

    Date date(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m - 1);
        c.set(Calendar.DAY_OF_MONTH, d);
        return c.getTime();
    }

    Calendar zeroTime(Calendar c){
        c.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        c.set(Calendar.MINUTE, 0);                 // set minute in hour
        c.set(Calendar.SECOND, 0);                 // set second in minute
        c.set(Calendar.MILLISECOND, 0);            // set millis in second
        return c;				            
    }
    
}
