package com.qz.app.filter;


import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.SearchDemp;

public class SortModel {

	private String name; // 显示的数据
	private String sortLetters; // 显示数据拼音的首字母
	public DepAndEmp.Userjson userjson;
	public SearchDemp.TRows rows;
	public boolean ischecked;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}



}
