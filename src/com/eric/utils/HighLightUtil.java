package com.eric.utils;

import java.util.ArrayList;
import java.util.List;

import com.eric.dto.SearchInfo;
import com.eric.model.Dictionary;
import com.eric.model.Item;
import com.eric.model.Standard;

/**
 * ������ʾ��ѯ���
 * 
 * @author Eric
 * 
 */
public class HighLightUtil {
	public static List<Item> highLightProcess(List<Item> items,
			String searchItem) {
		List<Item> result = new ArrayList<Item>();
		if (items != null) {
			for (Item item : items) {
				Item i = Item.newItem(item);
				result.add(i);
			}
			for (Item item : result) {
				if (!JudgeEnOrZh.isEnglish(searchItem)) {
					String tmp = item.getChineseName();
					item.setChineseName(tmp.replaceAll(searchItem,
							"<font color='red'>" + searchItem + "</font>"));
				} else {
					String tmp = item.getEnglishName();
					item.setEnglishName(tmp.replaceAll(searchItem,
							"<font color='red'>" + searchItem + "</font>"));
				}
				// System.out.println(item.getStandard());
			}
			return result;
		}

		return null;
	}

	public static List<SearchInfo> highLightProcess(List<Object> items,
			String searchItem, boolean flag) {
		List<SearchInfo> result = new ArrayList<SearchInfo>();
		if (items != null) {
			for (Object item : items) {
				result.add(SearchInfo.newSearchInfo(item));
			}
			for (SearchInfo item : result) {
				if (!JudgeEnOrZh.isEnglish(searchItem)) {
					String tmp = item.getChineseName();
					item.setChineseName(tmp.replaceAll(searchItem,
							"<font color='red'>" + searchItem + "</font>"));
				} else {
					String tmp = item.getEnglishName();
					item.setEnglishName(tmp.replaceAll(searchItem,
							"<font color='red'>" + searchItem + "</font>"));
				}
				// System.out.println(item.getStandard());
			}
			return result;
		}

		return null;
	}
	
	//分词高亮显示
	public static List<SearchInfo> highLightProcess(List<Object> items,
			List<String> split, boolean flag) {
		List<SearchInfo> result = new ArrayList<SearchInfo>();
		if (items != null) {
			for (Object item : items) {
				result.add(SearchInfo.newSearchInfo(item));
			}
			for (SearchInfo item : result) {
				
					String tmp = item.getChineseName();
					for(String t:split){
						if(tmp.contains(t)){
							item.setChineseName(tmp.replaceAll(t,
									"<font color='red'>" + t + "</font>"));
						}
					}
			}
			return result;
		}

		return null;
	}
	
	public static List<Standard> highLightProcess(List<Standard> items,
			String searchItem,int a) {
		List<Standard> result = new ArrayList<Standard>();
		if (items != null) {
			for (Standard item : items) {
				Standard i = Standard.newStandard(item);
				result.add(i);
			}
			for (Standard item : result) {
				if (!JudgeEnOrZh.isEnglish(searchItem)) {
					String tmp = item.getChineseName();
					item.setChineseName(tmp.replaceAll(searchItem,
							"<font color='red'>" + searchItem + "</font>"));
				} else {
					String tmp = item.getEnglishName();
					item.setEnglishName(tmp.replaceAll(searchItem,
							"<font color='red'>" + searchItem + "</font>"));
				}
				// System.out.println(item.getStandard());
			}
			return result;
		}

		return null;
	}
}
