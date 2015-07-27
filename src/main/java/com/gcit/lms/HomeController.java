package com.gcit.lms;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {

	//dependency injection
	@Autowired
	AuthorDAO authorDAO;
	@Autowired
	BookDAO bookDAO;
	@Autowired
	PublisherDAO publisherDAO;
	@Autowired
	BorrowerDAO borrowerDAO;
	@Autowired
	LibraryBranchDAO branchDAO;
	@Autowired
	GenreDAO genreDAO;
	@Autowired
	BookLoansDAO bookLoansDAO;
	@Autowired
	BookCopiesDAO copiesDAO;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		System.err.println("RECEIVED YOUR GET REQUEST");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home";
	}

	//************CREATE*************
	@Transactional
	@RequestMapping(value="/author/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Author addAuthor(@RequestBody Author author){
		try {
			authorDAO.create(author);
			return author;
			//return "Author Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Author Add Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/book/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Book addBook(@RequestBody Book book){
		try {
			bookDAO.create(book);
			return book;
			//return "Book Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return book;
			//return "Book Add Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/publisher/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Publisher addPublisher(@RequestBody Publisher publisher){
		try {
			publisherDAO.create(publisher);
			return publisher;
			//return "Publisher Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Publisher Add Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/borrower/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Borrower addBorrower(@RequestBody Borrower borrow){
		try {
			borrowerDAO.create(borrow);
			return borrow;
			//return "Borrower Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Borrower Add Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/librarybranch/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public LibraryBranch addBranch(@RequestBody LibraryBranch branch){
		try {
			branchDAO.create(branch);
			return branch;
			//return "Library Branch Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Library Branch Add Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/genre/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Genre addGenre(@RequestBody Genre genre){
		try {
			genreDAO.create(genre);
			return genre;
			//return "Genre Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Genre Add Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/bookloans/add", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public BookLoans addBookLoans(@RequestBody BookLoans loan){
		try {
			bookLoansDAO.create(loan);
			return loan;
			//return "Book Loan Added Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Book Loan Add Failed.";
		}
	}

	//************READ************
	//AUTHORs
	@RequestMapping(value="/authors/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Author> getAuthors(){
		try {
			return authorDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//read PageNo
	@RequestMapping(value="/authors/get/{pageNo}/{pageSize}", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Author> getAuthors(@PathVariable int pageNo, @PathVariable int pageSize){
		try {
			return authorDAO.readAll(pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read One
	@RequestMapping(value = "/author/getOne", method = RequestMethod.POST, 
			consumes = "application/json")
	public Author getAuthor(@RequestBody Author auth) throws Exception {
		return authorDAO.readOne(auth.getAuthorId());
	}

	//read Search
	@RequestMapping(value = "/author/search", method = RequestMethod.POST, 
			consumes = "application/json")
	public Author searchAuthor(@RequestBody Author auth) throws Exception {
		return (Author) authorDAO.readByAuthorName(auth.getAuthorName());
	}

	//BOOKs
	@RequestMapping(value="/books/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> getBooks(){
		try {
			return bookDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read BranchId
	@RequestMapping(value="/books/{branchID}", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> getBranchBooks(@PathVariable int branchID){
		try {
			return bookDAO.branchBooks(branchID);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read One
	@RequestMapping(value = "/book/getOne", method = RequestMethod.POST, 
			consumes = "application/json")
	public Book getPublisher(@RequestBody Book book) throws Exception {
		return bookDAO.readOne(book.getBookId());
	}

	//PUBLISHERs
	@RequestMapping(value="/publishers/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Publisher> getPublishers(){
		try {
			return publisherDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read One
	@RequestMapping(value = "/publisher/getOne", method = RequestMethod.POST, 
			consumes = "application/json")
	public Publisher getPublisher(@RequestBody Publisher pub) throws Exception {
		return publisherDAO.readOne(pub.getPublisherId());
	}
	
	//BORROWERs
	@RequestMapping(value="/borrowers/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Borrower> getBorrowers(){
		try {
			System.out.println("Borrower Here.");
			return borrowerDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read One
	@RequestMapping(value = "/borrower/getOne", method = RequestMethod.POST, 
			consumes = "application/json")
	public Borrower getBorrower(@RequestBody Borrower bor) throws Exception {
		return borrowerDAO.readOne(bor.getCardNo());
	}

	//BRANCHEs
	@RequestMapping(value="/librarybranches/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<LibraryBranch> getBranches(){
		try {
			return branchDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read One
	@RequestMapping(value = "/librarybranch/getOne", method = RequestMethod.POST, 
			consumes = "application/json")
	public LibraryBranch getBranch(@RequestBody LibraryBranch lb) throws Exception {
		return branchDAO.readOne(lb.getBranchId());
	}

	//GENREs
	@RequestMapping(value="/genres/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Genre> getGenres(){
		try {
			return genreDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//read One
	@RequestMapping(value = "/genre/getOne", method = RequestMethod.POST, 
			consumes = "application/json")
	public Genre getGenre(@RequestBody Genre gen) throws Exception {
		return genreDAO.readOne(gen.getGenreId());
	}
	
	//BOOKLOANs
	@RequestMapping(value="/bookloans/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<BookLoans> getBookLoans(){
		try {
			return bookLoansDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//BOOKCOPIEs
	@RequestMapping(value="/books/copies/get", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<BookCopies> getBookCopies(){
		try {
			return copiesDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	//***********UPDATE**********
	@Transactional
	@RequestMapping(value="/author/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Author editAuthor(@RequestBody Author author){
		try {
			authorDAO.update(author);
			return author;
			//return "Author Updated Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Author Update Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/book/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Book editBook(@RequestBody Book book){
		try {
			bookDAO.update(book);
			return book;
			//return "Book Updated Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Book Update Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/borrower/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Borrower editBorrower(@RequestBody Borrower borrow){
		try {
			borrowerDAO.update(borrow);
			return borrow;
			//return "Borrower Updated Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Borrower Update Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/publisher/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Publisher editPublisher(@RequestBody Publisher publisher){
		try {
			publisherDAO.update(publisher);
			return publisher;
			//return "Book Updated Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Book Update Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/genre/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Genre editGenre(@RequestBody Genre genre){
		try {
			genreDAO.update(genre);
			return genre;
			//return "Genre Updated Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Genre Update Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/librarybranch/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public LibraryBranch editBranch(@RequestBody LibraryBranch branch){
		try {
			branchDAO.update(branch);
			return branch;
			//return "Library Branch Updated Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Library Branch Update Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/bookloans/edit", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public BookLoans editBookLoans(@RequestBody BookLoans loan){
		try {
			bookLoansDAO.update(loan);
			return loan;
			//return "Book Returned Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Book Return Failed.";
		}
	}

	//add Copies to branch
	@Transactional
	@RequestMapping(value="/books/copies/edit", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public BookCopies editBookCopies(@RequestBody BookCopies copy){
		try {
			copiesDAO.update(copy);
			return copy;
			//return "Number of Copies Updated.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Number of Copies Update Failed.";
		}
	}

	//***********DELETE************
	@Transactional
	@RequestMapping(value="/author/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Author deleteAuthor(@RequestBody Author author){
		try {
			authorDAO.delete(author);
			return author;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@RequestMapping(value="/book/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Book deleteBook(@RequestBody Book book){
		try {
			bookDAO.delete(book);
			return book;
			//return "Book Deleted Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Book Delete Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/borrower/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Borrower deleteBorrower(@RequestBody Borrower borrow){
		try {
			borrowerDAO.delete(borrow);
			return borrow;
			//return "Borrower Deleted Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Borrower Delete Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/publisher/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Publisher deletePublisher(@RequestBody Publisher publisher){
		try {
			publisherDAO.delete(publisher);
			return publisher;
			//return "Publisher Deleted Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Publisher Delete Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/genre/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public Genre deleteGenre(@RequestBody Genre genre){
		try {
			genreDAO.delete(genre);
			return genre;
			//return "Genre Deleted Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Genre Delete Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/librarybranch/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public LibraryBranch deleteBranch(@RequestBody LibraryBranch branch){
		try {
			branchDAO.delete(branch);
			return branch;
			//return "Library Branch Deleted Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Library Branch Delete Failed.";
		}
	}

	@Transactional
	@RequestMapping(value="/bookloans/delete", method={RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public BookLoans deleteBookLoans(@RequestBody BookLoans loan){
		try {
			bookLoansDAO.delete(loan);
			return loan;
			//return "Book Loan Record Deleted Sucessfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//return "Book Loan Record Delete Failed.";
		}
	}


	//**********ETC***********
	//count Authors: ERROR - Malformed JSON: Unexpected 'T'
	@RequestMapping(value="/author/count", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String getAuthorCount(){
		try {
			String result = "Total Authors in DB: " + authorDAO.readCount();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error - Fix Your Code.";
		}
	}

}