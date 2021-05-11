package by.home.library.controller.command.impl;

import by.home.library.bean.Book;
import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.MetaCommand;
import by.home.library.service.LibraryService;
import by.home.library.service.exception.ServiceException;
import by.home.library.service.provider.ServiceProvider;

public class EditBook extends MetaCommand implements Command {

	@Override
	public String execute(String request) {
		// изменение данных книги в библиотеке

		String bookNewTitle = null;
		String bookNewAuthor = null;
		String bookNewYearStr = null;
		int bookNewYear = -1;
		String bookNewGenre = null;
		String bookNewNotForChildStr = null;
		boolean bookNewNotForChild = false;

		String bookIdStr = null;
		int bookId = -1;

		Book editBook = null;
		String response = null;

		// edit_book id=1;title=my test;author=new
		// author;year=2021;genre=стихи;notchild=false

		// удаляем комманду из запроса, оставляем только параметры
		request = request.substring(request.indexOf(REQUEST_COMMAND_DELIMETER) + 1);

		// get parameters from request and initialize variables

		if (super.countWords(request, REQUEST_PARAM_DELIMETER) < 2) {
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error in " + CommandName.EDIT_BOOK
					+ " command syntax!";

			return response;
		} else {
			bookIdStr = super.getValue("id", request, REQUEST_PARAM_DELIMETER);

			// обязательный параметр id (int)
			if (bookIdStr == null) {
				response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Parameter id is requered!";
				return response;
			}

			// проверяем что число
			try {
				bookId = Integer.parseInt(bookIdStr);
			} catch (NumberFormatException e) {
				response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error parsing id to integer!";
				return response;
			}

			bookNewTitle = super.getValue("title", request, REQUEST_PARAM_DELIMETER);
			bookNewAuthor = super.getValue("author", request, REQUEST_PARAM_DELIMETER);
			bookNewYearStr = super.getValue("year", request, REQUEST_PARAM_DELIMETER);

			// проверка входных данных
			try {
				if (bookNewYearStr != null) {
					bookNewYear = Integer.parseInt(bookNewYearStr);
				}
			} catch (NumberFormatException e) {
				response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error parsing year!";
				return response;
			}

			bookNewGenre = super.getValue("genre", request, REQUEST_PARAM_DELIMETER);
			bookNewNotForChildStr = super.getValue("notchild", request, REQUEST_PARAM_DELIMETER);
			if (bookNewNotForChildStr != null) {
				bookNewNotForChild = Boolean.parseBoolean(bookNewNotForChildStr);
			}
		}

		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		LibraryService bookService = serviceProvider.getLibraryService();

		try {

			editBook = bookService.getBookById(bookId);

			if (editBook == null) {
				response = CODE_ERROR_NODATA + RESPONSE_CODE_DELIMETER + "No book found by id=" + bookId + "!";
				return response;
			}
			
			// обновляем объект полученными данными
			if (bookNewTitle != null) {
				editBook.setTitle(bookNewTitle);
			}

			if (bookNewAuthor != null) {
				editBook.setAuthor(bookNewAuthor);
			}

			if (bookNewYear != -1) {
				editBook.setYear(bookNewYear);
			}

			if (bookNewGenre != null) {
				editBook.setGenre(bookNewGenre);
			}
			
			if (bookNewNotForChildStr != null) {
				editBook.setNotForChild(bookNewNotForChild);
			}
						
			bookService.addEditedBook(editBook);

			response = CODE_SUCCESS + RESPONSE_CODE_DELIMETER + editBook.getId();

		} catch (ServiceException e) {
			// write log
			// e.printStackTrace(); // нужно ли выводить?
			response = CODE_ERROR_INTERNAL + RESPONSE_CODE_DELIMETER + "Error occured during edit book procedure!";
		}

		return response;
	}

}
