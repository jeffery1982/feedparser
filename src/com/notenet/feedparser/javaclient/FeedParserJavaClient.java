package com.notenet.feedparser.javaclient;

import static org.elasticsearch.index.query.QueryBuilders.fieldQuery;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

/**
 * FeedParser java client
 * @author Jeffery
 *
 */
public class FeedParserJavaClient {
	private Node node;
	private Client client;
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public FeedParserJavaClient(String clusterName) {
		this.node = nodeBuilder().clusterName(clusterName).node();
		this.client = node.client();
	}
	
	public static void main(String args[]) throws IOException {
		// init node and client
		
		// prepare data
		// FeedSourceHelper helper = new FeedSourceHelper();
		// FeedSource[] feedSourceList = helper.getFeedSourceList();
		// Create Index and set settings and mappings

		// for (FeedSource feedSource : feedSourceList) {
		// client.prepareIndex();
		// }

		// client.prepareIndex("feedindex", "feed", "1")
		// .setSource(putJsonDocument("ElasticSearch: Java",
		// "ElasticSeach provides Java API, thus it executes all operations " +
		// "asynchronously by using client object..",
		// new Date(),
		// new String[]{"elasticsearch"},
		// "test")).execute().actionGet();
		//
		// client.prepareIndex("kodcucom", "article", "2")
		// .setSource(putJsonDocument("Java Web Application and ElasticSearch (Video)",
		// "Today, here I am for exemplifying the usage of ElasticSearch which is an open source, distributed "
		// +
		// "and scalable full text search engine and a data analysis tool in a Java web application.",
		// new Date(),
		// new String[]{"elasticsearch"},
		// "test")).execute().actionGet();
		//
		// getDocument(client, "kodcucom", "article", "1");
		//
		// updateDocument(client, "kodcucom", "article", "1", "title",
		// "ElasticSearch: Java API");
		// updateDocument(client, "kodcucom", "article", "1", "tags", new
		// String[]{"bigdata"});
		//
		// getDocument(client, "kodcucom", "article", "1");
		//
		// searchDocument(client, "kodcucom", "article", "title",
		// "ElasticSearch");
		//
		// deleteDocument(client, "kodcucom", "article", "1");

		//node.close();
	}
	
	public Client getClient() {
		return client;
	}
	
	public void createIndex(String indexName) {
		CreateIndexRequestBuilder createIndexRequestBuilder = this.client.admin()
				.indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();
	}
	
	public void deleteIndex(String indexName) {
		DeleteIndexRequestBuilder deleteIndexRequestBuilder = this.client.admin()
				.indices().prepareDelete(indexName);
		deleteIndexRequestBuilder.execute().actionGet();
	}
	
	public void deleteMapping(String indexName) {
		DeleteMappingRequestBuilder deleteMappingRequestBuilder = this.client.admin()
				.indices().prepareDeleteMapping(indexName);
		deleteMappingRequestBuilder.execute().actionGet();
	}

	public void pushMapping(String index, String type, String mappingJson) throws Exception {
		// If type does not exist, we create it
		boolean mappingExist = this.isMappingExist(index, type);
		if (!mappingExist) {
			if (mappingJson != null) {
				PutMappingRequestBuilder pmrb = client.admin().indices()
						.preparePutMapping(index).setType(type);

					pmrb.setSource(mappingJson);

				// Create type and mapping
				PutMappingResponse response = pmrb.execute().actionGet();
				if (!response.isAcknowledged()) {
					throw new Exception("Could not define mapping for type ["
							+ index + "]/[" + type + "].");
				} else {
					System.out.println("Mapping definition for ["+index+"]/["+type+"] succesfully created.");
				}
			} else {
				System.out.println("No mapping definition for ["+index+"]/["+type+"]. Ignoring.");
			}
		} else {
			System.out.println("Mapping ["+index+"]/["+type+"] already exists.");
		}
	}

	public boolean isMappingExist(String index, String type) {
		ClusterState cs = this.client.admin().cluster().prepareState()
				.setFilterIndices(index).execute().actionGet().getState();
		IndexMetaData imd = cs.getMetaData().index(index);

		if (imd == null)
			return false;

		MappingMetaData mdd = imd.mapping(type);

		if (mdd != null)
			return true;
		return false;
	}

	public boolean isIndexExist(String indexName) {
		ActionFuture<IndicesExistsResponse> exists = client.admin().indices()
				.exists(new IndicesExistsRequest(indexName));
		IndicesExistsResponse actionGet = exists.actionGet();
		return actionGet.isExists();
	}

	public boolean isDocumentExist(String indexName,
			String indexType, String id) {
		GetResponse getResponse = client.prepareGet(indexName, indexType, id)
				.execute().actionGet();
		return getResponse.isExists();
	}

	public void createDocument(String indexName, String indexType, String json){
	    System.out.println("Data creation");
	    IndexResponse response=null;
	    for (int i=0;i<10;i++){
	        //Map<String, Object> json = new HashMap<String, Object>();
	        //json.put("ATTR1", "new value" + i);
	        response = this.client.prepareIndex(indexName, indexType)
	                .setSource(json)
	                .setOperationThreaded(false)
	                .execute()
	                .actionGet();
	    }
	    String _index = response.getIndex();
	    String _type = response.getType();
	    long _version = response.getVersion();
	    System.out.println("Index : "+_index+"   Type : "+_type+"   Version : "+_version);
	    System.out.println("----------------------------------");
	}

	public void getDocument(String index, String type, String id) {

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

	public void updateDocument(String index, String type,
			String id, String field, String newValue) {

		Map<String, Object> updateObject = new HashMap<String, Object>();
		updateObject.put(field, newValue);

		client.prepareUpdate(index, type, id)
				.setScript("ctx._source." + field + "=" + field)
				.setScriptParams(updateObject).execute().actionGet();
	}

	public void updateDocument(String index, String type,
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

	public void searchDocument(String index, String type,
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

	public void deleteDocument(String index, String type,
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
