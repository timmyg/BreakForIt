package com.bfi.jdo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.javatuples.Pair;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Tour {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Tour() {
    }


    public Tour(Date startDate, Date endDate, String name, String location, String alternateName1, String alternateName2) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.location = location;
        this.alternateName1 = alternateName1;
        this.alternateName2 = alternateName2;
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
		this.year = Long.valueOf(c.get(Calendar.YEAR));
//        for(String tourName: getFullTourNames()){
//            this.searchTerms.add(new Pair<String,String>(tourName, getDateString(getStartDate())+getDateString(getEndDate())));
//        }
//        this.searchTermsYears.add(new Pair<String,String>(getYearString(),getYearString()));
    }
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent 
    private Date startDate;
    @Persistent 
    private Date endDate;
    @Persistent 
    private String name;
    @Persistent 
    private String location;
    @Persistent 
    private String alternateName1;
    @Persistent 
    private String alternateName2;
    @Persistent 
    private Long year;
   
    public String getDateString(Date d){
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance(); // today
        c1.setTime(d);
        return sdf.format(c1.getTime());
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ///////////////////////////////////////////////////////////////////////////////////


	public Key getId() {
		return id;
	}


	public void setId(Key id) {
		this.id = id;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getAlternateName1() {
		return alternateName1;
	}


	public void setAlternateName1(String alternateName1) {
		this.alternateName1 = alternateName1;
	}


	public String getAlternateName2() {
		return alternateName2;
	}


	public void setAlternateName2(String alternateName2) {
		this.alternateName2 = alternateName2;
	}
	
	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}
}
