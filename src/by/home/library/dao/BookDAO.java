package by.home.library.dao;

import java.util.ArrayList;

import by.home.library.bean.Book;
import by.home.library.dao.exception.DAOException;

public interface BookDAO {
	void add(Book book) throws DAOException;
	void delete(Book book) throws DAOException;
	void update(Book book) throws DAOException;
	void deleteBook(int idBook) throws DAOException;
	boolean isUniqueBookInfo(String checkValue, int pos) throws DAOException;	
	Book getBookById(int bookId) throws DAOException;
	ArrayList<Book> searchBooks(String searchTitle, String searchAuthor, int searchYear, String searchGenre, String userRole) throws DAOException;
}
