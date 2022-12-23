package in.skr.binding;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="CITIZEN_PLAN_INFO")
public class CitizenPlan {
	@Id
	@GeneratedValue
	private Integer cid;
	private String planName;
	private String planStatus;
	private String cName;
	private String cEmail;
	private String gender;
	private Long phNo;
	private Long ssn;


	
}
