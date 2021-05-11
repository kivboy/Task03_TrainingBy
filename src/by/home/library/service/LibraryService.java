package by.home.library.service;

import java.util.ArrayList;

import by.home.library.bean.Book;
import by.home.library.service.exception.ServiceException;

public interface LibraryService {
	void addNewBook(Book book) throws ServiceException;
	void addEditedBook(Book book) throws ServiceException;
	int getNewBookId() throws ServiceException;
	Book getBookById(int bookId) throws ServiceException;
	void deleteBook(int bookId) throws ServiceException;
	ArrayList<Book> searchBooks(String searchTitle, String searchAuthor, int searchYear, String searchGenre, String userRole) throws ServiceException;
	String createResponseString(ArrayList<Book> books);
}
