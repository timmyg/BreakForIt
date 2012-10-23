package com.bfi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.bfi.jdo.Venue;

@SuppressWarnings("serial")
public class CreateEventsServlet extends HttpServlet {
	PersistenceManager pm = null;
	public static Venue WI_ALPINE = null;
	public static Venue NY_GI = null;
	public static Venue NJ_BADER = null;
	public static Venue VA_JPJ = null;
	public static Venue OH_RBEND = null;
	public static Venue WA_GORGE = null;
	public static Venue IN_DEER = null;
	public static Venue CA_CHULA = null;
	public static Venue CA_IRVINE = null;
	public static Venue CA_MTNVIEW = null;
	public static Venue CA_HOLLYWOOD = null;
	public static Venue TX_WOOD = null;
	public static Venue TX_DALLASGEX = null;
	public static Venue AL_GULF = null;
	public static Venue GA_ATLLW = null;
	public static Venue NC_CHARVW = null;
	public static Venue CT_HARTFORD = null;
	public static Venue PA_SCRANTON = null;
	public static Venue CAN_TORONTO = null;
	public static Venue OH_BLOSSOM = null;
	public static Venue MA_MANSFIELD = null;
	public static Venue NY_SPAC = null;
	public static Venue NY_WANTAGH = null;
	public static Venue VA_BRISTOW = null;
	public static Venue VA_VBEACH = null;
	public static Venue MN_STPAUL = null;
	public static Venue NJ_CAMDEN = null;
	public static Venue PA_HERSHEY = null;
	public static Venue NY_BETHEL = null;
	public static Venue NY_DARIEN = null;
	public static Venue MI_CLARKSTON = null;
	public static Venue MO_MARYH = null;
	public static Venue PA_BTOWN = null;
	public static Venue FL_TAMPA = null;
	public static Venue FL_WPB = null;
	public static Venue CA_BURBANKELLEN = null;
	public static Venue CA_HWKIMMEL = null;
	public static Venue IL_CHLAKESIDE = null;
	public static Venue NY_RANDALLS = null;
	public static Venue CO_REDROCKS = null;
	// /
	public static Venue GER_HAMBURG = null;
	public static Venue GER_BERLIN = null;
	public static Venue AUT_VIENNA = null;
	public static Venue GER_MUNICH = null;
	public static Venue ITA_MILAN = null;
	public static Venue ITA_ROME = null;
	public static Venue ITA_PADUA = null;
	public static Venue GER_COLOGNE = null;
	public static Venue BEL_ANTWERP = null;
	public static Venue NED_AMSTER = null;
	public static Venue GER_FRANK = null;
	public static Venue ENG_LOND = null;
	public static Venue ENG_MANCH = null;
	public static Venue IRE_DUB = null;
	public static Venue SCT_GLAS = null;
	public static Venue ENG_BIRM = null;
	public static Venue DEN_FRED = null;
	public static Venue SWE_STOCK = null;
	public static Venue BRA_RIO = null;
	public static Venue BRA_ITU = null;
	public static Venue AUG_BUENA = null;
	public static Venue CHI_SANTIAGO = null;

	public static Venue NY_BUFF = null;
	public static Venue NY_UNIONDALE = null;
	public static Venue NY_ALBANY = null;
	public static Venue PA_PHIL76 = null;
	public static Venue MA_BOSTONTD = null;
	public static Venue NY_MSG = null;
	public static Venue GA_ATLHAWKS = null;
	public static Venue SC_NCHARL = null;

	public static Venue TN_MANCHESTER = null;
	public static Venue OH_COLUMHUNT = null;
	public static Venue PA_PITTPIRATES = null;
	public static Venue NY_FLUSHINGMETS = null;
	public static Venue DC_NATIONALS = null;
	public static Venue KY_HULLA = null;
	public static Venue KS_WITCH = null;
	public static Venue CO_COMMC = null;
	public static Venue UT_WESTVALLEYCITY = null;
	public static Venue CA_CONCORD = null;
	public static Venue CA_WHEATLAND = null;

