package edu.pnu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.pnu.domain.MemberDTO;
import edu.pnu.jdbc.DBManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDAO {

	private final DBManager dbm;

	private HashMap<String, List<MemberDTO>> map = new HashMap<>();

	@PostConstruct
	public void init() {
		System.out.println("MemberDAO 호출");
		dbm.getConnection();
	}

	// 모든 멤버를 출력하는 메서드
	public HashMap<String, List<MemberDTO>> getAllMember() {
		map.clear();
		String query = "select * from member order by id";
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			dbm.execQuery(query);
			while (dbm.getRs().next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(dbm.getRs().getInt("id"));
				dto.setName(dbm.getRs().getString("name"));
				dto.setPass(dbm.getRs().getString("pass"));
				dto.setRegidate(dbm.getRs().getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("getAllmember 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
		return map;
	}

	// 멤버 id를 통해 1명의 멤버를 출력하는 메서드
	public HashMap<String, List<MemberDTO>> getMemberById(int id) {
		map.clear();
		MemberDTO dto = new MemberDTO();
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			String query = "select * from member where id=" + id;
			dbm.execQuery(query);
			if (!dbm.getRs().next()) {
				map.put(query, null);
				return map; // 조회된 id가 없는 경우 null 리턴
			} else {
				dto.setId(dbm.getRs().getInt("id"));
				dto.setName(dbm.getRs().getString("name"));
				dto.setPass(dbm.getRs().getString("pass"));
				dto.setRegidate(dbm.getRs().getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("getMemberById 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
		return map;
	}

	// 멤버의 id로 조회 후 해당 멤버의 name과 pass를 갱신하는 메서드
	public HashMap<String, List<MemberDTO>> updateMemberById(int id, String name, String pass) {
		map.clear();
		String query = "update member set ";
		query += String.format("name='%s', pass='%s'", name, pass);
		query += " where id=" + id;
		if (getMemberById(id).values().contains(null)) {
			map.put(query, null);
			return map; // 선택한 id가 없는 경우 null 리턴
		} else {
			try {
				dbm.execQuery(query);
				map.put(query, getMemberById(id).values().iterator().next());
			} catch (SQLException e) {
				System.out.println("updateMemberById 예외발생 :: " + e.getMessage());
			} finally {
				dbm.closeDB();
			}
			return map;
		}
	}

	// name과 pass를 통해 새로운 멤버를 추가하는 메서드
	public HashMap<String, List<MemberDTO>> addMemberByPost(String name, String pass) {
		map.clear();
		MemberDTO dto = new MemberDTO();
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			String query = "insert into member (name, pass) values " + String.format("('%s', '%s')", name, pass);
			String query2 = "select * from member order by id desc limit 1";
			dbm.execUpdate(query); // 업데이트 쿼리문 실행
			dbm.execQuery(query2);
			while (dbm.getRs().next()) {
				dto.setId(dbm.getRs().getInt("id"));
				dto.setName(dbm.getRs().getString("name"));
				dto.setPass(dbm.getRs().getString("pass"));
				dto.setRegidate(dbm.getRs().getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("addMemberByPost 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
		return map;
	}

	// id를 통해 해당 멤버 1명을 삭제하는 메서드
	public HashMap<String, Integer> deleteMemberById(int id) {
		HashMap<String, Integer> delmap = new HashMap<>();
		int result = 0;
		String query = "delete from member where id=" + id;
		try {
			if (getMemberById(id).values().contains(null)) {
				delmap.clear();
				delmap.put(query, 0);
				return delmap;
			}
			result = dbm.execUpdate(query);
			delmap.put(query, result);
		} catch (SQLException e) {
			System.out.println("deleteMemberById 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
		return delmap;
	}

	// 배열 타입의 json으로 멤버를 추가하는 메서드
	public HashMap<String, List<MemberDTO>> addMemberJSON(List<MemberDTO> md) {
		try {
			List<MemberDTO> memberlist = new ArrayList<>();
			String totalQuery = "insert into member (id, name, pass) values "; // 로그 저장용 query
			int count = 0;
			for (MemberDTO m : md) {
				// json에 id값이 없는경우 마지막 id를 찾아 +1한 값을 id로 설정
				if (m.getId() == null) {
					dbm.execQuery("select * from member order by id desc limit 1");
					dbm.getRs().next();
					m.setId(dbm.getRs().getInt("id") + 1);
				}
				String query = "insert into member (id, name, pass) values "
						+ String.format("('%s', '%s', '%s')", m.getId(), m.getName(), m.getPass());
				// json의 id가 DB에 이미 등록된 경우 null 반환
				if (getMemberById(m.getId()).values().iterator().next() != null) {
					map.put(query, null);
					return map;
				}
				map.clear();
				dbm.execUpdate(query); // 업데이트 쿼리문 실행
				
				count++; // 로그 저장용 count
				totalQuery += String.format("('%s', '%s', '%s')", m.getId(), m.getName(), m.getPass());
				if (count < md.size()) totalQuery += ", ";	// 카운트 갯수만큼 쉼표로 구분
				
				dbm.execQuery("select * from member where id=" + m.getId());	// 결과 반환용 쿼리문
				while (dbm.getRs().next()) {
					MemberDTO dto = new MemberDTO();
					dto.setId(dbm.getRs().getInt("id"));
					dto.setName(dbm.getRs().getString("name"));
					dto.setPass(dbm.getRs().getString("pass"));
					dto.setRegidate(dbm.getRs().getDate("Regidate"));
					memberlist.add(dto);
				}
			}
			map.put(totalQuery, memberlist);
		} catch (SQLException e) {
			System.out.println("addMemberJSON List 예외발생 :: " + e.getMessage());
		} finally {
			dbm.closeDB();
		}
		return map;
	}
}
