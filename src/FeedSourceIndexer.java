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


public class FeedSourceIndexer {
	public static void main(String[] args) throws Exception {
		HttpESHelper http = new HttpESHelper();
		//http.createIndex("feedindex");
		//http.putMapping("feedindex", "feed");
		FeedSourceIndexer indexer = new FeedSourceIndexer();
		Map<String, String> feedSourceList = indexer.getFeedSourceList();
		for (String id : feedSourceList.keySet()) {
			String feedSource = feedSourceList.get(id);
			http.addIndex("feedindex", "feed", id, feedSource);
		}
		//http.getIndex("feedindex", "feed", 1);
		
	}
	
	public Map<String, String> getFeedSourceList() throws JsonParseException, IOException {
		Map<String, String> feedSourceList = new HashMap<String, String>();
		String filePath = "testdata/tech-7-20031020.txt";
		JsonFactory jfactory = new JsonFactory();
		String fileContent = FileUtils.readFileToString(new File(filePath), "UTF-8");
		Gson gson = new Gson();
		String[] contentList = gson.fromJson(fileContent, String[].class);
		for (String content : contentList ) {
			JsonParser jParser = jfactory.createParser(content);
			String id = "";
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				if ("id".equals(fieldname)) {
					jParser.nextToken();
					id = jParser.getText();
					break;
				}
			}
			feedSourceList.put(id, content);
		}
		return feedSourceList;
	}
}