	public static Venue NE_OMAHA = null;
	public static Venue MN_SPWILD = null;
	public static Venue IL_WRIGLEY = null;
	public static Venue ID_BOISE = null;

	public static Artist ARTIST_DAVE = null;


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		try {
			pm = PMF.get().getPersistenceManager();
			create();
		} finally {
			pm.close();
		}
		resp.getWriter().println("Successfully created events");
	}
		
	@SuppressWarnings("unchecked")
	private void load() {
		// LOAD VENUES
		Query q = pm.newQuery(Venue.class);
		try {
			List<Venue> results = (List<Venue>) q.execute();
			if (!results.isEmpty()) {
				for (Venue v : results) {
					if (v.getName().equals("Alpine Valley")) {
						WI_ALPINE = v;
					}
					;
					if (v.getName().equals("Riverbend")) {
						OH_RBEND = v;
					}
					;
					if (v.getName().equals("Deer Creek")) {
						IN_DEER = v;
					}
					;
					if (v.getName().equals("Gorge")) {
						WA_GORGE = v;
					}
					;
					if (v.getName().equals("Cricket Wireless Amphitheatre")) {
						CA_CHULA = v;
					}
					;
					if (v.getName().equals("Verizon Wireless Amphitheatre") && v.getCity().equals("Irvine")) {
						CA_IRVINE = v;
					}
					;
					if (v.getName().equals("Shoreline Amphitheatre")) {
						CA_MTNVIEW = v;
					}
					;
					if (v.getName().equals("Hollywood Bowl")) {
						CA_HOLLYWOOD = v;
					}
					;
					if (v.getName().equals("Warner Bros. Studios")) {
						CA_BURBANKELLEN = v;
					}
					;
					if (v.getName().equals("El Captain Theatre")) {
						CA_HWKIMMEL = v;
					}
					;
					if (v.getName().equals(
							"The Cynthia Woods Mitchell Pavilion")) {
						TX_WOOD = v;
					}
					;
					if (v.getName().equals("Gexa Energy Pavilion")) {
						TX_DALLASGEX = v;
					}
					;
					if (v.getName().equals("Hangout Festival")) {
						AL_GULF = v;
					}
					;
					if (v.getName().equals("Aaron's Amphitheatre at Lakewood")) {
						GA_ATLLW = v;
					}
					;
					if (v.getName().equals("Verizon Wireless Amphitheatre") && v.getCity().equals("Charlotte")) {
						NC_CHARVW = v;
					}
					;
					if (v.getName().equals("Comcast Theatre") && v.getCity().equals("Hartford") ) {
						CT_HARTFORD = v;
					}
					;
					if (v.getName().equals(
							"Toyota Pavilion at Montage Mountain")) {
						PA_SCRANTON = v;
					}
					;
					if (v.getName().equals("The Molson Amphitheatre")) {
						CAN_TORONTO = v;
					}
					;
					if (v.getName().equals("Blossom Music Center")) {
						OH_BLOSSOM = v;
					}
					;
					if (v.getName().equals("Comcast Center") && v.getCity().equals("Mansfield")) {
						MA_MANSFIELD = v;
					}
					;
					if (v.getName().equals("Saratoga Performing Arts Center")) {
						NY_SPAC = v;
					}
					;
					if (v.getName().equals("Nikon at Jones Beach Theater")) {
						NY_WANTAGH = v;
					}
					;
					if (v.getName().equals("Jiffy Lube Live")) {
						VA_BRISTOW = v;
					}
					;
					if (v.getName()
							.equals("Farm Bureau Live at Virginia Beach")) {
						VA_VBEACH = v;
					}
					;
					if (v.getName().equals("Harriet Island")) {
						MN_STPAUL = v;
					}
					;
					if (v.getName().equals("Susquehanna Bank Center")) {
						NJ_CAMDEN = v;
					}
					;
					if (v.getName().equals("Hersheypark Stadium")) {
						PA_HERSHEY = v;
					}
					;
					if (v.getName().equals("Bethel Woods Center for the Arts")) {
						NY_BETHEL = v;
					}
					;
					if (v.getName()
							.equals("Darien Lake Performing Arts Center")) {
						NY_DARIEN = v;
					}
					;
					if (v.getName().equals("DTE Energy Music Theatre")) {
						MI_CLARKSTON = v;
					}
					;
					if (v.getName().equals("Verizon Wireless Amphitheatre")  && v.getCity().equals("Maryland Heights")) {
						MO_MARYH = v;
					}
					;
					if (v.getName().equals("First Niagara Pavilion")) {
						PA_BTOWN = v;
					}
					;
					if (v.getName().equals("1-800-ASK-GARY Amphitheater")) {
						FL_TAMPA = v;
					}
					;
					if (v.getName().equals("Cruzan Amphitheatre")) {
						FL_WPB = v;
					}
					;
					if (v.getName().equals("Governors Island")) {
						NY_GI = v;
					}
					;
					if (v.getName().equals("Bader Field")) {
						NJ_BADER = v;
					}
					;
					if (v.getName().equals("Lakeside")) {
						IL_CHLAKESIDE = v;
					}
					;
					if (v.getName().equals("Randall's Island Park")) {
						NY_RANDALLS = v;
					}
					;
					if (v.getName().equals("HSBC Arena")) {
						NY_BUFF = v;
					}
					;
					if (v.getName().equals("Nassau Veterans Memorial Coliseum")) {
						NY_UNIONDALE = v;
					}
					;
					if (v.getName().equals("Times Union Center")) {
						NY_ALBANY = v;
					}
					;
					if (v.getName().equals("Wells Fargo Center")) {
						PA_PHIL76 = v;
					}
					;
					if (v.getName().equals("TD Garden")) {
						MA_BOSTONTD = v;
					}
					;
					if (v.getName().equals("Madison Square Garden")) {
						NY_MSG = v;
					}
					;
					if (v.getName().equals("Philips Arena")) {
						GA_ATLHAWKS = v;
					}
					;
					if (v.getName().equals("North Charleston Coliseum")) {
						SC_NCHARL = v;
					}
					;
					if (v.getName().equals("John Paul Jones Arena")) {
						VA_JPJ = v;
					}
					;
					if (v.getName().equals("HSBC Arena")) {
						BRA_RIO = v;
					}
					;
					if (v.getName().equals("Fazenda Meada")) {
						BRA_ITU = v;
					}
					;
					if (v.getName().equals("Luna Park")) {
						AUG_BUENA = v;
					}
					;
					if (v.getName().equals("Movistar Areana")) {
						CHI_SANTIAGO = v;
					}
					;
					if (v.getName().equals("Bonnaroo")) {
						TN_MANCHESTER = v;
					}
					;
					if (v.getName().equals("Huntington Park")) {
						OH_COLUMHUNT = v;
					}
					;
					if (v.getName().equals("PNC Park")) {
						PA_PITTPIRATES = v;
					}
					;
					if (v.getName().equals("Citi Field")) {
						NY_FLUSHINGMETS = v;
					}
					;
					if (v.getName().equals("Nationals Park")) {
						DC_NATIONALS = v;
					}
					;
					if (v.getName().equals("Churchill Downs")) {
						KY_HULLA = v;
					}
					;
					if (v.getName().equals("InTrust Bank Arena")) {
						KS_WITCH = v;
					}
					;
					if (v.getName().equals("Dick's Sporting Good Park")) {
						CO_COMMC = v;
					}
					;
					if (v.getName().equals("USANA Amphitheatre")) {
						UT_WESTVALLEYCITY = v;
					}
					;
					if (v.getName().equals("Sleep Train Pavilion at Concord")) {
						CA_CONCORD = v;
					}
					;
					if (v.getName().equals("Sleep Train Amphitheatre")) {
						CA_WHEATLAND = v;
					}
					;
					if (v.getName().equals("Taco Bell Arena")) {
						ID_BOISE = v;
					}
					;
					if (v.getName().equals("Qwest Center")) {
						NE_OMAHA = v;
					}
					;
					if (v.getName().equals("Xcel Energy Center")) {
						MN_SPWILD = v;
					}
					;
					if (v.getName().equals("Wrigley Field")) {
						IL_WRIGLEY = v;
					}
					;
					if (v.getName().equals("Congress Center Hamburg")) {
						GER_HAMBURG = v;
					}
					;
					if (v.getName().equals("Tempodrom")) {
						GER_BERLIN = v;
					}
					;
					if (v.getName().equals("Gasometer")) {
						AUT_VIENNA = v;
					}
					;
					if (v.getName().equals("Zenith")) {
						GER_MUNICH = v;
					}
					;
					if (v.getName().equals("PalaSharp")) {
						ITA_MILAN = v;
					}
					;
					if (v.getName().equals("PalaLottomatica")) {
						ITA_ROME = v;
					}
					;
					if (v.getName().equals("PaloSport San Lazzaro")) {
						ITA_PADUA = v;
					}
					;
					if (v.getName().equals("Palladium Koln")) {
						GER_COLOGNE = v;
					}
					;
					if (v.getName().equals("Lotta Arena")) {
						BEL_ANTWERP = v;
					}
					;
					if (v.getName().equals("Heineken Music Hall")) {
						NED_AMSTER = v;
					}
					;
					if (v.getName().equals("Jahrhunderthalle Frankfurt")) {
						GER_FRANK = v;
					}
					;
					if (v.getName().equals("The O2 Arena")) {
						ENG_LOND = v;
					}
					;
					if (v.getName().equals("Manchester Appolo")) {
						ENG_MANCH = v;
					}
					;
					if (v.getName().equals("The O2")) {
						IRE_DUB = v;
					}
					;
					if (v.getName().equals("Clyde Auditorium")) {
						SCT_GLAS = v;
					}
					;
					if (v.getName().equals("O2 Academy")) {
						ENG_BIRM = v;
					}
					;
					if (v.getName().equals("Falkoner Salen")) {
						DEN_FRED = v;
					}
					;
					if (v.getName().equals("Arenan Fryshuset")) {
						SWE_STOCK = v;
					}
					;
					if (v.getName().equals("Red Rocks")) {
						CO_REDROCKS = v;
					}
					;
				}
			}
			// else {
			// // Handle "no results" case
			// }
		} finally {
			q.closeAll();
		}

		// LOAD ARTISTS
		try {
			q = pm.newQuery(Artist.class);
			List<Artist> results = (List<Artist>) q.execute();
			if (!results.isEmpty()) {
				for (Artist a : results) {
					if (a.getTag().equals("dmb")) {
						ARTIST_DAVE = a;
					}
					;
				}
			} else {
				// Handle "no results" case
			}
		} finally {
			q.closeAll();
		}
	}

	private void create() {
		Collection<Object> collection = new ArrayList<Object>();
		
		load();
		
		collection.add(new Event(date(2012, 9, 13), CA_HWKIMMEL, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 12), CA_HOLLYWOOD, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 11), CA_BURBANKELLEN, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 9), CA_MTNVIEW, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 8), CA_IRVINE, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 7), CA_CHULA, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 2), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2012, 9, 1), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2012, 8, 31), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 21), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 20), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 18), FL_TAMPA, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 14), PA_BTOWN, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 13), PA_BTOWN, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 11), MO_MARYH, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 10), MI_CLARKSTON, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 7), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 6), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2012, 7, 3), NY_DARIEN, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 30), NY_BETHEL, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 29), PA_HERSHEY, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 27), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 26), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 24), MN_STPAUL, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 23), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 22), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 17), VA_VBEACH, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 16), VA_BRISTOW, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 13), NY_WANTAGH, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 12), NY_WANTAGH, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 9), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 8), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 6), MA_MANSFIELD, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 5), MA_MANSFIELD, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 3), OH_BLOSSOM, ARTIST_DAVE));
		collection.add(new Event(date(2012, 6, 2), CAN_TORONTO, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 29), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 28), PA_SCRANTON, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 26), CT_HARTFORD, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 25), CT_HARTFORD, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 23), NC_CHARVW, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 22), GA_ATLLW, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 20), AL_GULF, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 19), TX_DALLASGEX, ARTIST_DAVE));
		collection.add(new Event(date(2012, 5, 18), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2011, 9, 18), NY_RANDALLS, ARTIST_DAVE));
		collection.add(new Event(date(2011, 9, 17), NY_RANDALLS, ARTIST_DAVE));
		collection.add(new Event(date(2011, 9, 16), NY_RANDALLS, ARTIST_DAVE));
		collection.add(new Event(date(2011, 9, 4), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2011, 9, 3), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2011, 9, 2), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2011, 8, 26), NY_GI, ARTIST_DAVE));
		collection.add(new Event(date(2011, 7, 10), IL_CHLAKESIDE, ARTIST_DAVE));
		collection.add(new Event(date(2011, 7, 9), IL_CHLAKESIDE, ARTIST_DAVE));
		collection.add(new Event(date(2011, 7, 8), IL_CHLAKESIDE, ARTIST_DAVE));
		collection.add(new Event(date(2011, 6, 26), NJ_BADER, ARTIST_DAVE));
		collection.add(new Event(date(2011, 6, 25), NJ_BADER, ARTIST_DAVE));
		collection.add(new Event(date(2011, 6, 24), NJ_BADER, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 20), VA_JPJ, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 19), VA_JPJ, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 17), SC_NCHARL, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 16), GA_ATLHAWKS, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 13), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 12), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 10), MA_BOSTONTD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 9), MA_BOSTONTD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 6), PA_PHIL76, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 5), NY_ALBANY, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 3), NY_UNIONDALE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 11, 2), NY_BUFF, ARTIST_DAVE));
		collection.add(new Event(date(2010, 10, 16), CHI_SANTIAGO, ARTIST_DAVE));
		collection.add(new Event(date(2010, 10, 14), AUG_BUENA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 10, 10), BRA_ITU, ARTIST_DAVE));
		collection.add(new Event(date(2010, 10, 8), BRA_RIO, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 18), IL_WRIGLEY, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 17), IL_WRIGLEY, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 15), MN_SPWILD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 14), NE_OMAHA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 11), TX_DALLASGEX, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 1), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 5), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 4), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 9, 3), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 31), ID_BOISE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 28), CA_MTNVIEW, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 27), CA_WHEATLAND, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 25), CA_CONCORD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 23), CA_HOLLYWOOD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 21), CA_IRVINE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 20), CA_CHULA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 17), UT_WESTVALLEYCITY, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 15), CO_COMMC, ARTIST_DAVE));
		collection.add(new Event(date(2010, 8, 14), KS_WITCH, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 31), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 3), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 28), FL_TAMPA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 27), GA_ATLLW, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 25), KY_HULLA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 23), DC_NATIONALS, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 21), NC_CHARVW, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 20), VA_VBEACH, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 17), NY_FLUSHINGMETS, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 16), NY_FLUSHINGMETS, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 14), PA_SCRANTON, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 13), NY_BETHEL, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 1), PA_PITTPIRATES, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 9), PA_HERSHEY, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 4), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 3), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 7, 1), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 30), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 25), OH_BLOSSOM, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 23), MI_CLARKSTON, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 22), OH_COLUMHUNT, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 19), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 18), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 16), MO_MARYH, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 15), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 13), TN_MANCHESTER, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 8), MA_MANSFIELD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 7), MA_MANSFIELD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 5), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 4), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 2), NY_DARIEN, ARTIST_DAVE));
		collection.add(new Event(date(2010, 6, 1), CAN_TORONTO, ARTIST_DAVE));
		collection.add(new Event(date(2010, 5, 29), CT_HARTFORD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 5, 28), CT_HARTFORD, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 15), SWE_STOCK, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 14), DEN_FRED, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 12), ENG_BIRM, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 11), SCT_GLAS, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 9), IRE_DUB, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 7), ENG_MANCH, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 6), ENG_LOND, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 4), GER_FRANK, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 3), NED_AMSTER, ARTIST_DAVE));
		collection.add(new Event(date(2010, 3, 1), BEL_ANTWERP, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 28), GER_COLOGNE, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 25), ITA_PADUA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 23), ITA_ROME, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 22), ITA_MILAN, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 20), GER_MUNICH, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 19), AUT_VIENNA, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 17), GER_BERLIN, ARTIST_DAVE));
		collection.add(new Event(date(2010, 2, 16), GER_HAMBURG, ARTIST_DAVE));
		collection.add(new Event(date(2009, 9, 20), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2009, 9, 19), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2009, 9, 6), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2009, 9, 5), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2009, 9, 4), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2009, 8, 15), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2009, 8, 14), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2009, 8, 1), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2009, 7, 31), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2009,7,19), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2009,7,18), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2009, 6,16), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2009, 6, 13), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2009, 6, 12), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2009, 5, 1), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2009, 4, 18), VA_JPJ, ARTIST_DAVE));
		collection.add(new Event(date(2009, 4, 17), VA_JPJ, ARTIST_DAVE));
		collection.add(new Event(date(2009, 4, 14), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2008, 9, 23), VA_JPJ, ARTIST_DAVE));
		collection.add(new Event(date(2008, 9, 22), VA_JPJ, ARTIST_DAVE));
		collection.add(new Event(date(2008, 9, 10), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2008, 8, 31), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2008, 8, 30), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2008, 8, 29), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2008, 8, 15), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2008,8,10), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2008,8,9), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2008, 8,5), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2008, 7,26), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2008, 7, 25), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2008, 7, 12), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2008, 7, 11), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2008, 6, 21), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2008, 6, 20), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2008, 6, 4), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2008, 6, 3), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2007, 10, 2), CA_HOLLYWOOD, ARTIST_DAVE));
		collection.add(new Event(date(2007, 10, 1), CA_HOLLYWOOD, ARTIST_DAVE));
		collection.add(new Event(date(2007, 9, 21), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2007, 9, 16), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2007, 9, 15), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2007, 9, 2), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2007, 9, 1), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8, 31), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2007,8,26), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2007,8,25), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8,22), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8,18), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8,17), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8, 14), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8, 8), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2007, 8, 7), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2006, 9, 3), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2006, 9, 2), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2006, 9, 1), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2006, 8, 28), CA_HOLLYWOOD, ARTIST_DAVE));
		collection.add(new Event(date(2006, 8, 18), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2006, 8, 12), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2006, 8, 11), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2006, 8,1), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2006,7,2), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2006,7,1), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2006, 6, 28), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2006, 6, 27), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2006, 6, 17), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2006, 6, 16), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2006, 6,3), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2006, 6,2), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2005, 12, 10), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2005, 12, 9), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2005, 9, 12), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(2005, 9, 11), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(2005, 9, 10), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(2005, 9, 9), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(2005, 9, 5), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2005, 8, 21), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2005, 8, 20), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2005, 8, 19), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2005,7,24), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2005,7,23), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7,21), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7, 17), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7, 16), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7, 6), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7, 5), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7,2), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2005, 7,1), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2005, 6,13), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2005, 6,12), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2004, 9, 5), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2004, 9, 4), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2004, 9, 3), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2004, 8, 21), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2004, 8, 20), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2004,8,8), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2004,8,7), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2004, 8,5), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2004, 8, 1), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2004, 7, 31), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2004, 7, 21), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2004, 7, 20), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2004, 7,3), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2004, 7,2), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2004, 6,21), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2004, 6,20), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2003, 12, 17), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2003, 9, 6), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2003, 9, 5), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2003, 8,28), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2003, 8,27), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2003, 8,8), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2003, 8,7), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2003, 7, 16), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2003,7,6), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2003,7,5), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2003, 6,30), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2003, 6,29), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2003, 6, 24), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2003, 6, 23), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2002, 12, 21), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2002, 12, 20), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2002, 9, 8), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2002, 9, 7), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2002, 9, 6), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2002,9,1), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2002,8,31), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2002, 8,11), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2002, 8,10), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2002, 8,8), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7,29), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7,28), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7, 18), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7, 17), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7, 16), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7, 6), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2002, 7, 5), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2002, 5, 29), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2002, 5, 28), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2002, 5, 5), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2002, 5, 4), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2001, 9,29), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2001, 8, 26), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2001, 8, 25), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2001, 8, 24), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2001, 7,29), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2001, 7, 21), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2001, 6, 24), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2001, 6, 23), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2001, 6, 22), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(2001, 6, 9), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2001, 5, 12), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2001, 5, 11), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2000, 12, 13), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2000, 12, 12), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(2000, 9, 13), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2000, 9, 12), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(2000, 9, 8), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(2000,8,20), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2000,8,19), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(2000, 8, 6), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2000, 8, 5), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2000, 8, 4), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(2000, 7,28), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(2000, 6, 27), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2000, 6, 26), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(2000, 6,24), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2000, 6,23), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(2000, 6,22), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1999, 7, 26), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(1999, 7, 17), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(1999, 7, 16), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(1999,6,27), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(1999,6,26), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(1999, 6, 24), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1999, 6, 23), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1999, 6, 22), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1999, 6, 20), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(1999, 5, 2), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(1998, 12, 3), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(1998, 12, 2), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(1998, 8, 26), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(1998, 8, 14), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(1998, 8,9), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1998, 8,8), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1998, 7,26), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(1998, 7, 25), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(1998, 7, 24), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(1998, 6, 2), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(1998, 5, 30), WI_ALPINE, ARTIST_DAVE));
		collection.add(new Event(date(1998, 5, 25), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(1998, 5, 24), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(1998, 5, 15), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(1997, 7, 11), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(1997, 7, 2), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(1997, 7, 1), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(1997, 6, 28), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1997, 6, 22), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(1997, 6, 7), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(1997, 6,6), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(1996, 10, 25), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(1996, 10, 4), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(1996, 10, 3), NY_MSG, ARTIST_DAVE));
		collection.add(new Event(date(1996, 9, 4), FL_WPB, ARTIST_DAVE));
		collection.add(new Event(date(1996, 8, 4), WA_GORGE, ARTIST_DAVE));
		collection.add(new Event(date(1996, 7, 21), TX_WOOD, ARTIST_DAVE));
		collection.add(new Event(date(1996, 6, 19), IN_DEER, ARTIST_DAVE));
		collection.add(new Event(date(1996, 6, 16), OH_RBEND, ARTIST_DAVE));
		collection.add(new Event(date(1996, 6, 8), NY_SPAC, ARTIST_DAVE));
		collection.add(new Event(date(1996, 6, 5), NJ_CAMDEN, ARTIST_DAVE));
		collection.add(new Event(date(1995, 8, 15), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(1994, 5, 14), CO_REDROCKS, ARTIST_DAVE));
		collection.add(new Event(date(1994, 5, 13), CO_REDROCKS, ARTIST_DAVE));
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
