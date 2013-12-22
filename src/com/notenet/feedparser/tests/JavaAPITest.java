package com.notenet.feedparser.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.notenet.feedparser.javaclient.FeedParserJavaClient;
import com.notenet.feedparser.util.Constants;

public class JavaAPITest {
	public static FeedParserJavaClient javaClient;
	
	@BeforeClass
	public static void beforeClass() {
		javaClient = new FeedParserJavaClient(Constants.CLUSTER_NAME);
	}
	
	@AfterClass
	public static void afterClass() {
		if (javaClient != null) {
			javaClient.getClient().close();
			javaClient.getNode().close();
		}
	}
	
	@Test
	public void deleteIndexTest() {
		javaClient.deleteIndex(Constants.FEED_SOURCE_INDEX_NAME);
	}
	
	@Test
	public void createIndexTest() {
		javaClient.createIndex(Constants.FEED_SOURCE_INDEX_NAME);
	}
	
	@Test
	public void createMappingTest() throws Exception {
		String mappingJson = FileUtils.readFileToString(new File("testdata/mapping.txt"));
		javaClient.pushMapping(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, mappingJson);
	}
	
	@Test
	public void createDocumentTest() throws IOException {
		String json = FileUtils.readFileToString(new File("testdata/sampledocument.txt"));
		javaClient.createDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, json);
	}
	
	@Test
	public void getDocumentTest() {
		String id = "http%3A%2F%2Fes.appleweblog.com%2Ffeed%2F";
		javaClient.getDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id);
	}
	
	@Test
	public void searchDocumentTest() {
		String field = "title";
		String value = "AppleWeblog";
		javaClient.searchDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, field, value);
	}
}
