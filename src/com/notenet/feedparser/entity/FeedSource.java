package com.notenet.feedparser.entity;
import java.util.List;

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
	private List<String> tags;
	private List<FeedCategory> categories;
	private List<String> users;
	private boolean isValid;
	private String exceptionMessage;
	
	@JsonProperty("site")
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@JsonProperty("is_full_text")
	public String getIs_full_text() {
		return is_full_text;
	}

	public void setIs_full_text(String is_full_text) {
		this.is_full_text = is_full_text;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("lan")
	public String getLan() {
		return lan;
	}

	public void setLan(String lan) {
		this.lan = lan;
	}

	@JsonProperty("create_date")
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	@JsonProperty("update_date")
	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	@JsonProperty("tags")
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@JsonProperty("categories")
	public List<FeedCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<FeedCategory> categories) {
		this.categories = categories;
	}

	@JsonProperty("users")
	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	@JsonProperty("id")
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
	
	@JsonProperty("description")
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

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
