package com.notenet.feedparser.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedCategory {
	private List<String> root;
	private List<String> parent;
	private int level;
	
	@JsonProperty("root")
	public List<String> getRoot() {
		return root;
	}
	public void setRoot(List<String> root) {
		this.root = root;
	}
	@JsonProperty("parent")
	public List<String> getParent() {
		return parent;
	}
	public void setParent(List<String> parent) {
		this.parent = parent;
	}
	@JsonProperty("level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
