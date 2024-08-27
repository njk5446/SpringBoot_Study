package edu.pnu.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberDAO {

	private Connection con;

	private String url = "jdbc:mysql://localhost:3306/musthave";;
	private String user = "scott";
	private String pass = "tiger";
	Statement stmt;
	ResultSet rs;

	public MemberDAO() {
		System.out.println("MemberDAO 호출");
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

	public List<MemberDTO> getAllMember() {
		List<MemberDTO> memberlist = new ArrayList<>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from member");
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("Regidate"));
				memberlist.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("getAllmember 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return memberlist;
	}

	public MemberDTO getMemberById(int id) {
		MemberDTO dto = new MemberDTO();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from member where id=" + id);
			if (!rs.next())
				return null; // 조회된 id가 없는 경우 null 리턴
			else {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("Regidate"));
			}
		} catch (SQLException e) {
			System.out.println("getMemberById 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return dto;
	}

	public MemberDTO updateMemberById(int id, String name, String pass) {
		MemberDTO dto = getMemberById(id);
		if (dto == null)
			return null; // 선택한 id가 없는 경우 null 리턴
		else {
			try {
				String sql = "update member set ";
				stmt = con.createStatement();
				stmt.executeUpdate(sql + String.format("name='%s', pass='%s' where id=%d", name, pass, id));
			} catch (SQLException e) {
				System.out.println("updateMemberById 예외발생 :: " + e.getMessage());
			} finally {
				closeDB();
			}
			return getMemberById(id);
		}
	}

	public MemberDTO addMemberByPost(String name, String pass) {
		int lastid = 0;
		try {
			String sql = "insert into member (name, pass) values ";
			stmt = con.createStatement();
			stmt.executeUpdate(sql + String.format("('%s', '%s')", name, pass));

			ResultSet rs = stmt.executeQuery("select last_insert_id()");
			rs.next();
			lastid = rs.getInt("last_insert_id()");
		} catch (SQLException e) {
			System.out.println("addMemberByPost 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return getMemberById(lastid);
	}

	public int deleteMemberById(int id) {
		int result = 0;
		try {
			if (getMemberById(id) == null)
				return 0;
			String sql = "delete from member where id=";
			stmt = con.createStatement();
			result = stmt.executeUpdate(sql + id);
		} catch (SQLException e) {
			System.out.println("deleteMemberById 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return result;
	}

	public List<MemberDTO> addMemberJSON(MemberDTO md) {
		try {
			if (getMemberById(md.getId()) != null)
				return null;
			String sql = "insert into member (id, name, pass) values ";
			stmt = con.createStatement();
			stmt.executeUpdate(sql + String.format("('%d', '%s', '%s')", md.getId(), md.getName(), md.getPass()));

		} catch (SQLException e) {
			System.out.println("addMemberJSON 예외발생 :: " + e.getMessage());
		} finally {
			closeDB();
		}
		return getAllMember();
	}
}
