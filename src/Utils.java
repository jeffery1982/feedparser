import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;


public class Utils {
	public static List<String> getFeedList(String filePath) throws JsonParseException, IOException {
		List<String> feedUrlList = new Vector<String>();
		JsonFactory jfactory = new JsonFactory();  
		JsonParser jParser = jfactory.createJsonParser(new File(filePath));
		while (jParser.nextToken() != JsonToken.END_OBJECT) {  
			String fieldname = jParser.getCurrentName();  
			if ("uri".equals(fieldname)) {  
				jParser.nextToken();  
				System.out.println(jParser.getText());  
				feedUrlList.add(jParser.getText());
			}
		}
		return feedUrlList;
	}
}
