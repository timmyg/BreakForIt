package com.bfi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.google.gdata.data.youtube.VideoEntry;

@SuppressWarnings("serial")
public class UpdateAllCountsTaskServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		Collection<Event> updateCollection = new ArrayList<Event>();
		PersistenceManager pm = null;
		final Logger log = Logger.getLogger(UpdateAllCountsTaskServlet.class.getName());
		
		try {
		// /updateallvideocounts?lowIndex=0&highIndex=21
			String lowIndex = req.getParameter("lowIndex");
			String highIndex = req.getParameter("highIndex");
			
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Event.class);
			q.setOrdering("date asc");
			q.setRange(Long.valueOf(lowIndex),Long.valueOf(highIndex)); //setRange(long fromIncl, long toExcl)
			
			@SuppressWarnings("unchecked")
			Collection<Event> events = (Collection<Event>) q.execute();
			updateCollection = new ArrayList<Event>();
			for(Event e: events){
				YouTubeUtils ytu = new YouTubeUtils();
				//TODO could probably just get video count here
				List<VideoEntry> cleanedList = ytu.getVideos(e);
				e.setVideoCount(String.valueOf(cleanedList.size()));
				updateCollection.add(e);
			}
		} finally {
			pm.makePersistentAll(updateCollection);
			pm.close();
		}
		log.info("Successfully updated "+String.valueOf(updateCollection.size())+" event video counts");
		resp.getWriter().println("Successfully updated "+String.valueOf(updateCollection.size())+" event video counts");
	}
}
