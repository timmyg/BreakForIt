package com.bfi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Tour;
import com.bfi.jdo.Venue;

@SuppressWarnings("serial")
public class DeleteStuffServlet extends HttpServlet {
	
	PersistenceManager pm = null;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			Collection<Object> collection = new ArrayList<Object>();
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Venue.class);
			List<Venue> venues = (List<Venue>) q.execute();
			for(Venue v: venues){
				collection.add(v);
			}
			q = pm.newQuery(Event.class);
			List<Event> events = (List<Event>) q.execute();
			for(Event e: events){
				collection.add(e);
			}
			q = pm.newQuery(Artist.class);
			List<Artist> artists = (List<Artist>) q.execute();
			for(Artist a: artists){
				collection.add(a);
			}
			q = pm.newQuery(Tour.class);
			List<Tour> tours = (List<Tour>) q.execute();
			for(Tour t: tours){
				collection.add(t);
			}
			pm.deletePersistentAll(collection);
		} finally {
			pm.close();
		}
		resp.getWriter().println("Successfully deleted er-thing");
	}
}