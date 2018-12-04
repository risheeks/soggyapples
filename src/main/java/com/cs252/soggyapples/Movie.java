package com.cs252.soggyapples;

import com.google.cloud.Date;

public class Movie {
	String title;
	String posterPath;
	String description;
	Date date;
	int votes;
	
	public Movie(String title) {
		this.title = title;
	}
	
	public Movie(String title, String poster, String desc, String date) {
		this.title = title;
		this.posterPath = poster;
		this.description = desc;
		this.date = Date.parseDate(date);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
