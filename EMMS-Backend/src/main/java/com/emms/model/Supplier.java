//supplier
package com.emms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier {
	
	@Id
	@Column(name = "supplier_id")
	private int supplierId;
	@Column(name = "supplier_name")
	private String supplierName;
	@Column(name = "phone")
	private String  phone;
	@Column(name = "email")
	private String email;
	
	public Supplier() {
		
	}
	public Supplier(int supplierId, String supplierName, String phone, String email) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.phone = phone;
		this.email = email;
	}
	public int getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
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
	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", phone=" + phone + ", email="
				+ email + "]";
	}

}
