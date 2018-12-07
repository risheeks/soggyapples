package com.cs252.soggyapples;

public class Comment {
	String timestamp;
	String comment;
	String user;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Comment() {
		
	}
	public Comment(String timestamp, String comment, String user) {
		this.timestamp = timestamp;
		this.comment = comment;
		this.user = user;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
