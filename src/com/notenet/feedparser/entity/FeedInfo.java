package com.notenet.feedparser.entity;

import java.util.Date;

/**
 * FeedInfo
 * @author Jeffery
 *
 */
public class FeedInfo {
	private String title;

	private String id;
	private String description;
	private Date timestamp;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
