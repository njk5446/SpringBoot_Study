package com.rubypaper.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.BoardVO;

@RestController
public class BoardController {

	public BoardController() {
		System.out.println("===> BoardController 생성");
	}

	@GetMapping("/hello") // localhost:8080/hello
	public String hello(String name) { // 파라미터로 전달받기, ?name=
		return "Hello : " + name;
	}

	@PostMapping("/hello2") // localhost:8080/hello2
	public String hello2(String name) { 
		return "Hello : " + name;
	}

	@GetMapping("/getBoard") // localhost:8080/getBoard
	public BoardVO getBoard() {
		BoardVO board = new BoardVO();
		board.setSeq(1);
		board.setTitle("테스트 제목...");
		board.setWriter("테스터");
		board.setContent("테스트 내용입니다...................");
		board.setCreateDate(new Date());
		board.setCnt(0);
		return board;
	}

	@GetMapping("/getBoardList") // localhost:8080/getBoardList
	public List<BoardVO> getBoardList() {
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		for (int i = 0; i < 10; i++) {
			BoardVO board = new BoardVO();
			board.setSeq(i);
			board.setTitle("제목..."+i);
			board.setWriter("테스터"+i);
			board.setContent(i+"번 내용입니다...................");
			board.setCreateDate(new Date());
			board.setCnt(0);
			boardlist.add(board);
		}
		return boardlist;
	}
}