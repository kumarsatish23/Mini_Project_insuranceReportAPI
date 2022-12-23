package in.skr.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.skr.binding.CitizenPlan;

public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Serializable> {
	@Query("select distinct(planName) from CitizenPlan")
	public List<String> getPlanNames();
	@Query("select distinct(planStatus) from CitizenPlan")
	public List<String> getPlanStatus();
}
