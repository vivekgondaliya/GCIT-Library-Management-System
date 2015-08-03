package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements Serializable, ResultSetExtractor<List<Borrower>>{

	@Autowired
	BookLoansDAO bookLoanDAO;

	private static final long serialVersionUID = 1619700647002508164L;
	private final String BORROWER_COLLECTION = "borrowers";

	//CREATE
	public void create(Borrower borrow) throws Exception {
		borrow.setCardNo(getNextSequenceId(BORROWER_COLLECTION));
		mongoOps.insert(borrow, BORROWER_COLLECTION);
	}

	//READ
	public List<Borrower> readAll() throws Exception {
		return mongoOps.findAll(Borrower.class, BORROWER_COLLECTION);
	}

	//read ONE
	public Borrower readOne(long cardNo) throws Exception {
		Query query = new Query(Criteria.where("_id").is(cardNo));
        return this.mongoOps.findOne(query, Borrower.class, BORROWER_COLLECTION);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		return null;
	}

	//UPDATE
	public void update(Borrower borrow) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(borrow.getCardNo()));
 
		Update update = new Update();
		update.set("name", borrow.getName());
		update.set("name", borrow.getPhone());
		update.set("name", borrow.getAddress());
 
		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Borrower.class, BORROWER_COLLECTION);
	}

	//DELETE
	public void delete(Borrower borrow) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").exists(true));
		mongoOps.findAndRemove(query, Borrower.class, BORROWER_COLLECTION);
		}

}
