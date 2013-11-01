import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedSource;


public class FeedSourceIndexer {
	public static void main(String[] args) throws Exception {
		HttpESHelper http = new HttpESHelper();
		Gson gson = new Gson();
		//http.createIndex("feedindex");
		//http.putMapping("feedindex", "feed");
		//http.getIndex("feedindex", "feed", 1);
		FeedSourceIndexer indexer = new FeedSourceIndexer();
		FeedSource[] feedSourceList = indexer.getFeedSourceList();
		for (FeedSource feedSource : feedSourceList) {
			String id = feedSource.getId();
			if (http.getIndex("feedindex", "feed", id).contains("\"exists\":true")) {
				System.out.println("Feed: " + id + " exists");
			} else {
				String content = gson.toJson(feedSource);
				http.addIndex("feedindex", "feed", id, content);
			}
		}
	}
	
	public FeedSource[] getFeedSourceList() throws JsonParseException, IOException {
		String filePath = "testdata/tech-7-20031020.txt";
		String fileContent = FileUtils.readFileToString(new File(filePath), "UTF-8");
		Gson gson = new Gson();
		FeedSource[] feedSourceList = gson.fromJson(fileContent, FeedSource[].class);
		return feedSourceList;
	}
}
