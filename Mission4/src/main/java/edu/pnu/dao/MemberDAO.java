package edu.pnu.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberDAO {

	private Connection con;

	private String url = "jdbc:mysql://localhost:3306/musthave";
	private String user = "scott";
	private String pass = "tiger";
	private Statement stmt;
	private ResultSet rs;
	private HashMap<String, List<MemberDTO>> map = new HashMap<>();

	public MemberDAO() {
		System.out.println("MemberDAO 호출");
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("connection 예외발생 :: " + e.getMessage());
		}
	}

	// DB 자원해제용 메서드
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

	public HashMap<String, List<MemberDTO>> getAllMember() {
		map.clear();
		String query = "select * from member";
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("getAllmember 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return map;
	}

	public HashMap<String, List<MemberDTO>> getMemberById(int id) {
		map.clear();
		MemberDTO dto = new MemberDTO();
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			stmt = con.createStatement();
			String query = "select * from member where id=" + id;
			rs = stmt.executeQuery(query);
			if (!rs.next()) {
				map.put(query, null);
				return map; // 조회된 id가 없는 경우 null 리턴
			} else {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("getMemberById 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return map;
	}

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
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				map.put(query, getMemberById(id).values().iterator().next());
			} catch (SQLException e) {
				System.out.println("updateMemberById 예외발생 :: " + e.getMessage());
			} finally {
				closeDB();
			}
			return map;
		}
	}

	public HashMap<String, List<MemberDTO>> addMemberByPost(String name, String pass) {
		map.clear();
		MemberDTO dto = new MemberDTO();
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			String query = "insert into member (name, pass) values " + String.format("('%s', '%s')", name, pass);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			ResultSet rs = stmt.executeQuery("select * from member order by id desc limit 1");
			while (rs.next()) {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("addMemberByPost 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return map;
//		return getMemberById(lastid);
	}

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
			stmt = con.createStatement();
			result = stmt.executeUpdate(query);
			delmap.put(query, result);
		} catch (SQLException e) {
			System.out.println("deleteMemberById 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return delmap;
	}

	public HashMap<String, List<MemberDTO>> addMemberJSON(MemberDTO md) {
		String query = "insert into member (id, name, pass) values " +
				String.format("('%s', '%s', '%s')", md.getId(), md.getName(), md.getPass());
		List<MemberDTO> memberlist = new ArrayList<>();
		if (getMemberById(md.getId()).values().iterator().next() != null) { // 중복된 ID인 경우 null 반환
			map.put(query, null);
			return map;
		}
		try {
			map.clear();
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			ResultSet rs = stmt.executeQuery("select * from member where id=" + md.getId());
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("Regidate"));
				memberlist.add(dto);
			}
			map.put(query, memberlist);
		} catch (SQLException e) {
			System.out.println("addMemberJSON 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return map;
	}
}
