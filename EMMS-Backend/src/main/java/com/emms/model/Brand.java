package com.emms.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="brand")
public class Brand {
	
	@Id
	@Column(name="brand_id")
	private int brandId;
	@Column(name="brand_name")
	private String brandName;
	@Column(name="categories")
	private String[] categoryList;
	
	public Brand() {
		
	}
	public Brand(int brandId, String brandName, String[] categoryList) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.categoryList = categoryList;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String[] getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(String[] categoryList) {
		this.categoryList = categoryList;
	}
	@Override
	public String toString() {
		return "Brand [brandId=" + brandId + ", brandName=" + brandName + ", categoryList="
				+ Arrays.toString(categoryList) + "]";
	}
	
	
	
	

}
