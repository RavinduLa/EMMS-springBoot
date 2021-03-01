package com.emms.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="equipment")
public class Equipment {
	
	@Id
	@Column(name="asset_id")
	private int assetId;
	
	@Column(name="serial_number")
	private String serialNumber;
	
	@Column(name="type")
	private String type;
	
	@Column(name="location")
	private String location;
	
	@Column(name="brand")
	private String brand;
	
	@Column(name="model")
	private String model;
	
	// java sql date data type is used. May need to change to java util Date
	@Column(name="purchase_date")
	private Date purchaseDate;
	
	@Column(name="warranty_months")
	private int warrantyMonths;
	
	public Equipment() {
		
	}

	public Equipment(int assetId, String serialNUmber, String type, String location, String brand, String model,
			Date purchaseDate, int warrantyMonths) {
		super();
		this.assetId = assetId;
		this.serialNumber = serialNUmber;
		this.type = type;
		this.location = location;
		this.brand = brand;
		this.model = model;
		this.purchaseDate = purchaseDate;
		this.warrantyMonths = warrantyMonths;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNUmber) {
		this.serialNumber = serialNUmber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getWarrantyMonths() {
		return warrantyMonths;
	}

	public void setWarrantyMonths(int warrantyMonths) {
		this.warrantyMonths = warrantyMonths;
	}

	@Override
	public String toString() {
		return "Equipment [assetId=" + assetId + ", serialNUmber=" + serialNumber + ", type=" + type + ", location="
				+ location + ", brand=" + brand + ", model=" + model + ", purchaseDate=" + purchaseDate
				+ ", warrantyMonths=" + warrantyMonths + "]";
	}
	
	

}
