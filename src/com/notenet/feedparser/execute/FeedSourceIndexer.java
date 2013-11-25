package com.notenet.feedparser.execute;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedCategory;
import com.notenet.feedparser.entity.FeedSource;

public class FeedSourceIndexer {
	
	public static void main(String[] args) throws Exception {
		HttpESHelper http = new HttpESHelper();
		Gson gson = new Gson();
		//http.createIndex("feedindex");
		//http.putMapping("feedindex", "feed");
//		String indexContent = http.getIndex("feedindex", "feed", "http%3A%2F%2Fes.appleweblog.com%2Ffeed%2F");
//		System.out.println(indexContent);
		FeedSourceIndexer indexer = new FeedSourceIndexer();
		FeedSource[] feedSourceList = indexer.getFeedSourceList();
		for (FeedSource feedSource : feedSourceList) {
			String id = feedSource.getId();
			System.out.println("Start processing feed Source ID: " + id);
			String indexContent = http.getIndex("feedindex", "feed", id);
			if (indexContent.contains("\"exists\":true")) {
				System.out.println("Feed: " + id + " exists");
				// Merge logic here
				System.out.println(indexContent);
				String indexSource = http.getIndexSource("feedindex", "feed", id);
				FeedSource feedSourceFromES = gson.fromJson(indexSource, FeedSource.class);
				feedSourceFromES.setTags(indexer.mergeTags(feedSourceFromES.getTags(), feedSource.getTags()));
				feedSourceFromES.setCategories(indexer.mergeCategories(feedSourceFromES.getCategories(), feedSource.getCategories()));
			} else {
				String content = gson.toJson(feedSource);
				http.addIndex("feedindex", "feed", id, content);
			}
		}
	}
	
	public List<String> mergeTags(List<String> originalTagList, List<String> newTagList) {
		for (String newTag : newTagList) {
			if (!originalTagList.contains(newTag)) {
				System.out.println("Merge in new tag: " + newTag);
				originalTagList.add(newTag);
			}
		}
		return originalTagList;
	}
	
	public List<FeedCategory> mergeCategories(List<FeedCategory> originalCategoryList, List<FeedCategory> newCategoryList) {
		for (FeedCategory newCategory : newCategoryList) {
			boolean isFound= false;
			for (FeedCategory originalCategory : originalCategoryList) {
				if (originalCategory.getRoot().toString().equals(newCategory.getRoot().toString())) {
					isFound =true;
					break;
				}
			}
			if (!isFound) {
				System.out.println("Merge in new category: " + newCategory);
				originalCategoryList.add(newCategory);
			}
		}
		return originalCategoryList;
	}
	
	public FeedSource[] getFeedSourceList() throws JsonParseException, IOException {
		String filePath = "c:/testdata/feedsourcefile.txt";
		String fileContent = FileUtils.readFileToString(new File(filePath), "UTF-8");
		Gson gson = new Gson();
		FeedSource[] feedSourceList = gson.fromJson(fileContent, FeedSource[].class);
		return feedSourceList;
	}
	
	
}
