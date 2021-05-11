package by.home.library.dao.provider;

import by.home.library.dao.BookDAO;
import by.home.library.dao.UserDAO;
import by.home.library.dao.impl.FileBookDAO;
import by.home.library.dao.impl.FileUserDAO;

public final class DAOProvider {
	private static final DAOProvider instance = new DAOProvider();
	private final BookDAO fileBookImpl = new FileBookDAO();
	private final UserDAO fileUserImpl = new FileUserDAO();
	
	private DAOProvider() {}
	
	public static DAOProvider getInstance() {
		return instance;
	}
	
	public BookDAO getBookDAO( ) {
		return fileBookImpl;
	}

	public UserDAO getUserDAO() {
		return fileUserImpl;
	}
}
