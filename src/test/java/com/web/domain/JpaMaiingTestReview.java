package com.web.domain;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.domain.enums.BoardType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMaiingTestReview {

	private final String boardTestType = "�׽�Ʈ";
	private final String email = "test@gmail.com";
	
	//board repository
	@Autowired
	private BoardRepository boardRepository;
	//user repository
	@Autowired
	private UserRepository userRepository;
	
	//test�� ���� data setting
	@Before
	public void init() {
		User user = User.builder()
					.name("havi")
					.password("test")
					.email(email)
					.createdDate(LocalDateTime.now())
					.build();
		
		boardRepository.save(Board.builder()
						.title(boardTestType)
						.subTitle("����Ÿ��Ʋ")
						.content("������")
						.boardType(BoardType.free)
						.createdDate(LocalDateTime.now())
						.updatedDate(LocalDateTime.now())
						.user(user)
						.build()
				);
	}
	
	@Test
	public void �׽�Ʈ() {
		User user = userRepository.findByEmail(email);
		
	}
}
