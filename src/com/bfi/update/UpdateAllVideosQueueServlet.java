package com.bfi.update;


import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class UpdateAllVideosQueueServlet extends HttpServlet {
	PersistenceManager pm = null;
	
	public static Long GROUP_SIZE = 20L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			
			
		
		//example: http://www.rominirani.com/2009/11/24/episode-10-using-the-task-queue-service/
		
		//should prolly just get count here, not load all objects into collection
		pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("select COUNT(e) from " + Event.class.getName() + " e");
		long numEvents = (Long) q.execute();//getSingleResult(); 
		long groupSize = GROUP_SIZE;
		long numGroups =  (long)Math.ceil(numEvents/groupSize) + 1;
		long lowIndex = 0L;
		long highIndex = groupSize -1;
		
		Queue queue = QueueFactory.getQueue("updateall-queue");
		for(int i=0; i<numGroups; ){
			queue.add(TaskOptions.Builder.withUrl("/updateallvideocounts")
					.param("lowIndex",String.valueOf(lowIndex)).param("highIndex",String.valueOf(highIndex)));
			lowIndex = lowIndex+groupSize;
			highIndex = highIndex+groupSize;
			if(highIndex >= numEvents){
				highIndex = numEvents - 1;
				break;
			}
		}
		
	}
	

}
