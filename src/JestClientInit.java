import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;

import java.util.LinkedHashSet;
import java.util.Set;


public class JestClientInit {
	private static JestClient jestClient;

    /**
     * ����jest�ͻ���,��ʱʹ��springʱ,���������÷�ʽ ,������ʱʹ��new ...
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
     * ��ȡһ��jest�Ķ���
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
