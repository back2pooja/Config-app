package com.net.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.net.entity.EligibilityDetails;
import com.net.repo.EligibilityDetailsRepo;
import com.net.request.SearchRequest;
import com.net.response.SearchResponse;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private EligibilityDetailsRepo eligiRepo;
	
	@Override
	public List<String> getUniquePlanNames() {
		return eligiRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStatus() {
		// TODO Auto-generated method stub
		return eligiRepo.findPlanStatuses();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		
		List<SearchResponse> response = new ArrayList();
		EligibilityDetails queryBuilder = new  EligibilityDetails();
		
		String planName = request.getPlanName();
		if(planName !=null && planName.equals("")){
			queryBuilder.setPlanName(planName);
			
		}
		String planStatus = request.getPlanStatus();
		if(planStatus!= null && planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}
		LocalDate planStartDate = request.getPlanStartDate();
		if(planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}
		LocalDate planEndDate = request.getPlanEndDate();
		if(planEndDate !=null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}
		Example<EligibilityDetails> example = Example.of(queryBuilder);
		
		List<EligibilityDetails> entites = eligiRepo.findAll(example);
		for(EligibilityDetails entity :entites) {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			response.add(sr);
		}
		
		
		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) {
		
		List<EligibilityDetails> entities = eligiRepo.findAll();
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("Search Report");
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Sr.No");
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(0).setCellValue("E-Mail");
		headerRow.createCell(0).setCellValue("Mobile");
		headerRow.createCell(0).setCellValue("Gender");
		headerRow.createCell(0).setCellValue("Email");
		headerRow.createCell(0).setCellValue("NSS");
		
		int i=1;

		
		for(EligibilityDetails entity:entities){
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getMobile());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(entity.getGender());
			dataRow.createCell(3).setCellValue(entity.getEmail());
			dataRow.createCell(4).setCellValue(entity.getSsn());
			i++;
			
		}
		

	}

	@Override
	public void generatePdf(HttpServletResponse response) throws IOException {
		List<EligibilityDetails> entities = eligiRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document,response.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("Search Report",font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
	         
	   // Font font = FontFactory.getFont(FontFactory.HELVETICA);
	    font.setColor(Color.WHITE);
		
	    PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        
        PdfPCell cell = new PdfPCell();
	    cell.setBackgroundColor(Color.BLUE);
	    cell.setPadding(5);
       
        
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Phone No", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);       
         
        for(EligibilityDetails entity:  entities) {
        	table.addCell(entity.getName());
        	table.addCell(entity.getEmail());
        	table.addCell(String.valueOf(entity.getMobile()));
        	table.addCell(String.valueOf(entity.getGender()));
        	table.addCell(String.valueOf(entity.getSsn()));
        	
        }
        document.add(table);
        document.close();
	}

}
