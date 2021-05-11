package by.home.library.controller.command.impl;

import java.util.ArrayList;

import by.home.library.bean.Book;
import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.MetaCommand;
import by.home.library.service.LibraryService;
import by.home.library.service.exception.ServiceException;
import by.home.library.service.provider.ServiceProvider;

public class SearchBook extends MetaCommand implements Command {

	@Override
	public String execute(String request) {
		// поиск книг в библиотеке

		String bookSearchTitle = null;
		String bookSearchAuthor = null;
		String bookSearchYearStr = null;
		int bookSearchYear = -1;
		String bookSearchGenre = null;
		String userRole = null;
		
		ArrayList<Book>	booksList = null;
		String response = null;

		// search_book title=my test;author=new author;year=2021;genre=стихи;role=user
		// search_book title=my test;author=new author;year=2021;genre=стихи;role=child

		// удаляем комманду из запроса, оставляем только параметры
		request = request.substring(request.indexOf(REQUEST_COMMAND_DELIMETER) + 1);

		// get parameters from request and initialize variables

		if (super.countWords(request, REQUEST_PARAM_DELIMETER) < 2) {
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error in " + CommandName.SEARCH_BOOK
					+ " command syntax!";

			return response;
		} else {
			userRole = super.getValue("role", request, REQUEST_PARAM_DELIMETER);

			// обязательный параметр role
			if (userRole == null) {
				response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Parameter role is requered!";
				return response;
			}

			bookSearchTitle = super.getValue("title", request, REQUEST_PARAM_DELIMETER);
			bookSearchAuthor = super.getValue("author", request, REQUEST_PARAM_DELIMETER);
			bookSearchYearStr = super.getValue("year", request, REQUEST_PARAM_DELIMETER);

			// проверка входных данных
			try {
				if (bookSearchYearStr != null) {
					bookSearchYear = Integer.parseInt(bookSearchYearStr);
				}
			} catch (NumberFormatException e) {
				response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error parsing year!";
				return response;
			}

			bookSearchGenre = super.getValue("genre", request, REQUEST_PARAM_DELIMETER);
			
			if ((bookSearchTitle == null) &&			
				(bookSearchAuthor == null) &&
				(bookSearchYearStr == null) &&
				(bookSearchGenre == null)) {
				response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error no search parameters found!";
				return response;
			}
		}

		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		LibraryService bookService = serviceProvider.getLibraryService();

		try {

			booksList = bookService.searchBooks(bookSearchTitle, bookSearchAuthor, bookSearchYear, bookSearchGenre, userRole);

			if (booksList.size() == 0) {
				response = CODE_ERROR_NODATA + RESPONSE_CODE_DELIMETER + "No book found!";
				return response;
			}
			
			response = CODE_SUCCESS + RESPONSE_CODE_DELIMETER + bookService.createResponseString(booksList);

		} catch (ServiceException e) {
			// write log
			// e.printStackTrace(); // нужно ли выводить?
			response = CODE_ERROR_INTERNAL + RESPONSE_CODE_DELIMETER + "Error occured during search book procedure!";
		}

		return response;
	}

}
