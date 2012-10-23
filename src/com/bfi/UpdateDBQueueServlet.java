package com.bfi;


import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
