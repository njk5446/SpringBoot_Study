package com.rubypaper.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.Board;
import com.rubypaper.service.TestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final TestService ts;
	
	@GetMapping("/board")
	public List<Board> getBoards() {
		return ts.getAll();
	}
	@GetMapping("/board/{seq}")
	public Board getBoard (@PathVariable long seq) {
		return ts.getById(seq);
	}
	@PostMapping("/board")
	public Board postBoard(Board board) {
		return ts.postBoard(board);
	}
	@PostMapping("/boardJSON")
	public Board postJSON(@RequestBody Board board) {
		return ts.postJSON(board);
	}
	@PutMapping("/board")
	public Board putBoard(Board board) {
		return ts.putBoard(board);
	}
	@DeleteMapping("/board/{seq}")
	public Board deleteBoard (@PathVariable long seq) {
		return ts.delBoard(seq);
	}
}

