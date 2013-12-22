package com.notenet.feedparser.execute;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.httpclient.FeedParserHttpClient;
import com.notenet.feedparser.util.Constants;

public class FeedInfoIndexer {
	
	public static void main(String[] args) throws Exception {
		FeedParserHttpClient http = new FeedParserHttpClient(Constants.ES_HOST_HTTP);
		Gson gson = new Gson();
		//http.createIndex("feedindex");
		//http.putMapping("feedindex", "feed");
//		String indexContent = http.getIndex("feedindex", "feed", "http%3A%2F%2Fes.appleweblog.com%2Ffeed%2F");
//		System.out.println(indexContent);
		FeedInfoIndexer indexer = new FeedInfoIndexer();
		FeedSource[] feedSourceList = indexer.getFeedSourceList();
		for (FeedSource feedSource : feedSourceList) {
			String id = feedSource.getId();
			System.out.println("Start processing feed Source ID: " + id);
			if (http.getDocument("feedinfoindex", "feedinfo", id).contains("\"exists\":true")) {
				System.out.println("Feed: " + id + " exists");
			} else {
				String content = gson.toJson(feedSource);
				http.addDocument("feedindex", "feed", id, content);
			}
		}
	}
	
	public FeedSource[] getFeedSourceList() throws JsonParseException, IOException {
		String filePath = "c:/testdata/tech-7-20031020.txt";
		String fileContent = FileUtils.readFileToString(new File(filePath), "UTF-8");
		Gson gson = new Gson();
		FeedSource[] feedSourceList = gson.fromJson(fileContent, FeedSource[].class);
		return feedSourceList;
	}
	
	
}
