package com.rubypaper.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import com.rubypaper.exception.BoardNotFoundException;

@Controller
public class ExceptionController {
	
	@GetMapping("/boardError")
	public String boardError(){
		throw new BoardNotFoundException("검색된 게시글이 없습니다.");
	}
	@GetMapping("/illegalArgumentError")
	public String illegalArgumentError(){
		throw new IllegalArgumentException("부적절한 인자가 전달되었습니다.");
	}
	@GetMapping("/sqlError")
	public String sqlError() throws SQLException {
		throw new SQLException("SQL 구문에 오류가 있습니다.");
	}
	
	@ExceptionHandler (SQLException.class)
	public String numberFormatError(SQLException e, Model model) {
		model.addAttribute("exception", e);
		return "/errors/sqlError";
	}
	

}
