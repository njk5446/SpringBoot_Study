package com.rubypaper.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rubypaper.domain.MemberDTO;
import com.rubypaper.jdbc.DBManager;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDAO {

	private final DBManager dbm;

	@PostConstruct
	public void init() {
		System.out.println("MemberDAO 호출");
		dbm.getConnection();
	}

	// 모든 멤버를 출력하는 메서드
	public List<MemberDTO> getAllMember() {
		String query = "select * from member";
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			dbm.setRs(dbm.getCon().createStatement().executeQuery(query));
			while (dbm.getRs().next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(dbm.getRs().getInt("id"));
				dto.setName(dbm.getRs().getString("name"));
				dto.setPass(dbm.getRs().getString("pass"));
				dto.setRegidate(dbm.getRs().getDate("Regidate"));
				memberlist.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("getAllmember 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
		return memberlist;
	}
}
