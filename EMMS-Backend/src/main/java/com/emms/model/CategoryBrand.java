package com.emms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category_brand")
public class CategoryBrand {
	
	@Id
	@Column(name="id")
	private int id;
	@Column(name="type")
	private String type;
	@Column(name="brand")
	private String brand;
	
	public CategoryBrand() {
		
	}

	public CategoryBrand(int id, String type, String brand) {
		super();
		this.id = id;
		this.type = type;
		this.brand = brand;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "CategoryBrand [id=" + id + ", type=" + type + ", brand=" + brand + "]";
	}
	
	
	
	
	

}
