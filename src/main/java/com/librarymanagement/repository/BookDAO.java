package com.librarymanagement.repository;

import java.util.List;

import com.librarymanagement.model.Book;

public interface BookDAO {
	void addBook(Book book);

	void updateBook(Book book);

	void deleteBook(Long id);

	List<Book> getAllBooks();

	Book getBookById(Long id);

	List<Book> searchBooksByName(String name);

	List<Book> searchBooksByISBN(String isbn);

	List<Book> searchBooksByAuthor(String author);

	void deleteAllBooks();
}
