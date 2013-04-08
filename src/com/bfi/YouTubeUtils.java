package com.bfi;

import com.bfi.jdo.Artist;
import com.bfi.jdo.Event;
import com.bfi.jdo.PMF;
import com.google.common.base.Joiner;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.common.base.StringUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.tools.ant.util.StringUtils;

public class YouTubeUtils {
    HashMap<String, String[]> monthNames = new HashMap<String, String[]>();
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Artist openers = null;
    public List<VideoEntry> getVideos(Event event){

        VideoFeed videoFeed = null;
        Long startIndex = 1L;
//        String orderBy = "viewCount";
        String developerKey = "AI39si6xzNDr4sK-84zSW9v2Yc9HvVE5cC6WhDtmMn0jO7RH0496fjRpw-E3iyH8m4iElENPGZsDm0ntVQzSU9QbRItB37k_2g";
        List<VideoEntry> cleanedList = new ArrayList<VideoEntry>();
        try {
            YouTubeService service = new YouTubeService("BFI", developerKey);
            String feedURL = event.getFeedURL()
//                    + "&orderby=" + String.valueOf(orderBy)
                    + "&start-index=" + String.valueOf(startIndex)
                    + "&max-results=" + String.valueOf(VideoServlet.MAX_RESULTS_API_MAX);
            System.out.println("Feed URL: " + feedURL);
            videoFeed = service.getFeed(new URL(feedURL),
                    VideoFeed.class);
            System.out.println("Return XML: " + videoFeed.getXmlBlob().getFullText());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        List<VideoEntry> allVideos = videoFeed.getEntries();
        for(VideoEntry v: allVideos){
            if(v.getMediaGroup() != null && v.getMediaGroup().getDescription() != null){
                if (!isCorrectDateAndArtist(v.getTitle().getPlainText(), v.getMediaGroup().getDescription().getPlainTextContent(),
                        event.getDate(), event.getArtist().getSearchTerms(), v.getAuthors().get(0).getName())) {
                    continue;
                }
            }
            cleanedList.add(v);
        }
        event.setVideoCount(String.valueOf(cleanedList.size()));
        return cleanedList;

    }

    private boolean isCorrectDateAndArtist(String videoTitle, String videoDescription, Date eventDate,
                                           String artistTerms, String author) {

        boolean validVideo = false;
        loadOpeners();
        System.out.println("Checking video.. title: " + videoTitle +
        		" description: "+ videoDescription +
				" eventDate: "+ eventDate.toGMTString() +
				" artistTerms: "+ artistTerms +
				" author: "+ author);

        
        Calendar cal=Calendar.getInstance();
        cal.setTime(eventDate);
        String titleAndDescription = videoTitle + " " + videoDescription;
        String year =  String.valueOf(cal.get(Calendar.YEAR));
        String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);  //+1?
        ArrayList<String> regexs = getRegex(month, date, year);

        for(String regex: regexs){
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(titleAndDescription);
            while(m.find()){
                validVideo = true;
                break;    
            }
            if(validVideo==true)break;
        }
        if(author.equals("ChesterCopperPot5")){
        	System.out.println("excluding Copperpot vid");
        	return false;
        }
        
        if(validVideo){
        	return true;
        }else{
        	System.err.println("Returned false 3");
            return false;
        }
    }
    
    void loadOpeners(){
    	if(pm==null){
    		pm = PMF.get().getPersistenceManager();
    	}
    	if(openers==null){
    		Query q = pm.newQuery(Artist.class);
    		@SuppressWarnings("unchecked")
			List<Artist> results = (List<Artist>) q.execute();
			if (!results.isEmpty()) {
				for (Artist a : results) {
					if (a.getName().equals("Openers")) {
						openers = a;
					}
				}
			} 
    	}
    }

    private ArrayList<String> getRegex(String monthNum, String date, String year){
        ArrayList<String> regexs = new ArrayList<String>();

        String yearFirstTwo = year.substring(0,2);
        String yearSecondTwo = year.substring(2,4);
        String monthNamesWithBars = getMonthNamesWithBars(monthNum);
        String allowZeroMonth = date.length()==2?"":"0?";
        String allowZeroDate = monthNum.length()==2?"":"0?";

        //without singleDate 0 logic
//        regexs.add("0?"+monthNum+"+[-/.]0?"+date+"[-/.]("+yearFirstTwo+")?"+yearSecondTwo);
//        regexs.add("("+yearFirstTwo+")?"+yearSecondTwo+"[-/.]0?"+monthNum+"[-/.]0?"+date+"");
//        regexs.add("("+monthNamesWithBars+"){1}\\s"+date+"(rd|st|th|nd)?,?\\s("+yearFirstTwo+")?"+yearSecondTwo);

        //advanced logic where 017 (for example) is not allowed
        regexs.add(allowZeroMonth+monthNum+"+[-/.]"+allowZeroDate+date+"[-/.]("+yearFirstTwo+")?"+yearSecondTwo);
        regexs.add("("+yearFirstTwo+")?"+yearSecondTwo+"[-/.]"+allowZeroMonth+monthNum+"[-/.]"+allowZeroDate+date+"");
        regexs.add("("+monthNamesWithBars+"){1}\\s"+allowZeroDate+date+"(rd|st|th|nd)?,?\\s("+yearFirstTwo+")?"+yearSecondTwo);

//        System.out.println("Regexs" + StringUtil.join(regexs));
        
        return regexs;
    }

    String getMonthNamesWithBars(String monthNum){
        populateMonthNames();
        String[] thisMonthNames = monthNames.get(monthNum);
        return Joiner.on("|").join(thisMonthNames);
    }

    void populateMonthNames(){
        if(monthNames.size() == 0){
            monthNames.put("1", new String[]{"Jan", "January"});
            monthNames.put("2", new String[]{"Feb", "February"});
            monthNames.put("3", new String[]{"Mar", "March"});
            monthNames.put("4", new String[]{"April", "Apr"});
            monthNames.put("5", new String[]{"May"});
            monthNames.put("6", new String[]{"Jun", "June"});
            monthNames.put("7", new String[]{"Jul", "July"});
            monthNames.put("8", new String[]{"Aug", "August"});
            monthNames.put("9", new String[]{"Sep", "Sept", "September"});
            monthNames.put("10", new String[]{"Oct", "October"});
            monthNames.put("11", new String[]{"Nov", "November"});
            monthNames.put("12", new String[]{"Dec", "December"});
        }


    }



}
