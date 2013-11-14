package com.notenet.feedparser.execute;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpESHelper {
	private final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
		//HttpESHelper http = new HttpESHelper();
		//http.createIndex("feedindex");
		//http.putMapping("feedindex", "feed");
		//http.addIndex("feedindex", "feed", 1);
		//http.getIndex("feedindex", "feed", 1);
	}
	
	public void createIndex(String indexName) throws Exception {
		String url = String.format("http://localhost:9200/%s", indexName);
		String resultString = this.sendPost(url, FileUtils.readFileToString(new File("testdata/setting")));
		System.out.println(resultString);
	}
	
	public void putMapping(String indexName, String type) throws UnsupportedEncodingException, IOException {
		String url = String.format("http://localhost:9200/%s/%s/_mapping", indexName, type);
		String resultString = this.sendPost(url, FileUtils.readFileToString(new File("testdata/mappings")));
		System.out.println(resultString);
	}
	
	public String getIndex(String indexName, String type, String id) throws ClientProtocolException, IOException {
		String url = String.format("http://localhost:9200/%s/%s/%s", indexName, type, id);
		String resultString = this.sendGet(url);
		return resultString;
	}
	
	public void addIndex(String indexName, String type, String id, String indexEntity) throws UnsupportedEncodingException, IOException {
		System.out.println("Put into index for index name: " + indexName);
		String url = String.format("http://localhost:9200/%s/%s/%s", indexName, type, id);
		String resultString = this.sendPost(url, indexEntity);
		System.out.println(resultString);
	}
	
	private String sendPost(String url, String contentEntity) throws ClientProtocolException, IOException {
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
		HttpClient client= new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse result = client.execute(request);
		HttpEntity resultEntity = result.getEntity();
		InputStream inputStream = resultEntity.getContent();
		byte[] responseBody = IOUtils.toByteArray(inputStream);
		String resultString = new String(responseBody, Charset.forName("UTF-8"));
		return resultString;
	}	
}
