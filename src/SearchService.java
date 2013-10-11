import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


public class SearchService {
    String indexName = "feed";
	
    /**
     * Create new index
     * @param jestClient
     * @param indexName
     * @throws Exception
     */
    public void createIndex(JestClient jestClient, String indexName) throws Exception {
    	CreateIndex createIndex = new CreateIndex(indexName);
        jestClient.execute(createIndex);
    }
    
    /**
     * Delete index
     * @param jestClient
     * @param indexName
     * @throws Exception
     */
    public void deleteIndex(JestClient jestClient, String indexName) throws Exception {
    	DeleteIndex deleteIndex = new DeleteIndex(indexName);
        jestClient.execute(deleteIndex);
    }
    
    public void addIndex(JestClient jestClient, String indexName) throws Exception {
    	int num = 10;
    	Bulk bulk = new Bulk(indexName, indexName);
        for (int i = 0; i < num; i++) {
            FeedInfo news = new FeedInfo();
            news.setId(String.valueOf(i + 1));
            news.setTitle("elasticsearch RESTful搜索引擎-(java jest 使用[入门])" + (i + 1));
            news.setDescription("好吧下面我介绍下jest(第三方工具),个人认为还是非常不错的...想对ES用来更好,多多研究源代码吧...迟点,会写一些关于ES的源代码研究文章,现在暂时还是入门的阶段.哈..(不敢,不敢)"
                    + (i + 1));
            bulk.addIndex(new Index.Builder(news).build());
        }
        jestClient.execute(bulk);
    }
    
    /**
     * Create es feedinfo
     */
    public void builderSearchIndex(JestClient jestClient) {
        long start = System.currentTimeMillis();
        try {
        	this.createIndex(jestClient, indexName);
        	this.addIndex(jestClient, indexName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("创建索引时间:共用时间 -->> " + (end - start) + " 毫秒");
    }
    
    /**
     * 搜索新闻
     * 
     * @param param
     * @return
     */
    public List searchsNews(JestClient jestClient, String param) {
        try {
            long start = System.currentTimeMillis();
            QueryBuilder queryBuilder = QueryBuilders.queryString(param);
            Search search = new Search(Search.createQueryWithBuilder(queryBuilder.toString()));
            search.addIndex(indexName);
            search.addType(indexName);
            JestResult result = jestClient.execute(search);
            long end = System.currentTimeMillis();
            System.out.println("在100万条记录中,搜索新闻,共用时间 -->> " + (end - start) + " 毫秒");
            return result.getSourceAsObjectList(FeedInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
