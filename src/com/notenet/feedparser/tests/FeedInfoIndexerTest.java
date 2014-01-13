package com.notenet.feedparser.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import com.notenet.feedparser.execute.FeedInfoIndexer;
import com.notenet.feedparser.execute.FeedSourceIndexer;
import com.notenet.feedparser.util.Constants;

public class FeedInfoIndexerTest {
	public static FeedInfoIndexer feedInfoIndexer;
	
	@BeforeClass
	public static void beforeClass() {
		feedInfoIndexer = new FeedInfoIndexer(Constants.ES_HOST_HTTP);
	}
	
	@Test
	public void executeIndexTest() throws Exception {
		//String testFeedSourceFilePath = "testdata/sampledocument.txt";
		String testFeedSourceFilePath = "c:/testdata/feedsourcefile.txt";
		feedInfoIndexer.executeIndex(testFeedSourceFilePath);
	}
}
