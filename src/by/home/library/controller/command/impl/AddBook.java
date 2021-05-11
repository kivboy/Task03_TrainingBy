package by.home.library.controller.command.impl;

import by.home.library.bean.Book;
import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.MetaCommand;
import by.home.library.service.LibraryService;
import by.home.library.service.exception.ServiceException;
import by.home.library.service.provider.ServiceProvider;

public class AddBook extends MetaCommand implements Command {

	@Override
	public String execute(String request) {
		// добавление новой книги в библиотеку
		
		String bookTitle = null;
		String bookAuthor = null;
		String bookYearStr = null;
		int bookYear;
		String bookGenre = null;
		String bookNotForChildStr = null;
		boolean bookNotForChild;
		int bookId = -1;
				
		Book regBook = null;
		String response = null;
		
		//add_book title=my test;author=new author;year=2021;genre=стихи;notchild=false
		
		// удаляем комманду из запроса, оставляем только параметры
		request = request.substring(request.indexOf(REQUEST_COMMAND_DELIMETER) + 1);
		
		// get parameters from request and initialize variables
			
		if (super.countWords(request, REQUEST_PARAM_DELIMETER) != 5) {
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error in " + CommandName.ADD_BOOK + " command syntax!";
			
			return response;
		} else {
			bookTitle = super.getValue("title", request, REQUEST_PARAM_DELIMETER);
			bookAuthor = super.getValue("author", request, REQUEST_PARAM_DELIMETER);
			bookYearStr = super.getValue("year", request, REQUEST_PARAM_DELIMETER);					
			bookGenre = super.getValue("genre", request, REQUEST_PARAM_DELIMETER);
			bookNotForChildStr = super.getValue("notchild", request, REQUEST_PARAM_DELIMETER);
			
			bookNotForChild = Boolean.parseBoolean(bookNotForChildStr);	
		}
		
		// проверка входных данных
		try {
			bookYear = Integer.parseInt(bookYearStr);					
		} catch (NumberFormatException e) {			
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error parsing year!";
			return response;
		}
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		LibraryService bookService= serviceProvider.getLibraryService();
		
		regBook = new Book(bookTitle, bookAuthor, bookYear, bookGenre, bookNotForChild);
				
		try {
			
			bookId = bookService.getNewBookId();		
			regBook.setId(bookId);
			
			bookService.addNewBook(regBook);
			
			response = CODE_SUCCESS + RESPONSE_CODE_DELIMETER + regBook.getId();
			
		} catch (ServiceException e) {
			// write log
			//e.printStackTrace();  // нужно ли выводить?
			response = CODE_ERROR_INTERNAL + RESPONSE_CODE_DELIMETER + "Error occured during add book procedure!";
		}
		
		return response;
	}

}
