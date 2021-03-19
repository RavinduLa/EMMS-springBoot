//supplier alternative
package com.emms.model;

import java.util.Date;

//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="supplier2")
public class Supplier2 {
	
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "original_id")
	private int originalId;
	@Column(name = "supplier_name")
	private String supplierName;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String  email;
	@Column(name = "date")
	private Date dateCreated;
	@Column(name = "status")
	private String status;
	
	
	public Supplier2() {
		
	}
	public Supplier2(int id, int originalId, String supplierName, String phone, String email, Date dateCreated,
			String status) {
		super();
		this.id = id;
		this.originalId = originalId;
		this.supplierName = supplierName;
		this.phone = phone;
		this.email = email;
		this.dateCreated = dateCreated;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOriginalId() {
		return originalId;
	}
	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Supplier2 [id=" + id + ", originalId=" + originalId + ", supplierName=" + supplierName + ", phone="
				+ phone + ", email=" + email + ", dateCreated=" + dateCreated + ", status=" + status + "]";
	}
	

	
	
}
