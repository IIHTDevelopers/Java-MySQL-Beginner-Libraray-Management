package com.librarymanagement.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.librarymanagement.model.Book;

public class BookDAOImpl implements BookDAO {
	private final String jdbcUrl;
	private final String jdbcUsername;
	private final String jdbcPassword;

	public BookDAOImpl() {
		Properties properties = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		jdbcUrl = properties.getProperty("jdbc.url");
		jdbcUsername = properties.getProperty("jdbc.username");
		jdbcPassword = properties.getProperty("jdbc.password");

		initializeDatabase();
	}

	public void initializeDatabase() {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
			try (Statement statement = connection.createStatement()) {
				// Create database if it doesn't exist
				statement.executeUpdate("CREATE DATABASE IF NOT EXISTS library");
			}

			// Use the library database
			connection.setCatalog("library");

			try (Statement statement = connection.createStatement()) {
				// Create books table if it doesn't exist
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS books (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
						+ "name VARCHAR(100) NOT NULL," + "isbn VARCHAR(20) NOT NULL," + "author VARCHAR(100) NOT NULL"
						+ ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
	}

	@Override
	public void addBook(Book book) {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO books (name, isbn, author) VALUES (?, ?, ?)")) {
			statement.setString(1, book.getName());
			statement.setString(2, book.getIsbn());
			statement.setString(3, book.getAuthor());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBook(Book book) {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE books SET name = ?, isbn = ?, author = ? WHERE id = ?")) {
			statement.setString(1, book.getName());
			statement.setString(2, book.getIsbn());
			statement.setString(3, book.getAuthor());
			statement.setLong(4, book.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteBook(Long id) {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id = ?")) {
			statement.setLong(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();

		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getLong("id"));
				book.setName(resultSet.getString("name"));
				book.setIsbn(resultSet.getString("isbn"));
				book.setAuthor(resultSet.getString("author"));

				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public Book getBookById(Long id) {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")) {
			statement.setLong(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Book book = new Book();
					book.setId(resultSet.getLong("id"));
					book.setName(resultSet.getString("name"));
					book.setIsbn(resultSet.getString("isbn"));
					book.setAuthor(resultSet.getString("author"));
					return book;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Book> searchBooksByName(String name) {
		List<Book> books = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE name LIKE ?")) {
			statement.setString(1, "%" + name + "%");

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Book book = new Book();
					book.setId(resultSet.getLong("id"));
					book.setName(resultSet.getString("name"));
					book.setIsbn(resultSet.getString("isbn"));
					book.setAuthor(resultSet.getString("author"));

					books.add(book);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public List<Book> searchBooksByISBN(String isbn) {
		List<Book> books = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE isbn LIKE ?")) {
			statement.setString(1, "%" + isbn + "%");

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Book book = new Book();
					book.setId(resultSet.getLong("id"));
					book.setName(resultSet.getString("name"));
					book.setIsbn(resultSet.getString("isbn"));
					book.setAuthor(resultSet.getString("author"));

					books.add(book);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public List<Book> searchBooksByAuthor(String author) {
		List<Book> books = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE author LIKE ?")) {
			statement.setString(1, "%" + author + "%");

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Book book = new Book();
					book.setId(resultSet.getLong("id"));
					book.setName(resultSet.getString("name"));
					book.setIsbn(resultSet.getString("isbn"));
					book.setAuthor(resultSet.getString("author"));

					books.add(book);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public void deleteAllBooks() {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
			statement.executeUpdate("DELETE FROM books");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
