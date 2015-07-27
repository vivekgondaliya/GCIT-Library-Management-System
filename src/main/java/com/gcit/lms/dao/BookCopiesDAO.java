package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;


public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{

	private final String COPIES_COLLECTION = "bookCopies";
	
	//CREATE
	public void create(BookCopies copies) throws Exception {
		mongoOps.insert(copies, COPIES_COLLECTION);
	}

	//UPDATE
	public void update(BookCopies copies) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("bookId").is(copies.getBookId()));
		query.addCriteria(Criteria.where("branchId").is(copies.getBranchId()));
		
		Update update = new Update();
		update.set("noOfCopies", copies.getNoOfCopies());
 
		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), BookCopies.class, COPIES_COLLECTION);

	}

	//DELETE
	public void delete(BookCopies copy) throws Exception {

		Query query = new Query(Criteria.where("bookId").is(copy.getBookId()));
		query.addCriteria(Criteria.where("branchId").is(copy.getBranchId()));
		
		mongoOps.remove(query, COPIES_COLLECTION);
	}

	//SELECT ALL
	public List<BookCopies> readAll() throws Exception{
		return mongoOps.findAll(BookCopies.class);
	}

	//SELECT ONE based on ID
	public BookCopies readOne(int bookId, int branchId) throws Exception {
		Query query = new Query(Criteria.where("bookId").is(bookId));
		query.addCriteria(Criteria.where("branchId").is(branchId));
        return this.mongoOps.findOne(query, BookCopies.class, COPIES_COLLECTION);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {

		//create list
		List<BookCopies> noOfCopies = new ArrayList<BookCopies>();
//		while(rs.next()){
//			BookCopies copy = new BookCopies();
//			copy.setBookId((ObjectId) rs.getObject("bookId"));
//			copy.setBranchId((ObjectId) rs.getObject("branchId"));
//			copy.setNoOfCopies(rs.getInt("noOfCopies"));
//
//			noOfCopies.add(copy);
//		}
		return noOfCopies;
	}

	//update noOfCopies
	public BookCopies updateNoOfCopies(BookLoans bl){		
		BookCopies bc = new BookCopies();
		bc.setBookId(bl.getBook().getBookId());
		//bc.setBranchId(bl.getBranch().getBranchId());
		return bc;
	}

}
