package com.bfi;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import javax.jdo.Query;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Venue;

@SuppressWarnings("serial")
public class VideoServlet extends HttpServlet {
	Artist dmb = null;
	Venue alpine = null;
	Venue gi = null;
	Venue bader = null;
	public final static Long MAX_RESULTS_API_MAX = 50L;
	public final static int INITIAL_RESULTS = 10;
	public final static int MORE_RESULTS = 5;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PersistenceManager pm = null;
		
		System.out.println(req.getQueryString());
		resp.setContentType("text/html");

		System.out.println("in VideoSerlet");

		boolean isMore = (req.getParameter("action").equals("more"));
		String eventID = req.getParameter("eventID");
		String moreClicked = req.getParameter("moreClicked");
		String videoCountString = req.getParameter("videoCount");
		int videoCount = 0;
		if(videoCountString != null){
			videoCount = Integer.valueOf(videoCountString);
		}
		Long more = 0L;
		if(moreClicked != null){
			more = Long.valueOf(moreClicked);
		}
		
		List<VideoEntry> cleanedList = null;
		List<VideoEntry> finalList = new ArrayList<VideoEntry>();

		try {
			pm = PMF.get().getPersistenceManager();
			
			//load event and update videoCount
			Event e = null;
			e = pm.getObjectById(Event.class, Long.valueOf(eventID));
				
			//get videos for UI
			YouTubeUtils ytu = new YouTubeUtils();
			cleanedList = ytu.getVideos(e);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally{
			pm.close();
		}

		// more Videos
		try{
			if (isMore) {
					int vidsRemaining = cleanedList.size() - videoCount;
					if(vidsRemaining >= MORE_RESULTS){//>=5 remaining
						finalList = cleanedList.subList(videoCount, videoCount+MORE_RESULTS);
					}else if (vidsRemaining < MORE_RESULTS){ //<5
						finalList = cleanedList.subList(videoCount, videoCount + vidsRemaining);
					} //else = 0 so dont assign finalList
				
			// open accordion for first time	
			}else{
				if(cleanedList.size() >= INITIAL_RESULTS){
					finalList = cleanedList.subList(0, INITIAL_RESULTS);
				}else{
					finalList = cleanedList.subList(0, cleanedList.size());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			finalList = new ArrayList<VideoEntry>();
		}

//		req.setAttribute("moreClicked", String.valueOf(more));
		req.setAttribute("videos", finalList);
		req.setAttribute("videosPopulated", finalList.size() > 0);
		System.out.println("Videos returned: "
				+ String.valueOf(finalList.size()));
		try {
			req.getRequestDispatcher("/AjaxVideos.jsp").forward(req, resp);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
}
