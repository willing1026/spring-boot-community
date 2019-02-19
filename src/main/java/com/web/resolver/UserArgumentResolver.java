package com.web.resolver;

import static com.web.domain.enums.SocialType.FACEBOOK;
import static com.web.domain.enums.SocialType.GOOGLE;
import static com.web.domain.enums.SocialType.KAKAO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.web.annotation.SocialUser;
import com.web.domain.User;
import com.web.domain.UserRepository;
import com.web.domain.enums.SocialType;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private UserRepository userRepository;
	
	//bean객체 설정
	public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		return parameter.getParameterAnnotation(SocialUser.class) != null &&
				parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		User user = (User)session.getAttribute("user");
		
		return getUser(user, session);
	}
	
	private User getUser(User user, HttpSession session) {
		
		if(user==null) {
			try {
				OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				
				/** 
				 * 예제에는 HashMap<String, Object>로 Casting 하게 되어있으나
				 * attribute가 UnmodifiableMap으로 되어있어 변환없이 그대로 사용.
				 */
				Map<String, Object> map = authentication.getPrincipal().getAttributes();
				User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
				
				user = userRepository.findByEmail(convertUser.getEmail());
				if(user==null) {
					user = userRepository.save(convertUser);
				}
				
				setRoleIfNotSame(user, authentication, map);
				session.setAttribute("user", user);
			} catch(ClassCastException e) {
				e.printStackTrace();
				return user;
			}
		}
		
		return user;
	}
	
	private User convertUser(String authority, Map<String, Object> map) {
		if(FACEBOOK.getValue().equals(authority)) return getModernUser(FACEBOOK, map);
		else if(GOOGLE.getValue().equals(authority)) return getModernUser(GOOGLE, map);
		else if(KAKAO.getValue().equals(authority)) return getKakaoUser(map);
		return null;
	}
	
	private User getModernUser(SocialType socialType, Map<String, Object> map) {
		return User.builder()
				.name(String.valueOf(map.get("name")))
				.email(String.valueOf(map.get("email")))
				.principal(String.valueOf(map.get("sub")))
				.socialType(socialType)
				.createdDate(LocalDateTime.now())
				.build();
	}
	
	private User getKakaoUser(Map<String, Object> map) {
		HashMap<String, String> propertyMap = (HashMap<String, String>)(Object) map.get("properties");
		
		return User.builder()
				.name(propertyMap.get("nickname"))
				.email(String.valueOf(map.get("kaccount_email")))
				.principal(String.valueOf(map.get("id")))
				.socialType(KAKAO)
				.createdDate(LocalDateTime.now())
				.build();
	}
	
	private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
		if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(map, "N/A",
					AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
		}
	}
}
