package by.home.library.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import by.home.library.bean.Book;
import by.home.library.dao.BookDAO;
import by.home.library.dao.exception.DAOException;

public class FileBookDAO implements BookDAO {

	private static final String BOOK_FILE_SOURCE = "resources/Books.csv";
	private static final String DELIMETER = ";";

	@Override
	public void add(Book book) throws DAOException {
		// Добавление новой книги в файл

		String bookDataString = createBookDataString(book);

		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(BOOK_FILE_SOURCE, true));
			bufferedWriter.write("\r\n");
			bufferedWriter.write(bookDataString);

		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}
	}

	@Override
	public void delete(Book book) throws DAOException {
		// Удаление книги из справочника
		// загружаем все книги в массив, удаляем и остаток ыгружаем в файл
		ArrayList<Book> books;
		
		books = getAllBooks();

		books.remove(book);

		writeArrayBooks(books);

	}

	@Override
	public void update(Book book) throws DAOException {
		// Обновление данных книги в справочнике
		// загружаем все книги в массив, обновляем и выгружаем в файл
		ArrayList<Book> books;
		Book tmpBook;

		books = getAllBooks();

		for (int i = 0; i < books.size(); i++) {
			tmpBook = books.get(i);
			if (tmpBook.getId() == book.getId()) {
				books.set(i, book);
				break;
			}
		}

		writeArrayBooks(books);
	}

	@Override
	public void deleteBook(int idBook) throws DAOException {
		// Удаление книги из справочника по id
		// загружаем все книги в массив, удаляем и остаток ыгружаем в файл
		ArrayList<Book> books;
		Book tmpBook;

		books = getAllBooks();

		for (int i = 0; i < books.size(); i++) {
			tmpBook = books.get(i);
			if (tmpBook.getId() == idBook) {
				books.remove(i);
				break;
			}
		}

		writeArrayBooks(books);

	}

	@Override
	public boolean isUniqueBookInfo(String checkValue, int pos) throws DAOException {
		// проверка id пользователя на уникальность

		File bookDataSource = new File(BOOK_FILE_SOURCE);
		BufferedReader reader = null;
		boolean isUnique = true;
		String[] parsedData;
		String fileValue;

		try {
			reader = new BufferedReader(new FileReader(bookDataSource));
			String bookData = null;

			while ((bookData = reader.readLine()) != null) {

				parsedData = bookData.split(DELIMETER);
				fileValue = parsedData[pos].trim();
				if (fileValue.equals(checkValue)) {
					isUnique = false;
					break;
				}
			}
		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}

		return isUnique;
	}

	private String createBookDataString(Book book) {

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(book.getId());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.getTitle());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.getAuthor());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.getYear());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.getGenre());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.isNotForChild());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.isAvailable());
		strBuilder.append(DELIMETER);
		strBuilder.append(book.getOwner());

		return strBuilder.toString();
	}

	@Override
	public Book getBookById(int bookId) throws DAOException {
		// Поиск книги по id

		File bookDataSource = new File(BOOK_FILE_SOURCE);
		BufferedReader reader = null;

		Book book = null;

		String[] parsedData;
		String fileValue;

		try {
			reader = new BufferedReader(new FileReader(bookDataSource));
			String bookData = null;

			while ((bookData = reader.readLine()) != null) {

				parsedData = bookData.split(DELIMETER);
				fileValue = parsedData[0].trim();
				if (fileValue.equals(String.valueOf(bookId))) {
					book = parseBookData(bookData);
					break;
				}
			}
		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}

		return book;
	}

	private Book parseBookData(String bookData) throws DAOException {
		Book parsedBook = null;

		int bookId;
		int bookYear;
		int bookOwner;
		boolean bookNotForChild;
		boolean bookAvailable;

		String[] parsedString = bookData.split(DELIMETER);

		if (parsedString.length != 8) {
			throw new DAOException("Incorrect Book file structure!");
		}

		if (checkIntString(parsedString[0]) && checkIntString(parsedString[3]) && checkBooleanString(parsedString[5])
				&& checkBooleanString(parsedString[6]) && checkIntString(parsedString[7])) {

			bookId = Integer.parseInt(parsedString[0]);
			bookYear = Integer.parseInt(parsedString[3]);

			bookNotForChild = Boolean.parseBoolean(parsedString[5]);
			bookAvailable = Boolean.parseBoolean(parsedString[6]);

			bookOwner = Integer.parseInt(parsedString[7]);

		} else {
			throw new DAOException("Incorrect Book file data!");
		}

		parsedBook = new Book(bookId, parsedString[1], parsedString[2], bookYear, parsedString[4], bookNotForChild,
				bookAvailable, bookOwner);

		return parsedBook;
	}

	private boolean checkIntString(String string) {
		try {
			Integer.parseInt(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean checkBooleanString(String string) {
		return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
	}

	private ArrayList<Book> getAllBooks() throws DAOException {
		// загрузка всех книг в масив

		ArrayList<Book> books = new ArrayList<Book>();

		File bookDataSource = new File(BOOK_FILE_SOURCE);
		BufferedReader reader = null;
		Book book;

		try {
			reader = new BufferedReader(new FileReader(bookDataSource));
			String bookData = null;

			while ((bookData = reader.readLine()) != null) {
				book = parseBookData(bookData);

				if (book != null) {
					books.add(book);
				}
			}
		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}

		return books;
	}

	private void writeArrayBooks(ArrayList<Book> books) throws DAOException {

		BufferedWriter bufferedWriter = null;
		boolean isFisrt = true;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(BOOK_FILE_SOURCE, false));

			for (Book book : books) {
				String bookDataString = createBookDataString(book);

				if (!isFisrt) {
					bufferedWriter.write("\r\n");
				} else {
					isFisrt = false;
				}

				bufferedWriter.write(bookDataString);

			}

		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}
	}

	@Override
	public ArrayList<Book> searchBooks(String searchTitle, String searchAuthor, int searchYear, String searchGenre, String userRole)
			throws DAOException {

		ArrayList<Book> searchBooks = new ArrayList<Book>();
		
		ArrayList<Book> allBooks = this.getAllBooks();
		
		for (Book book : allBooks) {
			if (!book.isNotForChild() || (userRole != "child")) {
				
				if ((searchTitle != null) && (!searchTitle.isEmpty())) {
					if (book.getTitle().startsWith(searchTitle)) {
						searchBooks.add(book);
					}
				}
				
				if ((searchAuthor != null) && (!searchAuthor.isEmpty())) {
					if (book.getAuthor().startsWith(searchAuthor)) {
						searchBooks.add(book);
					}
				}
				
				if (searchYear > 0) {
					if (book.getYear() == searchYear) {
						searchBooks.add(book);
					}
				}
				
				if ((searchGenre != null) && (!searchGenre.isEmpty())) {
					if (book.getGenre().startsWith(searchGenre)) {
						searchBooks.add(book);
					}
				}
				
			}
		}
		

		return searchBooks;
	}
}
