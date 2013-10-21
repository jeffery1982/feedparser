package com.mediabox.feedparser.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is for feedsource which should be mapping with json file.
 * @author Jeffery
 *
 */
public class FeedSource {
	private String id;
	private String title;
	private String description;
	private String url;
	private String weight;
	private String site;
	private String is_full_text;
	private String type;
	private String lan;
	private String create_date;
	private String update_date;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty("title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("weight")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
}
