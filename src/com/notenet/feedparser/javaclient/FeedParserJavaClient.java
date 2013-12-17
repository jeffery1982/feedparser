package com.notenet.feedparser.javaclient;

import static org.elasticsearch.index.query.QueryBuilders.fieldQuery;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import com.notenet.feedparser.entity.FeedSource;
import com.notenet.feedparser.util.Constants;
import com.notenet.feedparser.util.FeedSourceHelper;

public class FeedParserJavaClient {
	public static void main(String args[]) throws IOException{
		// init node and client
        Node node     = nodeBuilder().clusterName(Constants.CLUSTER_NAME).node();
        Client client = node.client();
        // prepare data
//        FeedSourceHelper helper = new FeedSourceHelper();
//        FeedSource[] feedSourceList = helper.getFeedSourceList();
        // Create Index and set settings and mappings
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(Constants.FEED_SOURCE_INDEX_NAME);
        createIndexRequestBuilder.execute().actionGet();
        String source = "";
        createIndexRequestBuilder.addMapping(Constants.FEED_SOURCE_INDEX_TYPE, source);
        
//        for (FeedSource feedSource : feedSourceList) {
//        	client.prepareIndex();
//        }
        
//        client.prepareIndex("feedindex", "feed", "1")
//              .setSource(putJsonDocument("ElasticSearch: Java",
//                                         "ElasticSeach provides Java API, thus it executes all operations " +
//                                          "asynchronously by using client object..",
//                                         new Date(),
//                                         new String[]{"elasticsearch"},
//                                         "test")).execute().actionGet();
//        
//        client.prepareIndex("kodcucom", "article", "2")
//              .setSource(putJsonDocument("Java Web Application and ElasticSearch (Video)",
//                                         "Today, here I am for exemplifying the usage of ElasticSearch which is an open source, distributed " +
//                                         "and scalable full text search engine and a data analysis tool in a Java web application.",
//                                         new Date(),
//                                         new String[]{"elasticsearch"},
//                                         "test")).execute().actionGet();
//        
//        getDocument(client, "kodcucom", "article", "1");
//
//        updateDocument(client, "kodcucom", "article", "1", "title", "ElasticSearch: Java API");
//        updateDocument(client, "kodcucom", "article", "1", "tags", new String[]{"bigdata"});
//
//        getDocument(client, "kodcucom", "article", "1");
//
//        searchDocument(client, "kodcucom", "article", "title", "ElasticSearch");
//        
//        deleteDocument(client, "kodcucom", "article", "1");
        
        node.close();
    }
	
	private boolean isIndexExist(Client client, String indexName) {
	    ActionFuture<IndicesExistsResponse> exists = client.admin().indices()
	            .exists(new IndicesExistsRequest(indexName));
	    IndicesExistsResponse actionGet = exists.actionGet();
	    return actionGet.isExists();
	}
	
	
	
	public static Map<String, Object> putJsonDocument(FeedSource feedSource) {
		Map<String, Object> jsonDocument = new HashMap<String, Object>();

//		jsonDocument.put("title", feedSource.getCategories());
//		jsonDocument.put("content", content);
//		jsonDocument.put("postDate", postDate);
//		jsonDocument.put("tags", tags);
//		jsonDocument.put("author", author);

		return jsonDocument;
	}

	public static void getDocument(Client client, String index, String type,
			String id) {

		GetResponse getResponse = client.prepareGet(index, type, id).execute()
				.actionGet();
		Map<String, Object> source = getResponse.getSource();

		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");
	}

	public static void updateDocument(Client client, String index, String type,
			String id, String field, String newValue) {

		Map<String, Object> updateObject = new HashMap<String, Object>();
		updateObject.put(field, newValue);

		client.prepareUpdate(index, type, id)
				.setScript("ctx._source." + field + "=" + field)
				.setScriptParams(updateObject).execute().actionGet();
	}

	public static void updateDocument(Client client, String index, String type,
			String id, String field, String[] newValue) {

		String tags = "";
		for (String tag : newValue)
			tags += tag + ", ";

		tags = tags.substring(0, tags.length() - 2);

		Map<String, Object> updateObject = new HashMap<String, Object>();
		updateObject.put(field, tags);

		client.prepareUpdate(index, type, id)
				.setScript("ctx._source." + field + "+=" + field)
				.setScriptParams(updateObject).execute().actionGet();
	}

	public static void searchDocument(Client client, String index, String type,
			String field, String value) {

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(fieldQuery(field, value)).setFrom(0).setSize(60)
				.setExplain(true).execute().actionGet();

		SearchHit[] results = response.getHits().getHits();

		System.out.println("Current results: " + results.length);
		for (SearchHit hit : results) {
			System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
			System.out.println(result);
		}
	}

	public static void deleteDocument(Client client, String index, String type,
			String id) {

		DeleteResponse response = client.prepareDelete(index, type, id)
				.execute().actionGet();
		System.out.println("Information on the deleted document:");
		System.out.println("Index: " + response.getIndex());
		System.out.println("Type: " + response.getType());
		System.out.println("Id: " + response.getId());
		System.out.println("Version: " + response.getVersion());
	}
}
