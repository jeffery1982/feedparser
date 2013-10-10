import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;

import java.util.LinkedHashSet;
import java.util.Set;


public class JestClientInit {
	private static JestClient jestClient;

    /**
     * 配置jest客户端,到时使用spring时,可以用配置方式 ,现在暂时使用new ...
     * 
     * @return
     */
    private static ClientConfig clientConfig() {
        String connectionUrl = "http://localhost:9200";
        ClientConfig clientConfig = new ClientConfig();
        Set<String> servers = new LinkedHashSet<String>();
        servers.add(connectionUrl);
        clientConfig.getServerProperties().put(ClientConstants.SERVER_LIST, servers);
        clientConfig.getClientFeatures().put(ClientConstants.IS_MULTI_THREADED, false);
        return clientConfig;
    }

    /**
     * 获取一个jest的对象
     * 
     * @return
     */
    public static JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setClientConfig(clientConfig());
        if (jestClient != null) {
        	jestClient = factory.getObject();
        }
        return jestClient;
    }
    
    public void closeJestClient(){  
        if(null != jestClient)  {
            jestClient.shutdownClient();  
        }
    } 
}
