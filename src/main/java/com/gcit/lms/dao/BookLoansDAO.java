package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> implements ResultSetExtractor<List<BookLoans>>{

	@Autowired
	BookDAO bDAO;
	@Autowired
	LibraryBranchDAO lbDAO;
	@Autowired
	BorrowerDAO borDAO;
	
	private final String BOOKLOANS_COLLECTION = "bookLoans";
	//INSERT
	public void create(BookLoans bl) throws Exception {
		mongoOps.insert(bl, BOOKLOANS_COLLECTION);
	}

	//UPDATE
	public void update(BookLoans bl) throws Exception {
		template.update("update tbl_book_loans set dateOut=?, dueDate=?, dateIn=? where bookId=?, branchId=?, cardNo=? ",
				new Object[] { bl.getDateOut(), bl.getDueDate(), bl.getDateIn(), bl.getBook(), bl.getBranch(), bl.getBorrow()});
		
	}

	//DELETE
	public void delete(BookLoans bl) throws Exception {
		template.update("delete from tbl_book_loans where bookId =?, branchId=?, cardNo=?",
				new Object[] { bl.getBook(), bl.getBranch(), bl.getBorrow()});
		Query query = new Query();
		query.addCriteria(Criteria.where("bookId").exists(true));
		query.addCriteria(Criteria.where("branchId").exists(true));
		query.addCriteria(Criteria.where("cardNo").exists(true));
		
		mongoOps.findAndRemove(query, BookLoans.class, BOOKLOANS_COLLECTION);
	}

	//SELECT ALL
	public List<BookLoans> readAll() throws Exception {
		return mongoOps.findAll(BookLoans.class, BOOKLOANS_COLLECTION);
	}

	//SELECT ONE
	public BookLoans readOne(UUID bookId, UUID branchId, UUID cardNo) throws Exception {
		Query query = new Query(Criteria.where("bookId").is(bookId));
		query.addCriteria(Criteria.where("branchId").is(branchId));
		query.addCriteria(Criteria.where("cardNo").is(cardNo));
		
        return this.mongoOps.findOne(query, BookLoans.class, BOOKLOANS_COLLECTION);
	}

	//RETURN List of book_loans
	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		return null;
	}

	//book loans by Name
	public List<BookLoans> readByBookLoansName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<BookLoans>) template.query("select * from tbl_book_loans where cardNo like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<BookLoans> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<BookLoans>) template.query("select * from tbl_book_loans", this);
	}

}
