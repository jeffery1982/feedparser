package com.notenet.feedparser.execute;

import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.httpclient.FeedParserHttpClient;
import com.notenet.feedparser.util.Constants;
import com.notenet.feedparser.util.FeedSourceHelper;

public class FeedSourceIndexer {
	private FeedParserHttpClient client;
	
	public FeedSourceIndexer(String host) {
		client = new FeedParserHttpClient(host);
	}
	
	public void executeIndex() throws Exception {
		FeedSourceHelper feedSourceHelper = new FeedSourceHelper();
		Gson gson = new Gson();
		
		FeedSource[] feedSourceList = feedSourceHelper.getFeedSourceList();
		for (FeedSource feedSource : feedSourceList) {
			String id = feedSource.getId();
			System.out.println("Start processing feed Source ID: " + id);
			String indexContent = client.getDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id);
			if (indexContent.contains("\"exists\":true")) {
				System.out.println("Feed: " + id + " exists");
				// Merge logic here
				System.out.println(indexContent);
				String indexSource = client.getDocumentSource(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id);
				FeedSource feedSourceFromES = gson.fromJson(indexSource, FeedSource.class);
				feedSourceFromES.setTags(feedSourceHelper.mergeTags(feedSourceFromES.getTags(), feedSource.getTags()));
				feedSourceFromES.setCategories(feedSourceHelper.mergeCategories(feedSourceFromES.getCategories(), feedSource.getCategories()));
			} else {
				String content = gson.toJson(feedSource);
				client.addDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id, content);
			}
		}
	}
	
	
	
	
}
