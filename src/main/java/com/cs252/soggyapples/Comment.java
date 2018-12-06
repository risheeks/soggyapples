package com.cs252.soggyapples;

public class Comment {
	String timestamp;
	String comment;
	
	public Comment() {
		
	}
	public Comment(String timestamp, String comment) {
		this.timestamp = timestamp;
		this.comment = comment;
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
