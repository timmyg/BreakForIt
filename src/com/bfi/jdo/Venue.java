package com.bfi.jdo;

import java.io.Serializable;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Venue implements Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Venue() {
	}
	
	public Venue(String name, String city, String state, String country, String searchTerm1, String searchTerm2)
    {
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.searchTerm1 = searchTerm1;
        this.searchTerm2 = searchTerm2;     
    }
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent 
    private String name; 

//    @Persistent(mappedBy = "venue")
//    @Element(dependent = "true")
    private Set<Key> events;
    
    @Persistent
    private Venue venue;
    
    @Persistent 
    private String city; 
    
    @Persistent 
    private String state;
    
    @Persistent 
    private String country; 
    
    @Persistent 
    private String searchTerm1;
    
    @Persistent 
    private String searchTerm2;
    

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Key> getEvents() {
		return events;
	}

	public void setEvents(Set<Key> events) {
		this.events = events;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSearchTerm1() {
		return searchTerm1;
	}

	public void setSearchTerm1(String searchTerm1) {
		this.searchTerm1 = searchTerm1;
	}

	public String getSearchTerm2() {
		return searchTerm2;
	}

	public void setSearchTerm2(String searchTerm2) {
		this.searchTerm2 = searchTerm2;
	}
	
	public String getLocation() {
		if(country != null && country.equals("US")){
			return getCity() + ", " + getState();
		}else{
			return getCity() + ", " + getCountry();
		}
	}

}