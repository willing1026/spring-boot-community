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

	private final String boardTestType = "테스트";
	private final String email = "test@gmail.com";
	
	//board repository
	@Autowired
	private BoardRepository boardRepository;
	//user repository
	@Autowired
	private UserRepository userRepository;
	
	//test전 마다 data setting
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
						.subTitle("서브타이틀")
						.content("콘텐츠")
						.boardType(BoardType.free)
						.createdDate(LocalDateTime.now())
						.updatedDate(LocalDateTime.now())
						.user(user)
						.build()
				);
	}
	
	@Test
	public void 테스트() {
		User user = userRepository.findByEmail(email);
		
	}
}
