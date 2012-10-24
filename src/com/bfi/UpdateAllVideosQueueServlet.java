package com.bfi;


import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class UpdateAllVideosQueueServlet extends HttpServlet {
	PersistenceManager pm = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//http://www.rominirani.com/2009/11/24/episode-10-using-the-task-queue-service/
		Queue queue = QueueFactory.getQueue("updateall-queue");
		queue.add(TaskOptions.Builder.withUrl("/updateallvideocounts").param("emailid",""));

//		resp.getWriter().println("Successfully created artists, tours, and venues");
	}
}
