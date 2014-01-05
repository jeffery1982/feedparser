package com.notenet.feedparser.util;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.notenet.feedparser.entity.FeedInfo;
import com.notenet.feedparser.entity.FeedSource;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


/**
 * FeedParser to download and import feed to database
 * @author Jeffery
 *
 */
public class FeedInfoHelper {
	public void updateFeedSource(String feedListFilePath) throws JsonParseException, IOException {
		FeedSource[] feedList = Utils.getFeedListByObjectType(feedListFilePath);
	}
	
	public void downloadContent(String url, String filePath) throws Exception {
		this.downloadFeed(url, filePath);
		this.executeFeedFromFile(filePath);
	}
	
	public void downloadFeed(String url, String filePath) throws IOException {
		URL yahoo = new URL(url);
        URLConnection yc = yahoo.openConnection();
        yc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        StringBuffer sbContent = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	sbContent.append(inputLine);
        }
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, sbContent.toString());
        in.close();
	}
	
	public List<FeedInfo> downloadFeedAndAnalyze(String urlString) throws IOException {
		List<FeedInfo> feedInfoList = new Vector<FeedInfo>();
		
		XmlReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new XmlReader(url);
			SyndFeed feed = new SyndFeedInput().build(reader);
			for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();) {
				SyndEntry entry = i.next();
				FeedInfo feedInfo = new FeedInfo();
				if (entry.getTitle() != null) {
					feedInfo.setTitle(entry.getTitle());
				}
				if (entry.getDescription() != null) {
					feedInfo.setDescription(entry.getDescription().getValue());
				}
				if (entry.getAuthor()!=null) {
					feedInfo.setAuthor(entry.getAuthor());
				}
				if (entry.getLink()!=null) {
					feedInfo.setLink(entry.getLink());
				}
				if (entry.getPublishedDate()!=null) {
					feedInfo.setPublishedDate(entry.getPublishedDate());
				}
				if (entry.getUri()!=null) {
					//TODO
				}
				feedInfoList.add(feedInfo);
			} 
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			if (reader !=null) {
				reader.close();
			}
		}
		return feedInfoList;
	}
	
	public void executeFeedFromFile(String filePath) throws Exception {
		File file = new File(filePath);
		SyndFeed feed = new SyndFeedInput().build(file);
		for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
			SyndEntry entry = (SyndEntry) i.next();
			System.out.println(entry.getTitle());
			System.out.println(entry.getPublishedDate());
			System.out.println(entry.getDescription());
		}  
	}
	
	public void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
    {
    	BufferedInputStream in = null;
    	FileOutputStream fout = null;
    	try
    	{
    		URL url = new URL(urlString);
    		URLConnection urlConnection = url.openConnection();
    		urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
    		in = new BufferedInputStream(url.openStream());
    		fout = new FileOutputStream(filename);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1)
    		{
    			fout.write(data, 0, count);
    		}
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    }
	
	public boolean isAtom(String filePath) throws ParserConfigurationException, SAXException, IOException{
	    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	    f.setNamespaceAware(true);
	    DocumentBuilder builder = f.newInstance().newDocumentBuilder();
	    Document doc = builder.parse(new File(filePath));
	    Element e = doc.getDocumentElement(); 
	    return e.getLocalName().equals("feed") && 
	            e.getNamespaceURI().equals("http://www.w3.org/2005/Atom");
	}
	
	public boolean isRSS(String filePath) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder
                .parse(new File(filePath));
        return doc.getDocumentElement().getNodeName().equalsIgnoreCase("rss");
    }
}
