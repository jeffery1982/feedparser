package com.notenet.feedparser.execute;
import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.util.FeedSourceHelper;

public class FeedSourceIndexer {
	
	public static void main(String[] args) throws Exception {
		HttpESHelper http = new HttpESHelper();
		Gson gson = new Gson();
		FeedSourceHelper feedSourceHelper = new FeedSourceHelper();
		//http.createIndex("feedindex");
		//http.putMapping("feedindex", "feed");
//		String indexContent = http.getIndex("feedindex", "feed", "http%3A%2F%2Fes.appleweblog.com%2Ffeed%2F");
//		System.out.println(indexContent);
		FeedSourceIndexer indexer = new FeedSourceIndexer();
		FeedSource[] feedSourceList = feedSourceHelper.getFeedSourceList();
		for (FeedSource feedSource : feedSourceList) {
			String id = feedSource.getId();
			System.out.println("Start processing feed Source ID: " + id);
			String indexContent = http.getIndex("feedindex", "feed", id);
			if (indexContent.contains("\"exists\":true")) {
				System.out.println("Feed: " + id + " exists");
				// Merge logic here
				System.out.println(indexContent);
				String indexSource = http.getIndexSource("feedindex", "feed", id);
				FeedSource feedSourceFromES = gson.fromJson(indexSource, FeedSource.class);
				feedSourceFromES.setTags(feedSourceHelper.mergeTags(feedSourceFromES.getTags(), feedSource.getTags()));
				feedSourceFromES.setCategories(feedSourceHelper.mergeCategories(feedSourceFromES.getCategories(), feedSource.getCategories()));
			} else {
				String content = gson.toJson(feedSource);
				http.addIndex("feedindex", "feed", id, content);
			}
		}
	}
	
	
	
	
}
