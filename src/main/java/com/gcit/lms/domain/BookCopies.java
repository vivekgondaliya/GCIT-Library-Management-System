package com.gcit.lms.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BookCopies {
	
	private long bookId;
	private long branchId;
	private int noOfCopies;
	
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId (long branchId) {
		this.branchId = branchId;
	}
	public int getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	
}
