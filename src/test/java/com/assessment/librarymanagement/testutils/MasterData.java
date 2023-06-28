package com.assessment.librarymanagement.testutils;

import java.util.ArrayList;
import java.util.List;

import com.librarymanagement.model.Book;

public class MasterData {
	public static Book getBook() {
		Book book = new Book();
		book.setId(1L);
		book.setAuthor("author");
		book.setIsbn("isbn");
		book.setName("name");
		return book;
	}

	public static List<Book> getAllBooks() {
		List<Book> list = new ArrayList<>();

		Book book = new Book();
		book.setId(1L);
		book.setAuthor("author");
		book.setIsbn("isbn");
		book.setName("name");

		list.add(book);

		book.setId(2L);
		book.setAuthor("author 2");
		book.setIsbn("isbn ");
		book.setName("name 2");

		list.add(book);

		return list;
	}
}
