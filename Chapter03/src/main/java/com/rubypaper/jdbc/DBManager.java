package com.rubypaper.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	private String url;
	private String user;
	private String pass;

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Connection getConnection() {
		try {
			setCon(DriverManager.getConnection(url, user, pass));
			return getCon();
		} catch (Exception e) {
			System.out.println("Connection 예외 발생 :: ");
			e.printStackTrace();
		}
		return null;
	}

	public void closeDB() {
		try {
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			System.out.println("closeDB 예외 발생 :: ");
			e.printStackTrace();
		}
	}

}