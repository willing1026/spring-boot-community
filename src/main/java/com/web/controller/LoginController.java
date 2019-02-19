package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.annotation.SocialUser;
import com.web.domain.User;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value= "/loginSuccess")
	public String loginComplete(@SocialUser User user, RedirectAttributes redirectAttributes) {
		
		//RedirectAttributes redirectAttributes 이용해서 redirect도 넘길수 있음
		//redirectAttributes.addAttribute("user", user);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
}
