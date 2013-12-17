package com.notenet.feedparser.jest;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;

import java.io.IOException;

import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.util.FeedSourceHelper;

public class FeedSourceIndexExecutor {
	FeedSourceHelper feedSourceHelper = new FeedSourceHelper();

	/**
	 * indexFeedSource
	 * @param client
	 * @throws IOException
	 */
	public void indexFeedSource(JestClient client) throws IOException {
		Long start = System.currentTimeMillis();
		// Use bulk
		/*
		 * if want't use bulk api: Index index = new Index.Builder(new
		 * Object()).index("articles").type("article").build();
		 * elasticSearchClient.execute(index);
		 */
		Bulk bulk = new Bulk("feed", "feedsource");
		FeedSource[] feedSourceList = feedSourceHelper.getFeedSourceList();

		for (int i = 0; i < feedSourceList.length; i++) {
			FeedSource feedSource = feedSourceList[i];
			Index index = new Index.Builder(feedSource).build();
			bulk.addIndex(index);
		}

		try {
			// Delete news index if it is exists
			//DeleteIndex deleteIndex = new DeleteIndex("feed");
			//client.execute(deleteIndex);

			// Create articles index
			CreateIndex createIndex = new CreateIndex("feed");
			
			client.execute(createIndex);

			client.execute(bulk);
			Long end = System.currentTimeMillis();
			System.out.println("time:" + (end - start) + "mm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
