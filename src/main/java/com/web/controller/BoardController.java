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

	//generator�� �̿��ؼ� ������ ���� - setter, @Autowired, @AllArgsConstructor (lombok) ��� ����
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
		//HandlerMethodArgumentResolver ���. ���ǿ� �ִ� User ������ �����ͼ� ����
		//redirect, forward �ÿ� user������ �����ؼ� ������ �ʿ� ����
		
		//User�������� �Խñ� ��� �ҷ���
		model.addAttribute("boardList", boardService.findByUserBoardList(user, pageable));
		model.addAttribute("user", user);
		
		return "/board/list";
	}
	
}
