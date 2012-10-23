package com.bfi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bfi.jdo.Event;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

public class YouTubeUtils {
	public List<VideoEntry> getVideos(Event event){
		VideoFeed videoFeed = null;
		Long startIndex = 1L;
		String orderBy = "viewCount";
		String developerKey = "AI39si6xzNDr4sK-84zSW9v2Yc9HvVE5cC6WhDtmMn0jO7RH0496fjRpw-E3iyH8m4iElENPGZsDm0ntVQzSU9QbRItB37k_2g"; // TODO
		List<VideoEntry> cleanedList = new ArrayList<VideoEntry>();
		try {
			YouTubeService service = new YouTubeService("BFI", developerKey);
			String feedURL = event.getFeedURL()
			+ "&orderby=" + String.valueOf(orderBy)
			+ "&start-index=" + String.valueOf(startIndex)
			+ "&max-results=" + String.valueOf(VideoServlet.MAX_RESULTS_API_MAX);
			videoFeed = service.getFeed(new URL(feedURL),
					VideoFeed.class);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<VideoEntry> allVideos = videoFeed.getEntries();
		for(VideoEntry v: allVideos){
			if (!isCorrectDateAndArtist(v.getTitle().getPlainText(),
					event.getDate(), event.getArtist().getSearchTerms())) {
				continue;
			}
			cleanedList.add(v);
		}
		event.setVideoCount(String.valueOf(cleanedList.size()));
		return cleanedList;
		
	}
	
	private boolean isCorrectDateAndArtist(String videoTitle, Date eventDate,
			String artistTerms) {
		// this only works for 3 nighters
		boolean validVideo = true;
		Calendar dayBefore = Calendar.getInstance();
		Calendar dayAfter = Calendar.getInstance();
		Calendar dayOf = Calendar.getInstance();
		dayOf.setTime(eventDate);
		dayBefore.setTime(eventDate);
		dayAfter.setTime(eventDate);
		dayBefore.add(Calendar.DATE, -1);
		dayAfter.add(Calendar.DATE, 1);
		String yr = Integer.toString(dayOf.get(Calendar.YEAR));
		String yrShort = Integer.toString(dayOf.get(Calendar.YEAR));
		yrShort = yrShort.substring(2, 4);
		String date = Integer.toString(dayOf.get(Calendar.DAY_OF_MONTH));

		// day before
		String yrB = Integer.toString(dayBefore.get(Calendar.YEAR));
		String yrShortB = yrB.substring(2, 4);
		String monthB = Integer.toString(dayBefore.get(Calendar.MONTH) + 1);
		String monthNameB = new DateFormatSymbols().getMonths()[dayBefore
				.get(Calendar.MONTH)];
		String dateB = Integer.toString(dayBefore.get(Calendar.DAY_OF_MONTH));
		// date after
		String yrA = Integer.toString(dayAfter.get(Calendar.YEAR));
		String yrShortA = yrA.substring(2, 4);
		String monthA = Integer.toString(dayAfter.get(Calendar.MONTH) + 1);
		String monthNameA = new DateFormatSymbols().getMonths()[dayAfter
				.get(Calendar.MONTH)];
		String dateA = Integer.toString(dayAfter.get(Calendar.DAY_OF_MONTH));

		String[] possibleDates = new String[] {
				// day before
				monthB + " " + dateB + " " + yrB,
				monthB + " " + dateB + " " + yrShortB,
				monthB + "/" + dateB + "/" + yrB,
				monthB + "/" + dateB + "/" + yrShortB,
				monthB + "." + dateB + "." + yrB,
				monthB + "." + dateB + "." + yrShortB,
				monthB + "-" + dateB + "-" + yrB,
				monthB + "-" + dateB + "-" + yrShortB,
				monthNameB + " " + dateB + " " + yrB,
				monthNameB + " " + dateB + " " + yrShortB,
				monthNameB + " " + dateB + ", " + yrB,
				monthNameB + " " + dateB + ", " + yrShortB,
				// days after
				monthA + " " + dateA + " " + yrA,
				monthA + " " + dateA + " " + yrShortA,
				monthA + "/" + dateA + "/" + yrA,
				monthA + "/" + dateA + "/" + yrShortA,
				monthA + "." + dateA + "." + yrA,
				monthA + "." + dateA + "." + yrShortA,
				monthA + "-" + dateA + "-" + yrA,
				monthA + "-" + dateA + "-" + yrShortA,
				monthNameA + " " + dateA + " " + yrA,
				monthNameA + " " + dateA + " " + yrShortA,
				monthNameA + " " + dateA + ", " + yrA,
				monthNameA + " " + dateA + ", " + yrShortA, };

		List<String> artistTermsList = Arrays.asList(artistTerms
				.split("\\s*,\\s*"));
		for (String beforeOrAfterDate : possibleDates) {
			if (videoTitle.contains(beforeOrAfterDate)) {
				validVideo = false;
				break;
			}
		}
		if (validVideo) {
			for (String s : artistTermsList) {
				if (videoTitle.contains(s)) {
					validVideo = true;
					break;
				}
				validVideo = false;
			}
		}
		if (validVideo) {
			int ix = videoTitle.lastIndexOf(yrShort);
			int ix2 = videoTitle.lastIndexOf(yr);
			String singleDate = date;
			if (singleDate.length() == 2) {
				singleDate = singleDate.substring(1, 2);
			}

			if (ix != -1) {
				String shortYrString = videoTitle.substring(ix - 4, ix + 2);
				if (shortYrString.contains(singleDate + " " + yrShort)
						|| shortYrString.contains(singleDate + "/" + yrShort)
						|| shortYrString.contains(singleDate + "." + yrShort)
						|| shortYrString.contains(singleDate + ", " + yrShort)
						|| shortYrString.contains(singleDate + "-" + yrShort)) {
					return true;
				}
			}
			if (ix2 != -1) {
				String longYrString = videoTitle.substring(ix - 6, ix + 2);
				if (longYrString.contains(singleDate + " " + yr)
						|| longYrString.contains(singleDate + "/" + yr)
						|| longYrString.contains(singleDate + "." + yr)
						|| longYrString.contains(singleDate + ", " + yr)
						|| longYrString.contains(singleDate + "-" + yr)) {
					return true;
				}

			}
			return false;
		}
		return validVideo;

	}

}
