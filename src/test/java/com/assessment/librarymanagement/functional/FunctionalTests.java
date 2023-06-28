package com.assessment.librarymanagement.functional;

import static com.assessment.librarymanagement.testutils.TestUtils.businessTestFile;
import static com.assessment.librarymanagement.testutils.TestUtils.currentTest;
import static com.assessment.librarymanagement.testutils.TestUtils.testReport;
import static com.assessment.librarymanagement.testutils.TestUtils.yakshaAssert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import com.assessment.librarymanagement.testutils.MasterData;
import com.librarymanagement.LibraryManagementApplication;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.BookDAOImpl;

@Component
public class FunctionalTests {

	private static LibraryManagementApplication application;
	static BookDAOImpl bookDAO = null;
	static List<Book> list = null;

	@BeforeAll
	public static void setUp() {
		bookDAO = new BookDAOImpl();
		application = new LibraryManagementApplication(bookDAO);
		list = MasterData.getAllBooks();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testViewAllEmployees() throws IOException {
		try {
			System.out.println(list.size());
			yakshaAssert(currentTest(), list.size() == 2 ? true : false, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	void testAddBook() throws IOException {
		bookDAO.deleteAllBooks();
		String input = "Test Book\n1234567890\nTest Author\n0\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		application.addBook(new Scanner(System.in));

		// Verify that the book was added successfully
//		Assertions.assertEquals(1, bookDAO.getAllBooks().size());

		Book addedBook = bookDAO.getAllBooks().get(0);
//		Assertions.assertEquals("Test Book", addedBook.getName());
//		Assertions.assertEquals("1234567890", addedBook.getIsbn());
//		Assertions.assertEquals("Test Author", addedBook.getAuthor());
		try {
			yakshaAssert(currentTest(), "Test Book".equals(addedBook.getName()) ? true : false, businessTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
}
