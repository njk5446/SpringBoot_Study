package edu.pnu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
	@GetMapping({"/","index"})
	public String index() { System.out.println("인덱스"); return "index";}
	
	@GetMapping("/member")
	public String member() { System.out.println("멤버"); return "member";}
	
	@GetMapping("/manager")
	public String manager() { System.out.println("매니저"); return "manager";}
	
	@GetMapping("/admin")
	public String admin() { System.out.println("어드민"); return "admin";}
	

}
