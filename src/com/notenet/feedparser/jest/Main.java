package com.notenet.feedparser.jest;

import io.searchbox.client.JestClient;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {

		// create client
		JestClient client = FeedParserJestClient.initJestClient();

		// create index
		FeedSourceIndexExecutor ni = new FeedSourceIndexExecutor();
		ni.indexFeedSource(client);

		// search
//		NewsSearch ns = new NewsSearch();
//		ns.search1(client, "Robert");
	}
}
