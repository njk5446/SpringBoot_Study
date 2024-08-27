package edu.pnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	@GetMapping({"/","/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/member")
	public void member() {
		
	}
	@GetMapping("/manager")
	public void manager() {
		
	}
	@GetMapping("/admin")
	public void admin() {
		
	}
	
	
}
