package com.notenet.feedparser.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import com.notenet.feedparser.execute.FeedSourceIndexer;
import com.notenet.feedparser.util.Constants;

public class HttpIndexerTest {
	public static FeedSourceIndexer feedSourceIndexer;
	
	@BeforeClass
	public static void beforeClass() {
		feedSourceIndexer = new FeedSourceIndexer(Constants.ES_HOST_HTTP);
	}
	
	@Test
	public void executeIndexTest() throws Exception {
		String filePath = "c:/testdata/feedsourcefile.txt";
		feedSourceIndexer.executeIndex(filePath);
	}
}
