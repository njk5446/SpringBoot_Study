package edu.pnu.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.pnu.jdbc.DBManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LogDAO {

	private final DBManager dbm;

	@PostConstruct
	public void init() {
		System.out.println("LogDAO 호출");
		dbm.getConnection();
	}

	// dblog 테이블에 메소드, 쿼리문, 성공여부 (1/0) 저장
	public void dbLog(String method, String query, int success) {
		try {
			if (query.length() > 200) { // 쿼리문이 너무 긴 경우 substring
				String s = query.substring(0, 150) + " ... " + query.substring(query.length() - 50, query.length());
				query = s;
			}
			System.out.println(query);
			String sql = "insert into dblog (method, sqlstring, success) values ";
			dbm.execUpdate(sql + String.format("(\"%s\", \"%s\", \"%d\")", method, query, success));
		} catch (SQLException e) {
			System.out.println("Log 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
	}
}