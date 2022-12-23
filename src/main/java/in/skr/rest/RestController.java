package in.skr.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import in.skr.binding.CitizenPlan;
import in.skr.binding.SearchRequest;
import in.skr.service.ReportService;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ReportApi.pdf";
        response.setHeader(headerKey, headerValue);
		reportService.exportPdf(response);
         
    }
	@GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=ReportApi.xls";
		
		response.setHeader(headerKey, headerValue);
		reportService.exportExcel(response);
		
	}

	@GetMapping("/PlanNames")
	public List<String> getPlanNames() {
		return reportService.getPlanNames();
	}
	@GetMapping("/PlanStatus")
	public List<String> getPlanStatuses() {
		return reportService.getPlanStatuses();
	}
	@GetMapping("/PlanStatus/{request}")
	public List<CitizenPlan> getCitizenPlans(SearchRequest request) {
		return reportService.getCitizenPlans(request);
	}
}
