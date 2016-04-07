package com.laozhang.struts2.base.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


public class Pagination {
	public static final String SORT_DESC = "desc";

	public static final String SORT_ASC = "asc";
	
	private int pageNumber;
	
	private int pageSize = 10;
	
	private int firstResult;

	public int totalCount;

	public java.util.List list;

	private String searchId;

	private String sortCriterion;

	private String sortType;
	private int lastPage;
	
	private boolean exportAllData = false;
	
	private int[] viewPage;
	
	public int[] getViewPage() {
		int[] viewPage = null;
		if(lastPage>5){
			viewPage = new int[5];
			if(pageNumber <= 3){
				for(int i = 0; i < 5;i++){
					viewPage[i] = i+1;
				}
			}else if(pageNumber >= lastPage-2){
				for(int i = 0; i < 5;i++){
					viewPage[i] = lastPage-4+i;
				}
			}else{
				for(int i = 0; i < 5;i++){
					viewPage[i] = pageNumber-2+i;
				}
			}
			
		}else{
			viewPage = new int[lastPage];
			for(int i = 0; i < lastPage;i++){
				viewPage[i] = i+1;
			}
		}
		return viewPage;
	}
	
	public Pagination(){}
	
	public Pagination(HttpServletRequest request, String id, boolean exportDateAll) {
		setExportAllData(exportDateAll);
		init(
				request, id);
	}
	
	public Pagination(HttpServletRequest request, String id) {
		init(
				request, id);
	}
	
	public Pagination(HttpServletRequest request) {
		init(request);
	}
	
	private void init(HttpServletRequest request)
	{
		String pageSize=request.getParameter("limit");
		String sortname=request.getParameter("sortname");
		String sortorder=request.getParameter("sortorder");
		String page=request.getParameter("page");
		setPageNumber(Integer.parseInt(page));
		setSortType(sortorder);
		setSortCriterion(sortname);
		setPageSize(Integer.parseInt(pageSize));
	}
	
	private void init(HttpServletRequest request, String id)
	{
		String searchid = request.getParameter("searchid");
		this.searchId = id;
		if (!id.equals(searchid) || getExportAllData())
		{
			setPageNumber(1);
		}
		else
		{
			String page = request.getParameter("page");
			if (page == null || !StringUtils.isNumeric(page))
			{
				page = "1";
			}
			setPageNumber(Integer.parseInt(page));
			
			String sortorder=request.getParameter("sortorder");
			//String sort = request.getParameter("dir");
			if ("desc".equals(sortorder))
			{
				setSortType(Pagination.SORT_DESC);
			}
			else
			{
				setSortType(Pagination.SORT_ASC);
			}
			//String orderBy = request.getParameter("sort");
			String sortname=request.getParameter("sortname");
			setSortCriterion(sortname);
		}
	}
	
	
	

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstResult() {
		if (pageNumber > 0)
		{
			return (pageNumber - 1) * pageSize;
		}
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public java.util.List getList() {
		return list;
	}

	public void setList(java.util.List list) {
		this.list = list;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public boolean getExportAllData() {
		return exportAllData;
	}

	public void setExportAllData(boolean exportAllData) {
		this.exportAllData = exportAllData;
	}

	public void setViewPage(int[] viewPage) {
		this.viewPage = viewPage;
	}
	
	
}
