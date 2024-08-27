package edu.pnu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.pnu.dao.LogDAO;
import edu.pnu.dao.MemberDAO;
import edu.pnu.domain.MemberDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	// Lombok을 이용한 의존성 주입 (private final LogDAO / MemberDAO) 
	private final LogDAO lg;
	private final MemberDAO dao;
	// DB Log용 객체
	// map -> MemberDAO로부터 쿼리문 (키) : 쿼리 실행결과 리스트 (밸류)를 저장
	private HashMap<String, List<MemberDTO>> map = new HashMap<>();
	// list -> 쿼리 실행결과를 저장하기 위한 리스트
	private List<MemberDTO> list = new ArrayList<>();
	// method -> get/post/put 등의 메소드를 저장하기 위한 string 객체
	private String method;
	// query -> map의 키(쿼리문)를 저장하기 위한 set
	private Set<String> query;
	// it -> Set의 데이터에 접근하기 위한 iterator
	private Iterator<String> it;

	@PostConstruct
	private void init() {
		System.out.println("memberService 호출");
	}

	// dbLog의 method (controller에서 set)
	public void setMethod(String s) {
		this.method = s;
	}

	public List<MemberDTO> getMember() {
		map.clear(); list.clear();	//map, list 초기화
		map = dao.getAllMember();	//getAllMember 메서드 호출하여 결과값 map에 저장 
		query = map.keySet();		//map의 키값을 query에 저장
		it = query.iterator();		//키 값에 대한 iterator 지정
		for (String a : map.keySet()) {	//키 값에 대한 모든 값을 list에 저장
			list.addAll(map.get(a));
		}
		lg.dbLog(method, it.next(), 1);	//dbLog에 결과 전달
		return list;					//list를 controller에 반환
	}

	public List<MemberDTO> getMemberById(int id) {
		map.clear(); list.clear();	//map, list 초기화
		map = dao.getMemberById(id); //getMemberById 메서드 호출하여 결과값 map에 저장 
		query = map.keySet();		//map의 키값을 query에 저장
		it = query.iterator();		//키 값에 대한 iterator 지정
		String s = it.next();		//iterator로 set에 저장된 쿼리문을 s에 저장
		if (map.get(s) != null) {	//키에 대응하는 밸류가 있는 경우 리스트에 DTO 객체 추가
			for (MemberDTO a : map.get(s)) {
				list.add(a);
			}
			lg.dbLog(method, s, 1);	//dbLog에 메서드, 쿼리문, 성공 저장
			return list;
		} else {					//키에 대응하는 밸류가 없는 경우 null 리턴
			lg.dbLog(method, s, 0); //dbLog에 메서드, 쿼리문, 실패 저장
			return null;
		}
	}

	public List<MemberDTO> addMemberByPost(String name, String pass) {
		map.clear(); list.clear();
		map = dao.addMemberByPost(name, pass);
		query = map.keySet();
		it = query.iterator();
		String s = it.next();
		if (map.get(s) != null) {
			for (MemberDTO a : map.get(s)) {
				list.add(a);
			}
			lg.dbLog(method, s, 1);
			return list;
		} else {
			lg.dbLog(method, s, 0);
			return null;
		}
	}

	public List<MemberDTO> updateMemberById(int id, String name, String pass) {
		map.clear(); list.clear();
		map = dao.updateMemberById(id, name, pass);
		query = map.keySet();
		it = query.iterator();
		String s = null;
		while (it.hasNext()) {	// 키값이 2개*이므로 update가 포함된 키를 찾아서 s에 저장
			s = it.next();		// * update 후 해당 id로 select 하여 결과를 출력하기 때문
			if (s.contains("update"))
				break;
		}
		if (map.get(s) != null) {
			for (MemberDTO a : map.get(s)) {
				list.add(a);
			}
			lg.dbLog(method, s, 1);
			return list;
		} else {
			lg.dbLog(method, s, 0);
			return null;
		}
	}

	public int deleteMemberById(int id) {
		HashMap<String, Integer> delmap = new HashMap<>();
		delmap = dao.deleteMemberById(id);
		query = delmap.keySet();
		it = query.iterator();
		String s = it.next();
		if (delmap.get(s) != 0) {
			lg.dbLog(method, s, 1);
			return 1;
		} else {
			lg.dbLog(method, s, 0);
			return 0;
		}
	}

	public List<MemberDTO> addMemberJSON(List<MemberDTO> memberDTO) {
		map.clear(); list.clear();
		map = dao.addMemberJSON(memberDTO);
		query = map.keySet();
		it = query.iterator();
		String s = null;
		while (it.hasNext()) {
			s = it.next();
			if (s.contains("insert"))
				break;
		}
		if (map.get(s) != null) {
			for (MemberDTO a : map.get(s)) {
				list.add(a);
			}
			lg.dbLog(method, s, 1);
			return list;
		} else {
			lg.dbLog(method, s, 0);
			return null;
		}
	}
}
