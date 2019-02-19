package com.web.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.domain.Board;
import com.web.domain.BoardRepository;
import com.web.domain.User;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@Transactional(readOnly=true)
	public Page<Board> findBoardList(Pageable pageable) {
		
		/*
		 * findAll을 호출할 때 
		 * Pageable 인퍼테이스 또는 Pageable 인터페이스 구현체인 PageRequest를 
		 * 인자로 넣어 호출하는 것이 대체로 사용하는 방법이다.
		 */
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public Board findBoardByIdx(Long idx) {
		return boardRepository.findById(idx).orElse(new Board());
	}
	
	public Page<Board> findByUserBoardList(User user, Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
		return boardRepository.findByUser(user, pageable);
	}
	
	
}
