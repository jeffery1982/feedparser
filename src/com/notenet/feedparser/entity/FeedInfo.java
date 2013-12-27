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
	private String link;
	private String author;
	private Date publishedDate;
	private String uri;
	
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getUrl() {
		return uri;
	}
	public void setUrl(String url) {
		this.uri = url;
	}
}
