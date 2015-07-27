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

import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements Serializable, ResultSetExtractor<List<Publisher>>{

	@Autowired
	BookDAO bookDAO;

	private static final long serialVersionUID = 1619700647002508164L;
	private final String PUB_COLLECTION = "publishers";

	//CREATE
	public void create(Publisher pub) throws Exception {
		pub.setPublisherId(getNextSequenceId(PUB_COLLECTION));
		mongoOps.insert(pub, PUB_COLLECTION);
	}

	//READ
	public List<Publisher> readAll() throws Exception {
		return mongoOps.findAll(Publisher.class, PUB_COLLECTION);
	}

	//read one
	public Publisher readOne(long publisherId) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(publisherId));
		return mongoOps.findOne(query, Publisher.class, PUB_COLLECTION);
	}
	//read for PAGINATION
	public List<Publisher> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return mongoOps.findAll(Publisher.class, PUB_COLLECTION);

	}
	//read by Search
	public List<Publisher> readByPublisherName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherName or publisherAddress or publisherPhone like ?", new Object[] {searchString}, this);
	}
	
	//UPDATE
	public void update(Publisher pub) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(pub.getPublisherId()));

		Update update = new Update();
		update.set("publisherName", pub.getPublisherName());
		update.set("publisherAddress", pub.getPublisherAddress());
		update.set("publisherPhone", pub.getPublisherPhone());

		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Publisher.class, PUB_COLLECTION);
	}

	//DELETE
	public void delete(Publisher pub) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").exists(true));
		mongoOps.findAndRemove(query, Publisher.class, PUB_COLLECTION);
	}

	//RETURN List of Publishers
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		return null;
	}

}
