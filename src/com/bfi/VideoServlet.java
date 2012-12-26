package com.bfi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Venue;
import com.google.gdata.data.youtube.VideoEntry;

@SuppressWarnings("serial")
public class VideoServlet extends HttpServlet {
	Artist dmb = null;
	Venue alpine = null;
	Venue gi = null;
	Venue bader = null;
	public final static Long MAX_RESULTS_API_MAX = 50L;
	

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		int INITIAL_RESULTS = 8;
		int MORE_RESULTS = 4;
		
		PersistenceManager pm = null;
		
		System.out.println(req.getQueryString());
		resp.setContentType("text/html");

		System.out.println("in VideoSerlet");

		boolean isMore = (req.getParameter("action").equals("more"));
		String eventID = req.getParameter("eventID");
		int screenWidth = 1366;
		if(req.getParameter("screenWidth") !=null){
			screenWidth = Integer.valueOf(req.getParameter("screenWidth"));
		}
		String videoCountString = req.getParameter("videoCount");
		int videoCount = 0;
		if(screenWidth > 1500){
			INITIAL_RESULTS = 12;
			MORE_RESULTS = 8;
		}
		
		if(videoCountString != null){
			videoCount = Integer.valueOf(videoCountString);
		}

		
		List<VideoEntry> cleanedList = null;
		List<VideoEntry> finalList = new ArrayList<VideoEntry>();

		try {
			pm = PMF.get().getPersistenceManager();
			
			//load event and update videoCount
			Event e = null;
			e = pm.getObjectById(Event.class, Long.valueOf(eventID));
			if(e.getClicks() != null){
				e.setClicks(e.getClicks() + 1);
			}else{
				e.setClicks(1L);
			}
				
			//get videos for UI
			YouTubeUtils ytu = new YouTubeUtils();
			cleanedList = ytu.getVideos(e);
			
		} catch (Exception e1) {
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

//		Pair<String,String> videoDimensions = getVideoWidth(Long.valueOf(screenWidth));
		req.setAttribute("videos", finalList);
//		req.setAttribute("videoHeight", videoDimensions.getValue0());
//		req.setAttribute("videoWidth", videoDimensions.getValue1());
		req.setAttribute("videosPopulated", finalList.size() > 0);
		System.out.println("Videos returned: "
				+ String.valueOf(finalList.size()));
		try {
			if(MobileDeviceDetector.isMobile(req)){
				req.getRequestDispatcher("/AjaxVideosMobile.jsp").forward(req, resp);
			}else{
				req.getRequestDispatcher("/AjaxVideos.jsp").forward(req, resp);
			}
		} catch (ServletException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
}
