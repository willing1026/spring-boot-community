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
		 * findAll�� ȣ���� �� 
		 * Pageable �������̽� �Ǵ� Pageable �������̽� ����ü�� PageRequest�� 
		 * ���ڷ� �־� ȣ���ϴ� ���� ��ü�� ����ϴ� ����̴�.
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
