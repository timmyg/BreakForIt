package com.bfi.jdo;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Artist { 
	
	public Artist() {
	}
	
	public Artist(String name, String nameShort, String tag, String searchTerm1, String searchTerm2) {
		super();
        this.name = name;
        this.nameShort = nameShort;
        this.tag = tag;
        this.searchTerm1 = searchTerm1;
        this.searchTerm2 = searchTerm2;
    }
	
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent 
    @Unique
    private String name; 
    
    @Persistent
    @Unique
    private String nameShort;
    
    @Persistent
    @Unique
    private String tag;
    
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
	
	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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
	
	public String getSearchTerms() {
		String s = name;
		if(getSearchTerm1() != null){
			s = s + "," + getSearchTerm1();
		}
		return s;
	}
    
    

}