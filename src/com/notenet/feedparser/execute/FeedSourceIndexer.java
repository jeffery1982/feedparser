package com.notenet.feedparser.execute;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
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
			if (http.getIndex("feedindex", "feed", id).contains("\"exists\":true")) {
				System.out.println("Feed: " + id + " exists");
			} else {
				String content = gson.toJson(feedSource);
				http.addIndex("feedindex", "feed", id, content);
			}
		}
	}
	
	public FeedSource[] getFeedSourceList() throws JsonParseException, IOException {
		String filePath = "c:/testdata/tech-7-20031020.txt";
		String fileContent = FileUtils.readFileToString(new File(filePath));
		Gson gson = new Gson();
		FeedSource[] feedSourceList = gson.fromJson(fileContent, FeedSource[].class);
		return feedSourceList;
	}
	
	
}
