package com.bfi.jdo;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Event {
	
	public Event() {
	}
	
	public Event(Date date, Venue v, Artist a) {
		super();
		this.date = date;
		this.artist = a;
		this.venue = v;
		this.venueID = String.valueOf(v.getId().getId());
		this.artistID = String.valueOf(a.getId().getId());
//        this.artistKey = a.getId();
//        this.venueKey = v.getId();
    }
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id; 
	
//    @Persistent
//    private Key artistKey;
    
//    @Persistent
//    private Key venueKey;
    
    @Persistent
    private Event event;
    
    @Persistent
    private Date date;

    private String tag;
    
    @Persistent
    private String venueID;
    
    @Persistent
    private String artistID;
    
    @Persistent
    private String videoCount;
    
    @Persistent
    private Date updateTs;
    
    @Persistent(defaultFetchGroup = "true")
    @Unowned
    private Artist artist;
    
    @Persistent(defaultFetchGroup = "true")
    @Unowned
    private Venue venue;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

//	public Key getArtistKey() {
//		return artistKey;
//	}
//
//	public void setArtistKey(Key artistKey) {
//		this.artistKey = artistKey;
//	}
//
//	public Key getVenueKey() {
//		return venueKey;
//	}
//
//	public void setVenueKey(Key venueKey) {
//		this.venueKey = venueKey;
//	}
	
	public Artist getArtist() {
		return artist;
	}
	
	public Venue getVenue() {
		return venue;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getVideoCount() {
		if(videoCount == null){
			return "?";
		}else if(Long.valueOf(videoCount) >= 25){
			return "25+";
		}else{
			return videoCount;
		}
	}

	public void setVideoCount(String videoCount) {
		this.updateTs = new Date();
		this.videoCount = videoCount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	public String getDateFormatted() {
        return DateFormat.getDateInstance(DateFormat.LONG).format(getDate());
    }

	public String getFeedURL(){
        String feedURL = "http://gdata.youtube.com/feeds/api/videos?"+
                "q=";
        		if(this.getArtist() != null && this.getArtist().getName() != null){
        			feedURL = feedURL + this.getArtist().getName() + "+";
        		}
                if(this.getVenue() != null && this.getVenue().getSearchTerm1() != null){
                	feedURL = feedURL + this.getVenue().getSearchTerm1()+ "+";
                }
        		if(this.getSearchDate() != null){
        			feedURL = feedURL + this.getSearchDate();
        		}
        return feedURL.replaceAll(" ", "+");
    }
	
	public String getSearchDate(){
        Date date = getDate();
        Calendar d = Calendar.getInstance();
        d.setTime(date);
        String month = Integer.toString(d.get(Calendar.MONTH)+1);
//		String monthString = new SimpleDateFormat("MMMM").format(d);
        String day = Integer.toString(d.get(Calendar.DATE));
        String year = Integer.toString(d.get(Calendar.YEAR));

        // starts with ( ends with )
        return month + " " + day + " " + year;
    }
	
	public String getDateNumString() {
        SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        return new StringBuilder(dateformatYYYYMMDD.format(getDate())).toString();
    }

	public String getVenueID() {
		return venueID;
	}

	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}

	public String getArtistID() {
		return artistID;
	}

	public void setArtistID(String artistID) {
		this.artistID = artistID;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
	
}