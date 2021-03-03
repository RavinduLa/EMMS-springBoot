package com.emms.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jobs")
public class Job {
	
	@Id
	@Column(name="job_id")
	private int jobId;
	
	@Column(name="asset_id")
	private int assetId;
	
	@Column(name="serial_number")
	private String serialNumber;
	
	@Column(name="issue")
	private String issue;
	
	@Column(name="client")
	private String client;
	
	@Column(name="request_date_time")
	private LocalDateTime requestDateTime;
	
	@Column(name="completion_date_time")
	private LocalDateTime completionDateTime;
	
	@Column(name="status")
	private String status;
	
	

	public LocalDateTime getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(LocalDateTime requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public LocalDateTime getCompletionDateTime() {
		return completionDateTime;
	}

	public void setCompletionDateTime(LocalDateTime completionDateTime) {
		this.completionDateTime = completionDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
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

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", assetId=" + assetId + ", serialNUmber=" + serialNumber + ", issue=" + issue
				+ ", client=" + client + "]";
	}
	
	
	
	

}
