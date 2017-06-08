package com.wjg.base.shiro.vo;

import java.util.ArrayList;
import java.util.List;

public class DataTableResult<T> {
	private long iTotalRecords;//
	private List<T> aaData = new ArrayList<T>();
	private long iTotalDisplayRecords;
	private String sEcho;

	public DataTableResult() {
	}

	public DataTableResult(long iTotalRecords, List<T> aaData, String sEcho) {
		this(iTotalRecords, iTotalRecords, aaData, sEcho);
	}

	public DataTableResult(long iTotalRecords, long iTotalDisplayRecords, List<T> aaData,
			String sEcho) {
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords > 10000 ? 10000 : iTotalDisplayRecords;
		this.aaData = aaData;
		this.sEcho = sEcho;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	@Override
	public String toString() {
		return "DataTableResult [iTotalRecords=" + iTotalRecords + ", aaData=" + aaData
				+ ", iTotalDisplayRecords=" + iTotalDisplayRecords + ", sEcho=" + sEcho + "]";
	}

}
