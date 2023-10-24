package com.net.service;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.net.request.SearchRequest;
import com.net.response.SearchResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	
	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatus();
	
	public List<SearchResponse> search(SearchRequest request);
	
	public void  generateExcel(HttpServletResponse response);
	
	public void generatePdf(HttpServletResponse response)throws DocumentException, IOException;

}
