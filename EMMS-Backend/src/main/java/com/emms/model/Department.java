package com.emms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="department")
public class Department {
	
	@Id
	@Column(name= "did")
	private int did;
	
	@Column(name= "department_name")
	private String departmentName;
	
	public Department() {
		
	}

	public Department(int did, String departmentName) {
		super();
		this.did = did;
		this.departmentName = departmentName;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department [did=" + did + ", departmentName=" + departmentName + "]";
	}
	
	

}
