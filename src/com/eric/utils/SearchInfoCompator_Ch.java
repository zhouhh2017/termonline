package com.eric.utils;

import java.util.Comparator;

import com.eric.dto.SearchInfo;

//返回以chineseName字典排序的列表
public class SearchInfoCompator_Ch implements Comparator<SearchInfo>{

	@Override
	public int compare(SearchInfo o1, SearchInfo o2) {
		// TODO Auto-generated method stub
		return o1.getChineseName().compareTo(o2.getChineseName());
	}

}
