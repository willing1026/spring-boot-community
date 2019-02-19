package com.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.domain.Board;
import com.web.domain.BoardRepository;
import com.web.domain.User;
import com.web.domain.UserRepository;
import com.web.domain.enums.BoardType;
import com.web.resolver.UserArgumentResolver;

@SpringBootApplication
public class SpringBootCommunityApplication implements WebMvcConfigurer {

	public static final String APPLICATION_PROPERTIES = "spring.config.location="
			+ "classpath:application.properties,"
			+ "classpath:oauth2.yml";
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringBootCommunityApplication.class)
		.properties(APPLICATION_PROPERTIES)
		.run(args);
	}
	
	@Autowired
	private UserArgumentResolver userArgumentResolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}

	
	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception {
		return (args) -> {
			User user = userRepository.save(User.builder()
						.name("havi")
						.password("test")
						.email("havi@gmail.com")
						.createdDate(LocalDateTime.now())
						.build());
			
			IntStream.rangeClosed(1, 200).forEach(index->
				boardRepository.save(Board.builder()
							.title("°Ô½Ã±Û"+index)
							.subTitle("¼ø¼­"+index)
							.content("ÄÜÅÙÃ÷")
							.boardType(BoardType.free)
							.createdDate(LocalDateTime.now())
							.updatedDate(LocalDateTime.now())
							.user(user)
							.build())
					);
		};
	}

}

