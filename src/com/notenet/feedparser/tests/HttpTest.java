package com.notenet.feedparser.tests;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.notenet.feedparser.httpclient.FeedParserHttpClient;
import com.notenet.feedparser.util.Constants;

public class HttpTest {
	public static FeedParserHttpClient httpClient;
	
	@BeforeClass
	public static void beforeClass() {
		httpClient = new FeedParserHttpClient(Constants.ES_HOST_HTTP);
		
	}
	
	@Test
	public void getIndexStatusTest() throws ClientProtocolException, IOException {
		httpClient.getIndexStatus(Constants.FEED_SOURCE_INDEX_NAME);
	}
	
	@Test
	public void deleteIndexTest() throws ClientProtocolException, IOException {
		httpClient.deleteIndex(Constants.FEED_SOURCE_INDEX_NAME);
	}
	
	@Test
	public void createIndexTest() throws ClientProtocolException, IOException {
		String setting = FileUtils.readFileToString(new File("testdata/setting.txt"));
		httpClient.createIndex(Constants.FEED_SOURCE_INDEX_NAME, setting);
	}
	
	@Test
	public void createMappingTest() throws Exception {
		String mappingJson = FileUtils.readFileToString(new File("testdata/mapping.txt"));
		httpClient.putMapping(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, mappingJson);
	}
	
	@Test
	public void createDocumentTest() throws IOException {
		String id = "http%3A%2F%2Fes.appleweblog.com%2Ffeed%2F";
		String json = FileUtils.readFileToString(new File("testdata/sampledocument.txt"));
		httpClient.addDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id, json);
	}
	
	@Test
	public void getDocumentTest() throws ClientProtocolException, IOException {
		String id = "http%3A%2F%2Fwww.ringolab.com%2Fnote%2Fdaiya%2Fatom.xml";
		httpClient.getDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id);
	}
	
	@Test
	public void deleteMappingTest() throws UnsupportedEncodingException, IOException {
		httpClient.deleteMapping(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE);
	}
	
	@Test
	public void deleteDocumentTest() throws ClientProtocolException, IOException {
		String id = "http%3A%2F%2Fes.appleweblog.com%2Ffeed%2F";
		httpClient.deleteDocument(Constants.FEED_SOURCE_INDEX_NAME, Constants.FEED_SOURCE_INDEX_TYPE, id);
	}
}
