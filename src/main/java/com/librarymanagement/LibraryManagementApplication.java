package com.librarymanagement;

import java.util.List;
import java.util.Scanner;

import com.librarymanagement.model.Book;
import com.librarymanagement.repository.BookDAO;
import com.librarymanagement.repository.BookDAOImpl;

public class LibraryManagementApplication {
	private final BookDAO bookDAO;

	public LibraryManagementApplication(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public void run() {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("===== Library Management System =====");
			System.out.println("1. Add Book");
			System.out.println("2. Update Book");
			System.out.println("3. Delete Book");
			System.out.println("4. Search Books by Name");
			System.out.println("5. Search Books by ISBN");
			System.out.println("6. Search Books by Author");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline character

			switch (choice) {
			case 1:
				addBook(scanner);
				break;
			case 2:
				updateBook(scanner);
				break;
			case 3:
				deleteBook(scanner);
				break;
			case 4:
				searchBooksByName(scanner);
				break;
			case 5:
				searchBooksByISBN(scanner);
				break;
			case 6:
				searchBooksByAuthor(scanner);
				break;
			case 0:
				exit = true;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	public void addBook(Scanner scanner) {
		System.out.println("Enter book details:");
		System.out.print("Name: ");
		String name = scanner.nextLine();
		System.out.print("ISBN: ");
		String isbn = scanner.nextLine();
		System.out.print("Author: ");
		String author = scanner.nextLine();

		Book book = new Book();
		book.setName(name);
		book.setIsbn(isbn);
		book.setAuthor(author);

		bookDAO.addBook(book);
		System.out.println("Book added successfully!");
	}

	private void updateBook(Scanner scanner) {
		System.out.print("Enter book ID to update: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // Consume newline character

		Book book = bookDAO.getBookById(id);
		if (book != null) {
			System.out.println("Enter new book details:");
			System.out.print("Name: ");
			String name = scanner.nextLine();
			System.out.print("ISBN: ");
			String isbn = scanner.nextLine();
			System.out.print("Author: ");
			String author = scanner.nextLine();

			book.setName(name);
			book.setIsbn(isbn);
			book.setAuthor(author);

			bookDAO.updateBook(book);
			System.out.println("Book updated successfully!");
		} else {
			System.out.println("Book not found with ID: " + id);
		}
	}

	private void deleteBook(Scanner scanner) {
		System.out.print("Enter book ID to delete: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // Consume newline character

		Book book = bookDAO.getBookById(id);
		if (book != null) {
			bookDAO.deleteBook(id);
			System.out.println("Book deleted successfully!");
		} else {
			System.out.println("Book not found with ID: " + id);
		}
	}

	private void searchBooksByName(Scanner scanner) {
		System.out.print("Enter book name to search: ");
		String name = scanner.nextLine();

		List<Book> books = bookDAO.searchBooksByName(name);
		if (books.isEmpty()) {
			System.out.println("No books found with the name: " + name);
		} else {
			System.out.println("Books found with the name: " + name);
			for (Book book : books) {
				System.out.println(book);
			}
		}
	}

	private void searchBooksByISBN(Scanner scanner) {
		System.out.print("Enter book ISBN to search: ");
		String isbn = scanner.nextLine();

		List<Book> books = bookDAO.searchBooksByISBN(isbn);
		if (books.isEmpty()) {
			System.out.println("No books found with the ISBN: " + isbn);
		} else {
			System.out.println("Books found with the ISBN: " + isbn);
			for (Book book : books) {
				System.out.println(book);
			}
		}
	}

	private void searchBooksByAuthor(Scanner scanner) {
		System.out.print("Enter book author to search: ");
		String author = scanner.nextLine();

		List<Book> books = bookDAO.searchBooksByAuthor(author);
		if (books.isEmpty()) {
			System.out.println("No books found with the author: " + author);
		} else {
			System.out.println("Books found with the author: " + author);
			for (Book book : books) {
				System.out.println(book);
			}
		}
	}

	public static void main(String[] args) {
		BookDAOImpl bookDAO = new BookDAOImpl();
		bookDAO.initializeDatabase();
		LibraryManagementApplication application = new LibraryManagementApplication(bookDAO);
		application.run();
	}

}
