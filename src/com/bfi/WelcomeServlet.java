package com.bfi;

import com.google.gson.Gson;
import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.Tour;
import com.bfi.jdo.Venue;

import org.datanucleus.util.StringUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;

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

@SuppressWarnings("serial")
public class WelcomeServlet extends HttpServlet {
	Artist dmb = null;
	Venue alpine = null;
	Venue gi = null;
	Venue bader = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		try {
			req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
