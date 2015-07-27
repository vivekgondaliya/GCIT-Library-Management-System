package com.gcit.lms.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LibraryBranch {
	@Id	
	private UUID branchId = UUID.randomUUID();
	private String branchName;
	private String branchAddress;
	
	public UUID getBranchId() {
		return branchId;
	}
	public void setBranchId(UUID branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

}
