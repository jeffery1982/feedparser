package com.notenet.feedparser.entity;

import java.util.List;

public class FeedCategory {
	private List<String> root;
	private List<String> parent;
	private int level;
	
	public List<String> getRoot() {
		return root;
	}
	public void setRoot(List<String> root) {
		this.root = root;
	}
	public List<String> getParent() {
		return parent;
	}
	public void setParent(List<String> parent) {
		this.parent = parent;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
