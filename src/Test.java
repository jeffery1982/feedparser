import io.searchbox.client.JestClient;

import java.io.IOException;


public class Test {
	public static void main(String[] args) throws IOException{  
		JestClient client = JestClientInit.jestClient(); 
		SearchService searchService = new SearchService();
		searchService.builderSearchIndex(client);
		
	}
}
