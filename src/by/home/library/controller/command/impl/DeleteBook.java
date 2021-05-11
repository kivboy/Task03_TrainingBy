package by.home.library.controller.command.impl;

import by.home.library.bean.Book;
import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.MetaCommand;
import by.home.library.service.LibraryService;
import by.home.library.service.exception.ServiceException;
import by.home.library.service.provider.ServiceProvider;

public class DeleteBook extends MetaCommand implements Command {

	@Override
	public String execute(String request) {
		// удаление данных книги из библиотеке
		
		Book bookForDelete;
		String bookIdStr = null;
		int bookId = -1;
		
		String response = null;

		// delete_book id=1

		// удаляем комманду из запроса, оставляем только параметры
		request = request.substring(request.indexOf(REQUEST_COMMAND_DELIMETER) + 1);

		// get parameters from request and initialize variables

		if (super.countWords(request, REQUEST_PARAM_DELIMETER) != 1) {
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error in " + CommandName.DELETE_BOOK
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
		}

		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		LibraryService bookService = serviceProvider.getLibraryService();

		try {

			bookForDelete = bookService.getBookById(bookId);

			if (bookForDelete == null) {
				response = CODE_ERROR_NODATA + RESPONSE_CODE_DELIMETER + "No book found by id=" + bookId + "!";
				return response;
			}

			bookService.deleteBook(bookId);

			response = CODE_SUCCESS + RESPONSE_CODE_DELIMETER + bookForDelete.getId();

		} catch (ServiceException e) {
			// write log
			// e.printStackTrace(); // нужно ли выводить?
			response = CODE_ERROR_INTERNAL + RESPONSE_CODE_DELIMETER + "Error occured during delete book procedure!";
		}

		return response;
	}

}
