package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements ResultSetExtractor<List<LibraryBranch>>{

	@Autowired
	BookDAO bookDAO;
	@Autowired
	BookCopiesDAO bookCopiesDAO;
	@Autowired
	BookLoansDAO bookLoanDAO;

	private final String BRANCH_COLLECTION = "branches";
	//CREATE
	public void create(LibraryBranch lb) throws Exception {
		mongoOps.insert(lb, BRANCH_COLLECTION);
	}

	//READ
	public List<LibraryBranch> readAll() throws Exception {
		return mongoOps.findAll(LibraryBranch.class, BRANCH_COLLECTION);
	}

	//read ONE
	public LibraryBranch readOne(UUID branchId) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(branchId));
		return mongoOps.findOne(query, LibraryBranch.class, BRANCH_COLLECTION);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		return null;
	}

	//read Search
	public List<LibraryBranch> readByBranchName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchName like ? or branchAddress like ?", new Object[] {searchString, searchString}, this);
	}

	//read for PAGINATION
	public List<LibraryBranch> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return mongoOps.findAll(LibraryBranch.class, BRANCH_COLLECTION);

	}

	//UPDATE
	public void update(LibraryBranch lb) throws Exception {
		template.update("update tbl_library_branch set branchName=?, branchAddress=? where branchId=?",
				new Object[] { lb.getBranchName(), lb.getBranchAddress(), lb.getBranchId()});

	}

	//DELETE
	public void delete(LibraryBranch lb) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").exists(true));
		mongoOps.findAndRemove(query, LibraryBranch.class, BRANCH_COLLECTION);
	}

}
