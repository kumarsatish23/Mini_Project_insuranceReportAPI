package in.skr.service;

import java.awt.Color;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import in.skr.binding.CitizenPlan;
import in.skr.binding.SearchRequest;
import in.skr.repo.CitizenPlanRepository;
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository cpRepo;
	@Override
	public List<String> getPlanNames() {
		return cpRepo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatuses() {
		return cpRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> getCitizenPlans(SearchRequest request) {
		CitizenPlan entity=new CitizenPlan();
		if(request.getPlanName()!=null && !request.getPlanName().equals("")) {
			entity.setPlanName(request.getPlanName());
		}
		if(request.getPlanStatus()!=null && !request.getPlanStatus().equals("")) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if(request.getGender()!=null && !request.getGender().equals("")) {
			entity.setGender(request.getGender());
		}
		Example<CitizenPlan> example = Example.of(entity);
		List<CitizenPlan> records = cpRepo.findAll(example);
		return records;
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws Exception {
		
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Citizens Info");
		XSSFRow headerRow=sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("Id");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("SSN");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("Plan Name");
		headerRow.createCell(5).setCellValue("Plan Status");
		headerRow.createCell(6).setCellValue("Email");
		headerRow.createCell(7).setCellValue("Phone Number");
		
		List<CitizenPlan> records=cpRepo.findAll();
		
		int dataRowIndex=1;
		for(CitizenPlan record:records) {
			XSSFRow dataRow = sheet.createRow(dataRowIndex);
			
			dataRow.createCell(0).setCellValue(record.getCid());
			dataRow.createCell(1).setCellValue(record.getCName());
			dataRow.createCell(2).setCellValue(record.getSsn());
			dataRow.createCell(3).setCellValue(record.getGender());
			dataRow.createCell(4).setCellValue(record.getPlanName());
			dataRow.createCell(5).setCellValue(record.getPlanStatus());
			dataRow.createCell(6).setCellValue(record.getCEmail());
			dataRow.createCell(7).setCellValue(record.getPhNo());
			
			dataRowIndex++;


			
		}
		
		ServletOutputStream ops = response.getOutputStream(); 
		workbook.write(ops);
		workbook.close();
		ops.close();
		
		
	}

	@Override
	public void exportPdf(HttpServletResponse response) throws Exception{
		
		Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("Citizens Plans info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
		
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {0.7f, 2.5f, 2f, 1.5f, 3f, 2f, 4f, 3f});
        table.setSpacingBefore(10);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font f = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Id", f));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Name", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("SSN", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Plan Name", f));
        table.addCell(cell); 
        
        cell.setPhrase(new Phrase("Plan Status", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Email", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone Number", f));
        table.addCell(cell); 
		
        List<CitizenPlan> records=cpRepo.findAll();
        
        for(CitizenPlan record:records) {
        	table.addCell(String.valueOf(record.getCid()));
        	table.addCell(String.valueOf(record.getCName()));
        	table.addCell(String.valueOf(record.getSsn()));
        	table.addCell(String.valueOf(record.getGender()));
        	table.addCell(String.valueOf(record.getPlanName()));
        	table.addCell(String.valueOf(record.getPlanStatus()));
        	table.addCell(String.valueOf(record.getCEmail()));
        	table.addCell(String.valueOf(record.getPhNo()));
        }
        document.add(table);
        document.close();
	}

}
