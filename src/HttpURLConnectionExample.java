import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpURLConnectionExample {
	private final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		http.createIndex("feedindex");
		
	}
	
	public void createIndex(String indexName) throws Exception {
		String url = String.format("http://localhost:9200/%s", indexName);
		HttpClient client= new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new StringEntity(FileUtils.readFileToString(new File("testdata/feed.meta.txt")));
		request.setEntity(entity);
		HttpResponse result = client.execute(request);
		HttpEntity resultEntity = result.getEntity();
		InputStream inputStream = resultEntity.getContent();
		byte[] responseBody = IOUtils.toByteArray(inputStream);
		String resultString = new String(responseBody);
		System.out.println(resultString);
	}
 
	private void sendGet() throws Exception {
		String url = "http://www.google.com/search?q=mkyong";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}

}
