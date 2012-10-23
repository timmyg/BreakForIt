package com.bfi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Venue;

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
			e1.printStackTrace();
		}
	}
}
