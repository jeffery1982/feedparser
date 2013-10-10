import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


public class SearchService {
    String indexName = "feed";
	/**
     * Create es feedinfo
     */
    public void builderSearchIndex(JestClient jestClient) {
        int num = 10;
        long start = System.currentTimeMillis();
        try {
        	IndicesExists indicesExists = new IndicesExists(indexName);
            DeleteIndex deleteIndex = new DeleteIndex(indexName);
            jestClient.execute(deleteIndex);

            CreateIndex createIndex = new CreateIndex(indexName);
            jestClient.execute(createIndex);
            // Bulk ��������1:��������2:��������(������(article)����������)
            Bulk bulk = new Bulk(indexName, indexName);
            for (int i = 0; i < num; i++) {
                FeedInfo news = new FeedInfo();
                news.setId(String.valueOf(i + 1));
                news.setTitle("elasticsearch RESTful��������-(java jest ʹ��[����])" + (i + 1));
                news.setDescription("�ð������ҽ�����jest(����������),������Ϊ���Ƿǳ������...���ES��������,����о�Դ�����...�ٵ�,��дһЩ����ES��Դ�����о�����,������ʱ�������ŵĽ׶�.��..(����,����)"
                        + (i + 1));
                bulk.addIndex(new Index.Builder(news).build());
            }
            jestClient.execute(bulk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("��������ʱ��:��������  " + num + "��¼,����ʱ�� -->> " + (end - start) + " ����");
    }
    
    /**
     * ��������
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
            System.out.println("��100������¼��,��������,����ʱ�� -->> " + (end - start) + " ����");
            return result.getSourceAsObjectList(FeedInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
