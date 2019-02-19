package com.web.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	//https://stackoverrun.com/ko/q/3433831
	Page<Board> findByUser(User user, Pageable pageable);
}
