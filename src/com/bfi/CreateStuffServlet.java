package com.bfi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Artist;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Tour;
import com.bfi.jdo.Venue;

@SuppressWarnings("serial")
public class CreateStuffServlet extends HttpServlet {
	PersistenceManager pm = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			pm = PMF.get().getPersistenceManager();
			create();
		} finally {
			pm.close();
		}
		resp.getWriter().println("Successfully created artists, tours, and venues");
	}

	private void create() {
		Collection<Object> collection = new ArrayList<Object>();
		
		collection.add(new Artist("Dave Matthews Band","DMB","dmb","DMB",null));
		
		collection.add(new Tour(date(2012,11,30),date(2012,12,22), "Winter", "US", null,null));
		collection.add(new Tour(date(2012,5,18),date(2012,9,12), "Summer", "US", null,null));
		collection.add(new Tour(date(2011,6,24),date(2011,9,18), "Summer", "US", "Caravan", null));
		collection.add(new Tour(date(2010,11,2),date(2010,11,20), "Fall", "US", null,null));
		collection.add(new Tour(date(2010,10,8),date(2010,10,16), "Fall", "South America", null,null));
		collection.add(new Tour(date(2010,5,28),date(2010,9,18), "Summer", "US", null,null));
		collection.add(new Tour(date(2010,2,16),date(2010,3,15), "Winter", "Europe", null,null));
		collection.add(new Tour(date(2009,5,27),date(2009,10,3), "Summer", "US", null,null));
		collection.add(new Tour(date(2008,5,30),date(2008,9,7), "Summer", "US", null,null));
		collection.add(new Tour(date(2007,8,1),date(2007,10,2), "Summer", "US", null,null));
		collection.add(new Tour(date(2006,5,30),date(2006,9,23), "Summer", "US", null,null));
		collection.add(new Tour(date(2005,6,1),date(2005,9,12), "Summer", "US", null,null));
		collection.add(new Tour(date(2004,6,17),date(2004,9,8), "Summer", "US", null,null));
		collection.add(new Tour(date(2003,6,17),date(2003,9,20), "Summer", "US", null,null));
		collection.add(new Tour(date(2002,7,5),date(2002,9,8), "Summer", "US", null,null));
		collection.add(new Tour(date(2001,4,21),date(2001,8,28), "Summer", "US", null,null));
		collection.add(new Tour(date(2000,6,19),date(2000,9,20), "Summer", "US", null,null));
		collection.add(new Tour(date(1999,5,1),date(1999,8,8), "Summer", "US", null,null));
		collection.add(new Tour(date(1998,7,22),date(1998,8,30), "Summer", "US", null,null));
		collection.add(new Tour(date(1997,6,3),date(1997,7,11), "Summer", "US", null,null));
		collection.add(new Tour(date(1996,6,4),date(1996,6,23), "Summer", "US", null,null));
		collection.add(new Tour(date(1995,7,20),date(1995,9,3), "Summer", "US", null,null));
		collection.add(new Tour(date(1994,7,17),date(1994,9,4), "Summer", "US", null,null));
		
		collection.add(new Venue("Alpine Valley","East Troy","WI","US","Alpine Valley",null));
		collection.add(new Venue("Riverbend","Cincinnati","OH","US","Riverbend",null));
		collection.add(new Venue("Deer Creek","Noblesville","IN","US","Deer Creek",null));
		collection.add(new Venue("Gorge","George","WA","US","Gorge",null));
		collection.add(new Venue("Cricket Wireless Amphitheatre","Chula Vista","CA","US","Chula Vista",null));
		collection.add(new Venue("Verizon Wireless Amphitheatre","Irvine","CA","US","Irvine",null));
		collection.add(new Venue("Shoreline Amphitheatre","Mountain View","CA","US","Mountain View","Shoreline"));
		collection.add(new Venue("Hollywood Bowl","Hollywood","CA","US","Hollywood Bowl",null));
		collection.add(new Venue("Warner Bros. Studios","Burbank","CA","US","Ellen",null));
		collection.add(new Venue("El Captain Theatre","Hollywood","CA","US","Kimmel",null));
		collection.add(new Venue("The Cynthia Woods Mitchell Pavilion","The Woodlands","TX","US","Woodlands",null));
		collection.add(new Venue("Gexa Energy Pavilion","Dallas","TX","US","Dallas",null));
		collection.add(new Venue("Hangout Festival","Gulf Shores","AL","US","Hangout",null));
		collection.add(new Venue("Aaron's Amphitheatre at Lakewood","Atlanta","GA","US","Atlanta",null));
		collection.add(new Venue("Verizon Wireless Amphitheatre","Charlotte","NC","US","Charlotte",null));
		collection.add(new Venue("Comcast Theatre","Hartford","CT","US","Hartford",null));
		collection.add(new Venue("Toyota Pavilion at Montage Mountain","Scranton","PA","US","Scranton",null));
		collection.add(new Venue("The Molson Amphitheatre","Toronto","","CAN","Toronto",null));
		collection.add(new Venue("Blossom Music Center","Cuyahoga Falls","OH","US","Blossom","Cuyahoga"));
		collection.add(new Venue("Comcast Center","Mansfield","MA","US","Mansfield",null));
		collection.add(new Venue("Saratoga Performing Arts Center","Saratoga Springs","NY","US","SPAC","Saratoga"));
		collection.add(new Venue("Nikon at Jones Beach Theater","Wantagh","NY","US","Wantagh",null));
		collection.add(new Venue("Jiffy Lube Live","Bristow","VA","US","Bristow",null));
		collection.add(new Venue("Farm Bureau Live at Virginia Beach","Virginia Beach","VA","US","Virginia Beach",null));
		collection.add(new Venue("Harriet Island","St. Paul","MN","US","Paul","Harriet"));
		collection.add(new Venue("Susquehanna Bank Center","Camden","NJ","US","Camden",null));
		collection.add(new Venue("Hersheypark Stadium","Hershey","PA","US","Hershey",null));
		collection.add(new Venue("Bethel Woods Center for the Arts","Bethel","NY","US","Bethel",null));
		collection.add(new Venue("Darien Lake Performing Arts Center","Darien Center","NY","US","Darien",null));
		collection.add(new Venue("DTE Energy Music Theatre","Clarkston","MI","US","Clarkston","DTE"));
		collection.add(new Venue("Verizon Wireless Amphitheatre","Maryland Heights","MO","US","Maryland Heights",null));
		collection.add(new Venue("First Niagara Pavilion","Burgettstown","PA","US","Burgettstown",null));
		collection.add(new Venue("1-800-ASK-GARY Amphitheater","Tampa","FL","US","Tampa",null));
		collection.add(new Venue("Cruzan Amphitheatre","West Palm Beach","FL","US","West Palm Beach",null));
		collection.add(new Venue("Governors Island","New York","NY","US","Governors Island",null));
		collection.add(new Venue("Bader Field","Atlantic City","NJ","US","Bader Field",null));
		collection.add(new Venue("Lakeside","Chicago","IL","US","Lakeside",null));
		collection.add(new Venue("Randall's Island Park","New York","NY","US","Randalls",null));
		collection.add(new Venue("HSBC Arena","Buffalo","NY","US","Buffalo",null));
		collection.add(new Venue("Nassau Veterans Memorial Coliseum","Uniondale","NY","US","Uniondale",null));
		collection.add(new Venue("Times Union Center","Albany","NY","US","Albany",null));
		collection.add(new Venue("Wells Fargo Center","Philadelphia","PA","US","Philadelphia",null));
		collection.add(new Venue("TD Garden","Boston","MA","US","Boston",null));
		collection.add(new Venue("Madison Square Garden","New York","NY","US","MSG",null));
		collection.add(new Venue("Philips Arena","Atlanta","GA","US","Atlanta",null));
		collection.add(new Venue("North Charleston Coliseum","North Charleston","SC","US","Charleston",null));
		collection.add(new Venue("John Paul Jones Arena","Charlottesville","VA","US","John Paul Jones",null));
		collection.add(new Venue("HSBC Arena","Rio de Janeiro",null,"BRA","Rio",null));
		collection.add(new Venue("Fazenda Meada","Itu",null,"BRA","Itu",null));
		collection.add(new Venue("Luna Park","Buenos Aires",null,"ARG","Buenos Aires",null));
		collection.add(new Venue("Movistar Areana","Santiago",null,"CHI","Santiago",null));
		collection.add(new Venue("Bonnaroo","Manchester",null,"TN","Bonnaroo",null));
		collection.add(new Venue("Huntington Park","Columbus",null,"OH","Columbus",null));
		collection.add(new Venue("PNC Park","Pittsburgh",null,"PA","Pittsburgh",null));
		collection.add(new Venue("Citi Field","Flushing",null,"NY","Citi",null));
		collection.add(new Venue("Nationals Park","Washington",null,"DC","Washington",null));
		collection.add(new Venue("Churchill Downs","Louisville",null,"KY","Louisville",null));
		collection.add(new Venue("InTrust Bank Arena","Wichita",null,"KS","Wichita",null));
		collection.add(new Venue("Dick's Sporting Good Park","Commerce City",null,"CO","Commerce",null));
		collection.add(new Venue("USANA Amphitheatre","West Valley City","UT",null,"UT","West Valley"));
		collection.add(new Venue("Sleep Train Pavilion at Concord","Concord",null,"CA","Concord",null));
		collection.add(new Venue("Sleep Train Amphitheatre","Wheatland",null,"CA","Wheatland",null));
		collection.add(new Venue("Taco Bell Arena","Boise",null,"ID","Boise",null));
		collection.add(new Venue("Qwest Center","Omaha",null,"NE","Omaha",null));
		collection.add(new Venue("Xcel Energy Center","Saint Paul",null,"MN","Saint Paul",null));
		collection.add(new Venue("Wrigley Field","Chicago",null,"IL","Wrigley",null));
		collection.add(new Venue("Congress Center Hamburg","Hamburg",null,"GER","Hamburg",null));
		collection.add(new Venue("Tempodrom","Berlin",null,"GER","Berlin",null));
		collection.add(new Venue("Gasometer","Vienna",null,"AUT","Vienna",null));
		collection.add(new Venue("Zenith","Munich",null,"GER","Munich",null));
		collection.add(new Venue("PalaSharp","Milan",null,"ITA","Milan",null));
		collection.add(new Venue("PalaLottomatica","Rome",null,"ITA","Rome",null));
		collection.add(new Venue("PaloSport San Lazzaro","Padua",null,"ITA","Padua",null));
		collection.add(new Venue("Palladium Koln","Cologne",null,"GER","Cologne",null));
		collection.add(new Venue("Lotta Arena","Antwerp",null,"BEL","Antwerp",null));
		collection.add(new Venue("Heineken Music Hall","Amsterdam",null,"GER","Amsterdam",null));
		collection.add(new Venue("Jahrhunderthalle Frankfurt","Frankfurt am Main",null,"GER","Frankfurt",null));
		collection.add(new Venue("The O2 Arena","London",null,"ENG","London",null));
		collection.add(new Venue("Manchester Appolo","Manchester",null,"ENG","Manchester",null));
		collection.add(new Venue("The O2","Dublin",null,"IRE","Dublin",null));
		collection.add(new Venue("Clyde Auditorium","Glasgow",null,"SCT","Glasgow",null));
		collection.add(new Venue("O2 Academy","Birmingham",null,"ENG","Birmingham",null));
		collection.add(new Venue("Falkoner Salen","Frederiksberg",null,"DEN","Frederiksberg",null));
		collection.add(new Venue("Arenan Fryshuset","Stockholm",null,"SWE","Stockholm",null));
		collection.add(new Venue("Red Rocks","Morrison","CO","US","Red Rocks",null));
		
		collection.add(new Venue("Izod Center","East Rutherford","NJ","US","East Rutherford","Izod Center"));
		collection.add(new Venue("KFC Yum! Center","Louisville","KY","US","KFC Yum! Center",null));
		collection.add(new Venue("United Center","Chicago","IL","US","United Center",null));
		collection.add(new Venue("Air Canada Centre","Toronto","ON","CAN","Air Canada Centre",null));
		collection.add(new Venue("Mohegan Sun Arena","Uncasville","CT","US","Uncasville",null));
		collection.add(new Venue("The Arena at Gwinnett Center","Duluth","GA","US","Duluth",null));
		collection.add(new Venue("PNC Arena","Raleigh","NC","US","Raleigh",null));
		collection.add(new Venue("1st Mariner Arena","Baltimore","MD","US","Baltimore",null));
		collection.add(new Venue("Verizon Wireless Arena","Manchester","NH","US","Manchester, NH",null));
		collection.add(new Venue("Barclays Center","Brooklyn","NY","US","Barclays Center","Brooklyn"));
		
		
		pm.makePersistentAll(collection);
	}

	Date date(int y, int m, int d) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, y);
		c.set(Calendar.MONTH, m - 1);
		c.set(Calendar.DAY_OF_MONTH, d);
		c = zeroTime(c);
		return c.getTime();
	}

	Calendar zeroTime(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
		c.set(Calendar.MINUTE, 0); // set minute in hour
		c.set(Calendar.SECOND, 0); // set second in minute
		c.set(Calendar.MILLISECOND, 0); // set millis in second
		return c;
	}
}
