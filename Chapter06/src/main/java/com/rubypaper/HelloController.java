package com.rubypaper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

//	@GetMapping("/hello")
//	// /WEB-INF/board/hello.jsp
//	public void hello(String name, Model model) {
//		model.addAttribute("name", name);
//	}
	@GetMapping("/hello")
	// /src/main/resources/templates/hello.html
	public void hello(Model model) {
		model.addAttribute("name", "Hello 타임리프");
	}
	
}
