package by.home.library.service.impl;

import java.util.ArrayList;

import by.home.library.bean.Book;
import by.home.library.dao.BookDAO;
import by.home.library.dao.exception.DAOException;
import by.home.library.dao.provider.DAOProvider;
import by.home.library.service.LibraryService;
import by.home.library.service.exception.ServiceException;

public class LibraryServiceImpl implements LibraryService {

	@Override
	public void addNewBook(Book book) throws ServiceException {
		// Реализация сервиса добавление новой книги

		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			BookDAO bookDAO = daoObjProvider.getBookDAO();

			bookDAO.add(book);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void addEditedBook(Book book) throws ServiceException {
		// Реализация сервиса обновления книги в базе

		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			BookDAO bookDAO = daoObjProvider.getBookDAO();

			bookDAO.update(book);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public int getNewBookId() throws ServiceException {
		// Получение нового уникального id для книги
		int newBookId;

		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			BookDAO bookDAO = daoObjProvider.getBookDAO();

			// можно переписать на работу с файлом
			// iterate new book id and check if it unique
			// max 1000 books

			for (newBookId = 1; newBookId < 1000; newBookId++) {
				if (bookDAO.isUniqueBookInfo(String.valueOf(newBookId), 0)) {
					break;
				}
			}

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return newBookId;
	}

	@Override
	public Book getBookById(int bookId) throws ServiceException {
		// Реализация сервиса получения книги по id
		Book book = null;

		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			BookDAO bookDAO = daoObjProvider.getBookDAO();

			book = bookDAO.getBookById(bookId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return book;
	}

	@Override
	public void deleteBook(int bookId) throws ServiceException {
		// Удаление книги по номеру

		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			BookDAO bookDAO = daoObjProvider.getBookDAO();

			bookDAO.deleteBook(bookId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public ArrayList<Book> searchBooks(String searchTitle, String searchAuthor, int searchYear, String searchGenre, String userRole)
			throws ServiceException {
		// Поиск книг по параметрам

		ArrayList<Book> searchBooks;
		
		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			BookDAO bookDAO = daoObjProvider.getBookDAO();

			searchBooks = bookDAO.searchBooks(searchTitle, searchAuthor, searchYear, searchGenre, userRole);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return searchBooks;
	}

	@Override
	public String createResponseString(ArrayList<Book> books) {
		final String BOOK_DELIMETER = "~@~";
		String result = null;

		for (Book book : books) {
			if (result != null) {
				result = result.concat(BOOK_DELIMETER);
			}

			if (result == null) {
				result = createBookDataString(book);
			} else {
				result = result.concat(createBookDataString(book));
			}			
		}

		return result;
	}

	private String createBookDataString(Book book) {

		final String DELIMETER = ";";
		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append("id=");
		strBuilder.append(book.getId());
		strBuilder.append(DELIMETER);
		strBuilder.append("title=");
		strBuilder.append(book.getTitle());
		strBuilder.append(DELIMETER);
		strBuilder.append("author=");
		strBuilder.append(book.getAuthor());
		strBuilder.append(DELIMETER);
		strBuilder.append("year=");
		strBuilder.append(book.getYear());
		strBuilder.append(DELIMETER);
		strBuilder.append("genre=");
		strBuilder.append(book.getGenre());
		strBuilder.append(DELIMETER);
		strBuilder.append("not4child=");
		strBuilder.append(book.isNotForChild());
		strBuilder.append(DELIMETER);
		strBuilder.append("available=");
		strBuilder.append(book.isAvailable());
		strBuilder.append(DELIMETER);
		strBuilder.append("owner=");
		strBuilder.append(book.getOwner());

		return strBuilder.toString();
	}
}
