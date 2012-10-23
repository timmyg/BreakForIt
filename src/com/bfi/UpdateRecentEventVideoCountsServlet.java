package com.bfi;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;
import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Tour;
import com.bfi.jdo.Venue;

import org.datanucleus.util.StringUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("serial")
public class UpdateRecentEventVideoCountsServlet extends HttpServlet {
	PersistenceManager pm = null;
	Collection<Event> updateCollection = new ArrayList<Event>();


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String developerKey = "AI39si6xzNDr4sK-84zSW9v2Yc9HvVE5cC6WhDtmMn0jO7RH0496fjRpw-E3iyH8m4iElENPGZsDm0ntVQzSU9QbRItB37k_2g"; // TODO
		
		try {
			YouTubeService service = new YouTubeService("BFI", developerKey);
			List<VideoEntry> videos = null;
			List<VideoEntry> allVideos = null;
			
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Event.class);
			q.setOrdering("date desc");
			Collection<Event> events = (Collection<Event>) q.execute();
			updateCollection = new ArrayList<Event>();
			for(Event e: events){
				YouTubeUtils ytu = new YouTubeUtils();
				List<VideoEntry> cleanedList = ytu.getVideos(e);
				e.setVideoCount(String.valueOf(cleanedList.size()));
				updateCollection.add(e);
				if(updateCollection.size() == 10){
					pm.makePersistentAll(updateCollection);
					updateCollection = new ArrayList<Event>();
					break;
				}
			}
		} finally {
			pm.close();
		}
		resp.getWriter().println("Successfully updated "+String.valueOf(updateCollection.size())+" event video counts");
	}
}
