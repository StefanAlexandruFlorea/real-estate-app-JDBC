package com.stefan.realestateapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection con = null;

	static {
		String url = "jdbc:mysql://127.0.0.1:3306/real_estate_database";
		String user = "root";
		String password = "Password123";

		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}
}
