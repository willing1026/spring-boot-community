package com.web.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.annotation.SocialUser;
import com.web.domain.User;
import com.web.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;

	//generator를 이용해서 의존성 주입 - setter, @Autowired, @AllArgsConstructor (lombok) 방법 존재
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping({"","/"})
	public String board(@RequestParam(value="idx", defaultValue="0") Long idx, Model model) {
		model.addAttribute("board", boardService.findBoardByIdx(idx));
		return "/board/form";
	}
	
	@GetMapping("/list")
	public String list(@PageableDefault Pageable pageable, @SocialUser User user,  Model model) {
		//HandlerMethodArgumentResolver 방식. 세션에 있는 User 정보를 가져와서 세팅
		//redirect, forward 시에 user정보를 포함해서 전달할 필요 없음
		
		//User기준으로 게시글 목록 불러옴
		model.addAttribute("boardList", boardService.findByUserBoardList(user, pageable));
		model.addAttribute("user", user);
		
		return "/board/list";
	}
	
}
