package com.notenet.feedparser.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import com.notenet.feedparser.entity.FeedCategory;
import com.notenet.feedparser.entity.FeedSource;

public class FeedSourceHelper {
	
	public List<String> mergeTags(List<String> originalTagList, List<String> newTagList) {
		for (String newTag : newTagList) {
			if (!originalTagList.contains(newTag)) {
				System.out.println("Merge in new tag: " + newTag);
				originalTagList.add(newTag);
			}
		}
		return originalTagList;
	}
	
	public List<FeedCategory> mergeCategories(List<FeedCategory> originalCategoryList, List<FeedCategory> newCategoryList) {
		for (FeedCategory newCategory : newCategoryList) {
			boolean isFound= false;
			for (FeedCategory originalCategory : originalCategoryList) {
				if (originalCategory.getRoot().toString().equals(newCategory.getRoot().toString())) {
					isFound =true;
					break;
				}
			}
			if (!isFound) {
				System.out.println("Merge in new category: " + newCategory);
				originalCategoryList.add(newCategory);
			}
		}
		return originalCategoryList;
	}
	
	
	
	public FeedSource[] getFeedSourceList() throws JsonParseException, IOException {
		String filePath = "c:/testdata/feedsourcefile.txt";
		String fileContent = FileUtils.readFileToString(new File(filePath), "UTF-8");
		Gson gson = new Gson();
		FeedSource[] feedSourceList = gson.fromJson(fileContent, FeedSource[].class);
		return feedSourceList;
	}
}
