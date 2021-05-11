package by.home.library.service.provider;

import by.home.library.service.ClientService;
import by.home.library.service.LibraryService;
import by.home.library.service.impl.ClientServiceImpl;
import by.home.library.service.impl.LibraryServiceImpl;

public class ServiceProvider {
	private static final ServiceProvider instance = new ServiceProvider();
	
	private final ClientService clientService = new ClientServiceImpl();
	private final LibraryService libraryService = new LibraryServiceImpl();
	
	private ServiceProvider() {}
	
	public static ServiceProvider getInstance() {
		return instance;
	}
	
	public ClientService getClientService() {
		return clientService;
	}
	
	public LibraryService getLibraryService() {
		return libraryService;
	}
}
