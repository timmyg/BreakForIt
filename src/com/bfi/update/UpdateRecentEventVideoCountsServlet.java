package com.bfi.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.YouTubeUtils;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.google.gdata.data.youtube.VideoEntry;

@SuppressWarnings("serial")
public class UpdateRecentEventVideoCountsServlet extends HttpServlet {
	PersistenceManager pm = null;
	Collection<Event> updateCollection = new ArrayList<Event>();


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		try {
			Calendar tomorrow = Calendar.getInstance();
			tomorrow.add(Calendar.DATE,1);
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Event.class);
			q.setFilter("date <= tomorrow");
			q.setOrdering("date desc");
			q.declareParameters("String tomorrow");
			@SuppressWarnings("unchecked")
			Collection<Event> events = (Collection<Event>) q.execute( tomorrow.getTime());
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
		
		//hit breakforit.com so it doesnt get cold
		URL yahoo = new URL("http://www.breakforit.com/");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
        System.err.println("breakforit.com hit");
		
		
		resp.getWriter().println("Successfully updated "+String.valueOf(updateCollection.size())+" event video counts");
	}
}
