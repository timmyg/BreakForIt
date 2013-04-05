package com.bfi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Tour;
import com.bfi.jdo.Venue;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class EventServlet extends HttpServlet {
	PersistenceManager pm = null;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		String mobileOverride = null;
		try {

			pm = PMF.get().getPersistenceManager(); // TODO memcache

			MemcacheService memcache = MemcacheServiceFactory
					.getMemcacheService();

			String timeframeCookieValue = "";
			String artistCookieValue = "";

			if (req.getCookies() != null) {
				for (Cookie c : req.getCookies()) {
					if (c.getName().equals("artist"))
						artistCookieValue = URLDecoder.decode(c.getValue(),
								"UTF-8");
					if (c.getName().equals("timeframe"))
						timeframeCookieValue = URLDecoder.decode(c.getValue(),
								"UTF-8");
				}
			}

			if (artistCookieValue.equals(""))
				artistCookieValue = "dmb";
			if (timeframeCookieValue.equals(""))
				timeframeCookieValue = "This Year";

			final Calendar today = Calendar.getInstance();
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();

			mobileOverride = req.getParameter("mob");
			String diffTodayDate = req.getParameter("date");
			if (diffTodayDate != null) {
				// 20120645
				today.set(Integer.valueOf(diffTodayDate.substring(0, 4)),
						Integer.valueOf(diffTodayDate.substring(4, 6)) - 1,
						Integer.valueOf(diffTodayDate.substring(6, 8)));
				startDate.set(Integer.valueOf(diffTodayDate.substring(0, 4)),
						Integer.valueOf(diffTodayDate.substring(4, 6)) - 1,
						Integer.valueOf(diffTodayDate.substring(6, 8)));
				endDate.set(Integer.valueOf(diffTodayDate.substring(0, 4)),
						Integer.valueOf(diffTodayDate.substring(4, 6)) - 1,
						Integer.valueOf(diffTodayDate.substring(6, 8)));
			}

			if (timeframeCookieValue.equals("This Week")) {
				startDate.add(Calendar.DATE, -7);
			} else if (timeframeCookieValue.equals("This Month")) {
				startDate.add(Calendar.DATE, -31);
			} else if (timeframeCookieValue.equals("This Year")) {
				startDate.add(Calendar.DATE, -365);
			} else if (timeframeCookieValue.equals("Recent Tour")) {
				Query q = pm.newQuery(Tour.class);
				q.setFilter("startDate <= todayParam");
				q.setOrdering("startDate desc");
				q.declareParameters("String todayParam");
				q.setRange(0, 1);
				List<Tour> results = (List<Tour>) q.execute(today.getTime());
				if (!results.isEmpty()) {
					for (Tour t : results) { // should only be one
						startDate.setTime(t.getStartDate());
						endDate.setTime(t.getEndDate());
						break;
					}
				}
			}

			// make sure it doesn't show future dates
			if (endDate.after(today)) {
				endDate = today;
			}

			// memcache test
			memcache.put("test", "testvalue123",
					Expiration.byDeltaSeconds(60 * 60 * 12 * 10)); // 120 hrs

			Collection<Event> filteredEvents = new ArrayList<Event>();
			boolean filteredEventsAreCached = memcache.contains("filteredEvents"
					+ startDate.toString() + endDate.toString());
			
			if (!filteredEventsAreCached) {
				
				System.out.println("getting filteredEvents from DB");
				filteredEvents = getEvents(startDate, endDate); // TODO memcache
				memcache.put("testArtist", filteredEvents.iterator().next()
						.getArtist(),
						Expiration.byDeltaSeconds(60 * 60 * 12 * 10)); // 120
																		// hrs
//				System.out.println("memcache artist completed");
//				memcache.put("testVenue", filteredEvents.iterator().next()
//						.getVenue(),
//						Expiration.byDeltaSeconds(60 * 60 * 12 * 10)); // 120
//																		// hrs
//				System.out.println("memcache venue completed");
//				
//				memcache.put(
//						"filteredEvent" + startDate.toString()
//								+ endDate.toString(), filteredEvents.iterator().next(),
//						Expiration.byDeltaSeconds(60 * 60 * 12)); // 12 hours
//				System.out.println("memcache event completed");
//				
//				ArrayList<Event> filteredEvents2 = new ArrayList<Event>(filteredEvents);
//				
//				memcache.put(
//						
//						"filteredEvents" + startDate.toString()
//								+ endDate.toString(), filteredEvents2,
//						Expiration.byDeltaSeconds(60 * 60 * 12)); // 12 hours
//				System.out.println("memcache filteredEvents completed");
			} else{
				System.out.println("getting filteredEvents from cache");
				filteredEvents= (Collection<Event>) memcache.get("filteredEvents");
			}

			if (filteredEvents == null || filteredEvents.size() == 0) {
				// if theres still no events, just return this years events
				Calendar startDateY = Calendar.getInstance();
				startDateY.add(Calendar.DATE, -365);
				Calendar endDateY = Calendar.getInstance();
				filteredEvents = getEvents(startDateY, endDateY);
			}

			ArrayList<Triplet<String, String, String>> allSearchTerms = new ArrayList<Triplet<String, String, String>>();
			boolean searchTermsAreCached = memcache.contains("searchTerms");
			if (!searchTermsAreCached) {
				System.out.println("getting searchTerms from DB");
				
				allSearchTerms = getAllSearchTerms();
				memcache.put("searchTerms", allSearchTerms,
						Expiration.byDeltaSeconds(60 * 60 * 48)); // 2 days TODO
																	// maybe
																	// longer?
			}
			else{
				allSearchTerms = (ArrayList<Triplet<String, String, String>>)memcache.get("searchTerms");
				System.out.println("getting searchTerms from cache");
			}

			Gson gson = new Gson();
			String searchJson = gson.toJson(allSearchTerms);

			// get artist terms for video title checking
			Artist artist = filteredEvents.iterator().next().getArtist();
			String artistTerms = artist.getName();
			if (artist.getSearchTerm1() != null) {
				artistTerms = artistTerms + "," + artist.getSearchTerm1();
			}
			if (artist.getSearchTerm2() != null) {
				artistTerms = artistTerms + "," + artist.getSearchTerm2();
			}

			List<Event> nextEvents = new ArrayList<Event>();
			boolean nextEventsAreCached = memcache.contains("nextEvents");
			if (!nextEventsAreCached) {
				System.out.println("getting nextEvents from DB");
				nextEvents = getNextEvents();
				ArrayList<Event> nextEvents2 = new ArrayList<Event>(nextEvents);
				memcache.put("nextEvents", nextEvents2,
						Expiration.byDeltaSeconds(60 * 60 * 12)); // 12 hours
			}else{
				System.out.println("getting nextEvents from cache");
			}
//			System.out.println("memcache nextEvents completed");

			Long days = getDaysUntilNextConcert(getNextEvents().get(0));

			req.setAttribute("artistTerms", artistTerms);
			req.setAttribute("currentTime", Calendar.getInstance().getTime()
					.toString());
			req.setAttribute("searchJson", searchJson);
			req.setAttribute("events", filteredEvents);
			// req.setAttribute("bgCount", bgCount);
			req.setAttribute("eventsPopulated", filteredEvents.size() > 0);
			req.setAttribute("daysUntil", days > 0 ? String.valueOf(days) : "0");
			req.setAttribute("daysUntilRemaining", days > 0);
			req.setAttribute("nextEvents", nextEvents);
		} finally {
			// pm.close();
		}
		try {
			if (MobileDeviceDetector.isMobile(req)
					|| (mobileOverride != null && mobileOverride.length() > 0)) {
				// servlet filter that intercepts jsp?
				req.getRequestDispatcher("/ArtistMobile.jsp")
						.forward(req, resp);
			} else {
				req.getRequestDispatcher("/Artist.jsp").forward(req, resp);
			}
		} catch (ServletException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Event> getNextEvents() {

		Query q = pm.newQuery(Event.class);
		q.setFilter("date >= todayParam");
		q.setOrdering("date asc");
		q.declareParameters("String todayParam");
		q.setRange(0, 5);
		return (List<Event>) q.execute(Calendar.getInstance().getTime());
	}

	private Long getDaysUntilNextConcert(Event next) {
		Calendar nextConcert = Calendar.getInstance();
		nextConcert.setTimeZone(TimeZone.getTimeZone("US/Central"));
		nextConcert.set(next.getDate().getYear(), next.getDate().getMonth(),
				next.getDate().getDate());
		nextConcert.set(Calendar.HOUR, 20);
		nextConcert.set(Calendar.MINUTE, 0);

		Long a = Calendar.getInstance().getTime().getTime();
		Long b = nextConcert.getTime().getTime();
		Long hours = (b - a) / (1000 * 60 * 60);
		return hours / 24;

	}

	@SuppressWarnings({ "unchecked" })
	private ArrayList<Triplet<String, String, String>> getAllSearchTerms() {
		ArrayList<Triplet<String, String, String>> allSearchTerms = new ArrayList<Triplet<String, String, String>>();
		// VENUE
		Query q = pm.newQuery(Venue.class);
		q.setOrdering("name asc");
		List<Venue> results = (List<Venue>) q.execute();
		if (!results.isEmpty()) {
			for (Venue v : results) {
				allSearchTerms.add(new Triplet<String, String, String>(v
						.getName(), String.valueOf(v.getId().getId()),
						SearchServlet.CATEGORY_VENUE));
			}
		}

		// YEAR
		// Query q = pm.newQuery("select id from " + Person.class.getName());
		q = pm.newQuery("select distinct year from " + Tour.class.getName());
		q.setOrdering("year desc");
		List<Long> yearResults = (List<Long>) q.execute();
		if (!yearResults.isEmpty()) {
			for (Long yr : yearResults) {
				allSearchTerms.add(new Triplet<String, String, String>(String
						.valueOf(yr), String.valueOf(yr),
						SearchServlet.CATEGORY_YEAR));
			}
		}

		// EVENT
		q = pm.newQuery(Event.class);
		q.setOrdering("date desc");
		List<Event> eventResults = (List<Event>) q.execute();
		if (!results.isEmpty()) {
			for (Event e : eventResults) {
				// allSearchTerms.add(new Triplet<String, String,
				// String>(getDate1(e.getDate()),
				// String.valueOf(e.getId().getId()),
				// SearchServlet.CATEGORY_DATE));
				allSearchTerms.add(new Triplet<String, String, String>(
						getDate2(e.getDate()), String
								.valueOf(e.getId().getId()),
						SearchServlet.CATEGORY_DATE));
				// {"val0":"09/13/2012","val1":"20120913","val2":"d"}
				// {"val0":"September 13, 2012","val1":"20120913","val2":"d"}
			}
		}

		// TOUR
		q = pm.newQuery(Tour.class);
		q.setOrdering("startDate desc");
		List<Tour> tourResults = (List<Tour>) q.execute();
		if (!results.isEmpty()) {
			for (Tour t : tourResults) {
				Calendar c = Calendar.getInstance();
				c.setTime(t.getStartDate());
				String yr = Integer.toString(c.get(Calendar.YEAR));
				String nonUSLocation = "";
				if (!t.getLocation().equals("US")) {
					nonUSLocation = " " + t.getLocation() + " ";
				}
				allSearchTerms.add(new Triplet<String, String, String>(t
						.getName() + nonUSLocation + " Tour " + yr, String
						.valueOf(t.getId().getId()),
						SearchServlet.CATEGORY_TOUR));
				if (t.getAlternateName1() != null) {
					allSearchTerms.add(new Triplet<String, String, String>(t
							.getName()
							+ " "
							+ t.getAlternateName1()
							+ " "
							+ yr
							+ " " + t.getLocation(), String.valueOf(t.getId()
							.getId()), SearchServlet.CATEGORY_TOUR));
				}
				if (t.getAlternateName2() != null) {
					allSearchTerms.add(new Triplet<String, String, String>(t
							.getName()
							+ " "
							+ t.getAlternateName2()
							+ " "
							+ yr
							+ " " + t.getLocation(), String.valueOf(t.getId()
							.getId()), SearchServlet.CATEGORY_TOUR));
				}
			}
		}

		for (Pair<String, String> t : getRelativeTimes()) {
			allSearchTerms.add(new Triplet<String, String, String>(t
					.getValue0(), t.getValue1(),
					SearchServlet.CATEGORY_TIMEFRAME));
		}

		return allSearchTerms;
	}

	String TODAY = "0";
	String YESTERDAY = "1";
	String THIS_WEEK = "7";
	String LAST_WEEK = "L7";
	String THIS_MONTH = "30";
	String LAST_MONTH = "L30";
	String THIS_YEAR = "365";
	String LAST_YEAR = "L365";

	ArrayList<Pair<String, String>> getRelativeTimes() {
		ArrayList<Pair<String, String>> a = new ArrayList<Pair<String, String>>();
		a.add(p("Today", TODAY));
		a.add(p("Tonight", TODAY));
		a.add(p("Last Night", YESTERDAY));
		a.add(p("Yesterday", YESTERDAY));
		a.add(p("This Week", THIS_WEEK));
		a.add(p("Last Week", LAST_WEEK));
		a.add(p("This Month", THIS_MONTH));
		a.add(p("Last Month", LAST_MONTH));
		a.add(p("This Year", THIS_YEAR));
		a.add(p("Last Year", LAST_YEAR));
		// a.add(p("All", ALL));
		return a;
	}

	Pair<String, String> p(String s1, Object s2) {
		return new Pair<String, String>(s1, String.valueOf(s2));
	}

	public String getDateString(Date d) {
		String DATE_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance(); // today
		c1.setTime(d);
		return sdf.format(c1.getTime());
	}

	public String getDate1(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		String m = Integer.toString(c.get(Calendar.MONTH) + 1);
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		String y = Integer.toString(c.get(Calendar.YEAR));
		if (m.length() == 1) {
			m = "0" + m;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}

		return m + "/" + d + "/" + y;
	}

	public String getDate2(Date d) {
		return DateFormat.getDateInstance(DateFormat.LONG).format(d);
	}

	ArrayList<Pair<String, String>> arrL(String[] names, Long venueID) {
		ArrayList<Pair<String, String>> namesList = new ArrayList<Pair<String, String>>();
		if (names != null && names.length > 0) {
			for (String name : names) {
				namesList.add(new Pair<String, String>(name, String
						.valueOf(venueID)));
			}
		}
		return namesList;
	}

	Calendar zeroTime(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
		c.set(Calendar.MINUTE, 0); // set minute in hour
		c.set(Calendar.SECOND, 0); // set second in minute
		c.set(Calendar.MILLISECOND, 0); // set millis in second
		return c;
	}

	Collection<Event> getEvents(Calendar start, Calendar end) {
		Calendar startDate = zeroTime(start);
		Calendar endDate = zeroTime(end);
		// filter
		Query q = pm.newQuery(Event.class);
		q.setFilter("date >= startParam && date <= endParam");
		q.setOrdering("date desc");
		q.declareParameters("String startParam,String endParam");
		return (Collection<Event>) q.execute(startDate.getTime(),
				endDate.getTime());
	}

	int getBackgroundCount() {
		return new File("images/bg").listFiles().length;
	}

}
