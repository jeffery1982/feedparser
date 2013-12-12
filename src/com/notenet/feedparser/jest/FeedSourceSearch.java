package com.notenet.feedparser.jest;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.notenet.feedparser.entity.FeedSource;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

public class FeedSourceSearch {
	public void search(JestClient client){  
        long start = System.currentTimeMillis();  
        try {  
            String query = "{\"query\":{\"term\":{\"title\":\"Robert\"}}}";  
            Search search = new Search(query);  
              
            // multiple index or types can be added.  
            search.addIndex("news");  
            System.out.println("index exist is "+search.isIndexExist("news"));  
              
            JestResult result = client.execute(search);  
            List<FeedSource> list = result.getSourceAsObjectList(FeedSource.class);  
            System.out.println("list:"+list.size());  
            for (FeedSource news : list) {  
				System.out.println("search result is 【Id:" + news.getId()
						+ ",Title:" + news.getTitle() + ",Content:"
						+ news.getDescription() + "】");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        long end = System.currentTimeMillis();  
        System.out.println("times out:"+(end-start)+"mm");  
    }  
      
	public void search1(JestClient client, String param) {
		try {
			long start = System.currentTimeMillis();
			QueryBuilder queryBuilder = QueryBuilders.queryString(param);
			Search search = new Search(
					Search.createQueryWithBuilder(queryBuilder.toString()));
			search.addIndex("news");
			search.addType("news");
			JestResult result = client.execute(search);
			List<FeedSource> list = result
					.getSourceAsObjectList(FeedSource.class);
			long end = System.currentTimeMillis();
			System.out.println("在100万条记录中,搜索新闻,共用时间 -->> " + (end - start)
					+ " 毫秒");
			for (int i = 0; i < list.size(); i++) {
				FeedSource news = (FeedSource) list.get(i);
				System.out.println(news.getId() + "   " + news.getTitle()
						+ "   " + news.getDescription());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
