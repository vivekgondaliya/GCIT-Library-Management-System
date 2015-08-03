package com.gcit.lms.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Borrower {
	@Id
	private long cardNo;
	private String name;
	private String address;
	private String phone;
	
//	private List<BookLoans> bookLoans; 
//	
//	public List<BookLoans> getBookLoans() {
//		return bookLoans;
//	}
//	public void setBookLoans(List<BookLoans> bookLoans) {
//		this.bookLoans = bookLoans;
//	}
	public long getCardNo() {
		return cardNo;
	}
	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
