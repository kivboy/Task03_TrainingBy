package by.home.library.main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import by.home.library.controller.Controller;

public class Main {

	public static void main(String[] args) {
		String activeUserId = null;
		String activeUserRole = null;
		String activeUserName = null;
		String response = null;
		String[] parsedString;
		boolean isExit = false;

		final String REQUEST_DATA_DELIMETER = ";";
		final String RESPONSE_DATA_DELIMETER = ";";

		Controller controller = new Controller();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		do {

			do {
				// логин в систему или выбор выйти из программы
				System.out.println("=================================================");
				System.out.println("================= Домашняя библиотека ===========");
				System.out.println("=================================================");
				System.out.println(" 1) Вход в систему");
				System.out.println(" 2) Выйти из программы");

				switch (getChoise("Введи номер операции", new Integer[] { 1, 2 })) {
				case 1: // ввод логина и пароля
					String login;
					String password;

					System.out.print("Введите логин : ");
					login = sc.next();
					System.out.print("Введите пароль : ");
					password = sc.next();

					response = controller.executeTask("sign_in login=" + login + ";password=" + password);

					if (response.startsWith("200") == true) {
						// успешный логин вернуло id пользователя
						response = response.substring(response.indexOf(' ') + 1);

						parsedString = response.split(RESPONSE_DATA_DELIMETER);
						activeUserId = parsedString[0];
						activeUserName = parsedString[1];
						activeUserRole = parsedString[2];

						System.out.println("Здравствуй " + activeUserName);

					} else if (response.startsWith("400") == true) {
						// ошибка входа
						System.out.println("Не правильный логин или пароль!");
					} else {
						// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
						// ошибки
						System.out.println(response.substring(response.indexOf(' ') + 1));
					}

					break;
				case 2: // выход из программы
					isExit = true;
					break;
				default:
					break;
				}

			} while ((activeUserId == null) && (!isExit));

			// выводим меню для разных ролей // предусмотреть sign_out <- activeUserId =
			// null
			while ((!isExit) && (activeUserId != null)) {

				switch (activeUserRole) {
				case "admin":

					switch (getChoise("Введи номер операции", menuForRole(activeUserRole))) {
					case 1: // регистрация

						// получение данных для регистрации пользователя

						response = controller.executeTask("register login=test;password=test;name=QQQ;role=admin");

						if (response.startsWith("200") == true) {
							// пользователь зарегистрирован
							System.out.println("Пользователь успешно зарегистрирован!");
						} else {
							// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
							// ошибки
							System.out.println(response.substring(response.indexOf(' ') + 1));
						}

						break;
					case 2: // завершение сессии

						activeUserId = null;
						activeUserRole = null;
						activeUserName = null;
						break;
					}

					break;

				case "user":

					switch (getChoise("Введи номер операции", menuForRole(activeUserRole))) {
					case 1: // добавить книгу

						// получение данных для регистрации книги
						
						response = controller.executeTask(
								"add_book title=my test;author=new author;year=2021;genre=стихи;notchild=false");

						if (response.startsWith("200") == true) {
							// книга добавлена успешно
							System.out.println("Книга добавлена успешно!");

						} else {
							// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
							// ошибки
							System.out.println(response.substring(response.indexOf(' ') + 1));
						}

						break;
					case 2: // редактировать книгу
						
						// получение данных для редактирования книги
						
						response = controller.executeTask("edit_book id=1;title=new test;author=author111");

						if (response.startsWith("200") == true) {
							// книга изменена успешно
							System.out.println("Книга отредактирована успешно!");
						} else {
							// возвращаем 300 (ошибка синтакса) или 400 (плохие данные) или 500 (ошибка
							// выполнения) и описание
							// ошибки
							System.out.println(response.substring(response.indexOf(' ') + 1));
						}
						
						break;
					case 3: // удалить книгу
						
						// получение данных для удалния книги
						
						response = controller.executeTask("delete_book id=1");

						if (response.startsWith("200") == true) {
							// книга удалена успешно
							System.out.println("Книга удалена успешно!");
						} else {
							// возвращаем 300 (ошибка синтакса) или 400 (плохие данные) или 500 (ошибка
							// выполнения) и описание
							// ошибки
							System.out.println(response.substring(response.indexOf(' ') + 1));
						}
						
						break;
					case 4: // найти книги
						
						// получение данных для поиска книги
						
						response = controller.executeTask("search_book title=my;role=" + activeUserRole);

						if (response.startsWith("200") == true) {
							// поиск книг выполнен успешно
							System.out.println("Поиск книг выполнен успешно!");

							System.out.println(response.substring(response.indexOf(' ') + 1));

						} else {
							// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
							// ошибки
							System.out.println(response.substring(response.indexOf(' ') + 1));
						}
						
						break;
					case 5: // завершить сессию
						activeUserId = null;
						activeUserRole = null;
						activeUserName = null;
						break;
					}

					break;

				default: // child

					switch (getChoise("Введи номер операции", menuForRole(activeUserRole))) {
					case 1: // найти книги
						
						// получение данных для поиска книги
						
						response = controller.executeTask("search_book title=my;role=" + activeUserRole);

						if (response.startsWith("200") == true) {
							// поиск книг выполнен успешно
							System.out.println("Поиск книг выполнен успешно!");

							System.out.println(response.substring(response.indexOf(' ') + 1));

						} else {
							// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
							// ошибки
							System.out.println(response.substring(response.indexOf(' ') + 1));
						}
						
						break;
					case 2: // завершить сессию
						activeUserId = null;
						activeUserRole = null;
						activeUserName = null;
						break;
					}
				}
			}

		} while (!isExit);

		System.out.println("Работа программы завершена.");
	}

	// Ввод числа с консоли
	public static int inputInt(String message) {
		int x;

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.print(message + " > ");
		while (!sc.hasNextInt()) {
			sc.nextLine();
			System.out.print(message + " > ");

		}
		x = sc.nextInt();

		return x;
	}

	public static int getChoise(String message, Integer[] choises) {
		int choise = -1;

		List<Integer> choiseList = Arrays.asList(choises);

		do {
			choise = inputInt(message);
		} while (!choiseList.contains(choise));

		return choise;
	}

	public static Integer[] menuForRole(String role) {
		Integer[] menuChoises;

		switch (role) {
		case "admin":
			System.out.println("1) Регистрация нового пользователя");
			System.out.println("2) Завершить сессию");
			menuChoises = new Integer[] { 1, 2 };
			break;
		case "user":
			System.out.println("1) Добавить книгу");
			System.out.println("2) Редактировать книгу");
			System.out.println("3) Удалить книгу");
			System.out.println("4) Найти книги по запросу");
			System.out.println("5) Завершить сессию");
			menuChoises = new Integer[] { 1, 2, 3, 4, 5 };
			break;
		default: // child
			System.out.println("1) Найти книги по запросу");
			System.out.println("2) Завершить сессию");
			menuChoises = new Integer[] { 1, 2 };
		}

		return menuChoises;

	}
}
