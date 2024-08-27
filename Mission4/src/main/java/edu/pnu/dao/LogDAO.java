package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogDAO {

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private String url = "jdbc:mysql://localhost:3306/musthave";
	private String user = "scott";
	private String pass = "tiger";

	public LogDAO() {
		System.out.println("LogDAO 호출");
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("connection 예외발생 :: " + e.getMessage());
		}
	}

	private void closeDB() {
		try {
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// dblog 테이블에 메소드, 쿼리문, 성공여부 (1/0) 저장
	public void dbLog(String method, String query, int success) {
		try {
			String sql = "insert into dblog (method, sqlstring, success) values ";
			stmt = con.createStatement();
			stmt.executeUpdate(sql + String.format("(\"%s\", \"%s\", \"%d\")", method, query, success));
		} catch (SQLException e) {
			System.out.println("Log 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
	}
}