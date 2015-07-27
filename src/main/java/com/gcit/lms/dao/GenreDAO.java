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

import com.gcit.lms.domain.Genre;

public class GenreDAO extends BaseDAO<Genre> implements Serializable, ResultSetExtractor<List<Genre>>{

	@Autowired
	BookDAO bookDAO;

	private static final long serialVersionUID = 1619700647002508164L;

	private final String GENRE_COLLECTION = "genres";

	//CREATE
	public void create(Genre gen) throws Exception {
		gen.setGenreId(getNextSequenceId(GENRE_COLLECTION));
		mongoOps.insert(gen, GENRE_COLLECTION);
	}

	//READ
	public List<Genre> readAll() throws Exception {
		return mongoOps.findAll(Genre.class, GENRE_COLLECTION);
	}

	//SELECT ONE
	public Genre readOne(long genreId) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(genreId));
		return mongoOps.findOne(query, Genre.class, GENRE_COLLECTION);
	}

	//list of Publishers
	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		return null;
	}

	//author by Name
	public List<Genre> readByGenreName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Genre>) template.query("select * from tbl_genre where genre_Name like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<Genre> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return mongoOps.findAll(Genre.class, GENRE_COLLECTION);
	}

	//UPDATE
	public void update(Genre gen) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(gen.getGenreId()));
 
		Update update = new Update();
		update.set("genreName", gen.getGenreName());
 
		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Genre.class, GENRE_COLLECTION);
	}

	//DELETE
	public void delete(Genre gen) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").exists(true));

		mongoOps.findAndRemove(query, Genre.class, GENRE_COLLECTION);
	}

}
