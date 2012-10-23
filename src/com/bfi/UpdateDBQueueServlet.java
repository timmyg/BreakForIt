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
public class UpdateDBQueueServlet extends HttpServlet {
	PersistenceManager pm = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Queue queue = QueueFactory.getQueue("updatedb-queue");
		queue.add(TaskOptions.Builder.withUrl("/deletestuff"));
		queue.add(TaskOptions.Builder.withUrl("/createstuff"));
		queue.add(TaskOptions.Builder.withUrl("/createevents"));

		resp.getWriter().println("Successfully deleted and created stuff");
	}
}
