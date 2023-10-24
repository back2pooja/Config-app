package com.net.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.net.request.SearchRequest;
import com.net.response.SearchResponse;
import com.net.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportRestController {
	
	@Autowired
	private ReportService service;
	
	@GetMapping("/plans")
	public ResponseEntity <List<String>> getPlanNames(){
		List<String> planNames = service.getUniquePlanNames();
		return new ResponseEntity<>(planNames,HttpStatus.OK);
		
	}
	@GetMapping("/statuses")
	public ResponseEntity<List<String>> getPlanStatuses(){
		List<String> planStatus = service.getUniquePlanStatus();
		return new ResponseEntity<>(planStatus ,HttpStatus.OK);
	}
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> getSearch(@RequestBody SearchRequest request){
		List<SearchResponse> response = service.search(request);
		return new ResponseEntity<>(response ,HttpStatus.OK);
	}
	@GetMapping("/excel")
	public void excelReport(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename =data.xls";
		response.setHeader(headerKey, headerValue);
		
		service.generateExcel(response);
	}
	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response)throws Exception{
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.pdf";
		response.setHeader(headerKey, headerValue);
		
		service.generatePdf(response);
		
	}
	

}
