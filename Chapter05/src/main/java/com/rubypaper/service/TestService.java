package com.rubypaper.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestService {

	private final BoardRepository repo;

	public List<Board> getAll() {
		return repo.findAll();
	}

	public Board getById(long seq) {
		return repo.findById(seq).orElseThrow();
	}

	@Builder
	public Board postBoard(Board bd) {
		return repo.save(Board.builder()
				.title(bd.getTitle())
//				.writer(bd.getWriter())
				.content(bd.getContent())
				.createDate(new Date())
				.cnt(0L)
				.build());
	}
	
	public Board postJSON(Board bd) {
		return repo.save(bd);
	}

	public Board putBoard(Board bd) {
		Board board = repo.findById(bd.getSeq()).orElseThrow();
		if (bd.getTitle() != null) board.setTitle(bd.getTitle());
		if (bd.getContent() != null) board.setContent(bd.getContent());
		return repo.save(board);
	}

	public Board delBoard(long seq) {
		Board board = repo.findById(seq).orElseThrow();
		repo.deleteById(seq);
		return board;
	}
}
