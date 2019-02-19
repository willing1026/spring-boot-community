package com.web.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
public class JpaMappingTest {
	private final String boardTestTitle="테스트";
	private final String email="test@gmail.com";
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Before
	public void init() {
		User user = userRepository.save(User.builder()
				.name("havi")
				.password("test")
				.email(email)
				.createdDate(LocalDateTime.now())
				.build());
		
		boardRepository.save(Board.builder()
				.title(boardTestTitle)
				.subTitle("서브타이틀")
				.content("콘텐츠")
				.boardType(BoardType.free)
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.user(user)
				.build());
	}
	
	@Test
	public void 테스트() {
		User user = userRepository.findByEmail(email);
		assertThat(user.getName(), is("havi"));
		
//		Board board = boardRepository.findByUser(user);
//		assertThat(board.getTitle(), is(boardTestTitle));
//		assertThat(board.getBoardType(), is(BoardType.free));
	}
}
