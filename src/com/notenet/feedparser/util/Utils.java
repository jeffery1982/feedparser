package com.notenet.feedparser.util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notenet.feedparser.entity.FeedSource;


public class Utils {
	
	public static List<String> getFeedList(String filePath) throws JsonParseException, IOException {
		List<String> feedUrlList = new Vector<String>();
		JsonFactory jfactory = new JsonFactory();  
		JsonParser jParser = jfactory.createParser(new File(filePath));
		
		while (jParser.nextToken() != JsonToken.END_OBJECT) {  
			String fieldname = jParser.getCurrentName();  
			if ("url".equals(fieldname)) {  
				jParser.nextToken();
				System.out.println(jParser.getText());  
				feedUrlList.add(jParser.getText());
			}
		}
		return feedUrlList;
	}
	
	public static FeedSource[] getFeedListByObjectType(String filePath) throws JsonParseException, IOException {
		FeedSource[] arr = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			arr = objectMapper.readValue(new File(filePath),
					FeedSource[].class);
			System.out.println(arr.length);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	public static boolean checkURLValid(String urlString) {
		URLConnection connection;
		try {
			connection = new URL(urlString).openConnection();
			connection.connect();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
