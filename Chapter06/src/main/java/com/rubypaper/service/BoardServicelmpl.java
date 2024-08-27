package com.rubypaper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@Service
public class BoardServicelmpl implements BoardService {

	@Autowired
	private BoardRepository boardRepo;
	
	public List<Board> getBoardList(Board board) {
		return (List<Board>) boardRepo.findAll();
	}
	
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}
	public Board getBoard(Board board) {
		Board temp = boardRepo.findById(board.getSeq()).get();
		temp.setCnt(temp.getCnt()+1);
		boardRepo.save(temp);
		return temp;
	}
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		boardRepo.save(findBoard);
	}
	public void deleteBoard(Board board) {
		boardRepo.deleteById(board.getSeq());
	}
	
}
