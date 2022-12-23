package in.skr.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import in.skr.binding.CitizenPlan;
import in.skr.binding.SearchRequest;

public interface ReportService {
	
	public List<String> getPlanNames();
	
	public List<String> getPlanStatuses();

	public List<CitizenPlan> getCitizenPlans(SearchRequest request);
	
	public void exportExcel(HttpServletResponse response) throws Exception;

	public void exportPdf(HttpServletResponse response) throws Exception;
	

}
