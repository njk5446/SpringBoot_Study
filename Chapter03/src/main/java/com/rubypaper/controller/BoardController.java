package com.rubypaper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardController {
	
	public BoardController() {
		log.error("error");
		log.warn("warn");
		log.info("BoardControoler 생성");
		log.debug("debug");
		log.trace("trace");
	}

	@GetMapping("/hello")
	public String hello(String name) {
		return "Hello : " + name;
	}
	
	@GetMapping("/getBoard")
	public BoardVO getBoard() {
		BoardVO bd = new BoardVO();
		bd.setWriter("테스터");
		return bd; 
	}
	
	
}
