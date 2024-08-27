package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.service.BoardService;

@SessionAttributes("member")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService bs;

	@ModelAttribute("member")	// Model 객체에 "member"라는 이름으로 member 객체를 저장
	public Member setMember() {	// 최종적으로 @SessionAttributes에 의해 session 영역에 member를 저장
		return new Member();	
	}
	
	@RequestMapping(value="/getBoardList", method = {RequestMethod.GET, RequestMethod.POST})
	public String getBoardList(@ModelAttribute("member") Member member, Model model, Board board) {
		// Model에 "member" 로 저장된 객체를 매개변수로 사용
		if(member.getId() == null) return "redirect:login";
		
		List<Board> boardList = bs.getBoardList(board);
		model.addAttribute("boardList", boardList);
		return "getBoardList";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoardView() {
		return "insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null) return "redirect:login";
		
		bs.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		if(member.getId() == null) return "redirect:login";
		
		model.addAttribute("board",bs.getBoard(board));
		return "getBoard";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null) return "redirect:login";
		
		bs.updateBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null) return "redirect:login";
		
		bs.deleteBoard(board);
		return "forward:getBoardList";
	}
}
