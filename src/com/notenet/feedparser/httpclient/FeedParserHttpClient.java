package com.notenet.feedparser.httpclient;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class FeedParserHttpClient {
	private String host;
	
	public FeedParserHttpClient(String host) {
		this.host = host;
	}
	
	public void createIndex(String indexName, String setting) throws ClientProtocolException, IOException {
		String url = String.format(this.host + "/%s/", indexName);
		String resultString = this.sendPost(url, setting);
		System.out.println(resultString);
	}
	
	public void deleteIndex(String indexName) throws ClientProtocolException, IOException {
		String url = String.format(this.host + "/%s/", indexName);	
		String resultString = this.sendDelete(url);
		System.out.println(resultString);
	}
	
	public void getIndexStatus(String indexName) throws ClientProtocolException, IOException {
		String url = String.format(this.host + "/%s/_status", indexName);	
		String resultString = this.sendGet(url);
		System.out.println(resultString);
	}
	
	public void putMapping(String indexName, String type, String mappingJson) throws UnsupportedEncodingException, IOException {
		String url = String.format(this.host + "/%s/%s/_mapping", indexName, type);
		String resultString = this.sendPost(url, mappingJson);
		System.out.println(resultString);
	}
	
	public void deleteMapping(String indexName, String type) throws UnsupportedEncodingException, IOException {
		String url = String.format(this.host + "/%s/%s", indexName, type);
		String resultString = this.sendDelete(url);
		System.out.println(resultString);
	}
	
	public String getDocument(String indexName, String type, String id) throws ClientProtocolException, IOException {
		String url = String.format(host + "/%s/%s/%s", indexName, type, id);
		String resultString = this.sendGet(url);
		System.out.println(resultString);
		return resultString;
	}
	
	public void deleteDocument(String indexName, String type, String id) throws ClientProtocolException, IOException {
		String url = String.format(host + "/%s/%s/%s", indexName, type, id);
		String resultString = this.sendDelete(url);
		System.out.println(resultString);
	}
	
	public String getDocumentSource(String indexName, String type, String id) throws ClientProtocolException, IOException {
		String url = String.format(host + "/%s/%s/%s/_source", indexName, type, id);
		String resultString = this.sendGet(url);
		return resultString;
	}
	
	public void addDocument(String indexName, String type, String id, String indexEntity) throws UnsupportedEncodingException, IOException {
		System.out.println("Put into index for index name: " + indexName);
		String url = String.format(host + "/%s/%s/%s", indexName, type, id);
		String resultString = this.sendPost(url, indexEntity);
		System.out.println(resultString);
	}
	
	private String sendPost(String url, String contentEntity) throws ClientProtocolException, IOException {
		System.out.println("url: " + url);
		HttpClient client= new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new StringEntity(contentEntity, Charset.forName("UTF-8") );//FileUtils.readFileToString(new File("testdata/sampledoc")));
		request.setEntity(entity);
		HttpResponse result = client.execute(request);
		HttpEntity resultEntity = result.getEntity();
		InputStream inputStream = resultEntity.getContent();
		byte[] responseBody = IOUtils.toByteArray(inputStream);
		String resultString = new String(responseBody);
		return resultString;
	}
	
	private String sendGet(String url) throws ClientProtocolException, IOException {
		System.out.println("url: " + url);
		HttpClient client= new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse result = client.execute(request);
		HttpEntity resultEntity = result.getEntity();
		InputStream inputStream = resultEntity.getContent();
		byte[] responseBody = IOUtils.toByteArray(inputStream);
		String resultString = new String(responseBody, Charset.forName("UTF-8"));
		return resultString;
	}
		
	private String sendDelete(String url) throws ClientProtocolException, IOException {
		System.out.println("url: " + url);
		HttpClient client= new DefaultHttpClient();
		HttpDelete request = new HttpDelete(url);
		HttpResponse result = client.execute(request);
		HttpEntity resultEntity = result.getEntity();
		InputStream inputStream = resultEntity.getContent();
		byte[] responseBody = IOUtils.toByteArray(inputStream);
		String resultString = new String(responseBody, Charset.forName("UTF-8"));
		return resultString;
	}	
}
