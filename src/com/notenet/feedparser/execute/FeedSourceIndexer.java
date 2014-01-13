package com.notenet.feedparser.execute;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.entity.FeedSourceIndexDocument;
import com.notenet.feedparser.httpclient.FeedParserHttpClient;
import com.notenet.feedparser.util.Constants;
import com.notenet.feedparser.util.FeedSourceHelper;
import com.notenet.feedparser.util.Utils;

public class FeedSourceIndexer {
	private FeedParserHttpClient client;
	
	public FeedSourceIndexer(String host) {
		client = new FeedParserHttpClient(host);
	}
	
	public void executeIndex(String filePath) throws Exception {
		FeedSourceHelper feedSourceHelper = new FeedSourceHelper();
		Gson gson = new Gson();
		
		FeedSource[] feedSourceList = feedSourceHelper.getFeedSourceList(filePath);
		for (FeedSource feedSource : feedSourceList) {
			String id = feedSource.getId();
			feedSource.setValid(Utils.checkURLValid(feedSource.getUrl()));
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
				String content = gson.toJson(feedSourceFromES);
				client.addDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id, content);
			} else {
				String content = gson.toJson(feedSource);
				client.addDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id, content);
			}
		}
	}
	
	public FeedSource getFeedSourceFromIndex(String id) throws ClientProtocolException, UnsupportedEncodingException, IOException {
		String document = this.client.getDocument(
				Constants.FEED_SOURCE_INDEX_NAME,
				Constants.FEED_SOURCE_INDEX_TYPE,
				URLEncoder.encode(id, "UTF-8"));
		Gson gson = new Gson();
		FeedSource feedSource = gson.fromJson(document, FeedSourceIndexDocument.class).get_source();
		return feedSource;
	}
	
	public void setFeedSourceToIndex(FeedSource feedSource) throws UnsupportedEncodingException, IOException {
		Gson gson = new Gson();
		String feedSourceJsonString = gson.toJson(feedSource);
		this.client.addDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, feedSource.getId(), feedSourceJsonString);
	}
}
