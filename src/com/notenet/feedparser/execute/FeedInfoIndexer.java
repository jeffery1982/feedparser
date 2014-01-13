package com.notenet.feedparser.execute;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedInfo;
import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.httpclient.FeedParserHttpClient;
import com.notenet.feedparser.util.Constants;
import com.notenet.feedparser.util.FeedInfoHelper;
import com.notenet.feedparser.util.FeedSourceHelper;

public class FeedInfoIndexer {
	private FeedParserHttpClient client;
	
	public FeedInfoIndexer(String host) {
		client = new FeedParserHttpClient(host);
	}
	
	public void executeIndex(String feedSourceFilePath) {
		FeedInfoHelper feedInfoHelper = new FeedInfoHelper();
		FeedSourceHelper feedSourceHelper = new FeedSourceHelper();
		Gson gson = new Gson();
		try {
			FeedSource[] feedSourceList = feedSourceHelper
					.getFeedSourceList(feedSourceFilePath);
			// Iterate all the feed source list
			for (FeedSource feedSource : feedSourceList) {
				System.out.println("Download feed from url: " + feedSource.getUrl());
				List<FeedInfo> feedInfoList = feedInfoHelper.downloadFeedAndAnalyze(feedSource.getUrl());
				if (feedInfoList != null) {
					System.out.println("feedInfoList size: " + feedInfoList.size());
				}
				for (FeedInfo feedInfo : feedInfoList) {
					String content = gson.toJson(feedInfo);
					client.addDocument(Constants.FEED_INFO_INDEX_NAME,
							Constants.FEED_INFO_INDEX_TYPE,
							URLEncoder.encode(feedInfo.getLink(), "UTF-8"),
							content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Index feedinfo finished!");
	}
}
