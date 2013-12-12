package com.notenet.feedparser.jest;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;

import java.util.LinkedHashSet;

public class FeedParserJestClient {
	private static JestClient client ;  
    /** 
     * 创建Client 
     * @return 
     */  
    public static JestClient initJestClient(){  
        // Configuration  
        ClientConfig clientConfig = new ClientConfig();  
        LinkedHashSet<String> servers = new LinkedHashSet<String>();  
        servers.add("http://localhost:9200/");
        clientConfig.getProperties().put(ClientConstants.SERVER_LIST, servers);  
          
        //为什么这个会错呀？  
        //clientConfig.getClientFeatures().put(ClientConstants.IS_MULTI_THREADED, false);  
        clientConfig.getProperties().put(ClientConstants.IS_MULTI_THREADED, false);  
  
        // Construct a new Jest client according to configuration via factory  
        JestClientFactory factory = new JestClientFactory();  
        factory.setClientConfig(clientConfig);  
        if(client == null){  
            client = factory.getObject();  
        } else{  
            System.out.println("client null");  
        }  
        return client;  
    }  
      
    /** 
     * Close JestClient 
     */  
    public void closeJestClient(){  
        if(null != client)  
            ((io.searchbox.client.JestClient) client).shutdownClient();  
    }  
}
