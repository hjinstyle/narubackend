package naru.narucof.backend.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import naru.narucof.backend.dao.UserMapper;
import naru.narucof.backend.dto.UserDto;

@Slf4j
@Service
@RequiredArgsConstructor    //userMapper선언할때 생성자 구지 만들필요없어서 선언함.
public class UserService {

	private final UserMapper userMapper;

	//@Autowired
	private PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	
	/*
	 * 유저 회원가입
	 */
	public int signUp(final UserDto userDto) {
		
		//인풋값이 널이면 예외처리
		if(userDto == null || userDto.getEmail() == null || userDto.getEmail().equals("") || userDto.getPassword().equals("") || userDto.getUserName().equals("")) {
			throw new RuntimeException("입력값을 모두 넣어주세요.");
		}

		//이메일로 user테이블 조회하여, 이미 존재하는 사람인지 확인한다.
		List<UserDto> userList = userMapper.selectFindByEmail(userDto);
		
		//계정이 이미존재할경우 예외처리 리턴함.
		if(userList!=null && userList.size()>0) {
			log.warn("Email already exists {}", userList.get(0).getEmail());
			throw new RuntimeException("이미 존재하는 계정입니다.");
		}

		
		//패스워드 암호화
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
	
		//insert처리 (기존에 없는 회원이라면 회원가입 처리함) 
		return userMapper.insertSignUp(userDto);
		
		
		
	}	
	
	
	/*
	 * 유저 로그인
	 */
	public UserDto signIn(final UserDto userDto) {
		
		
		//이메일로 user테이블 조회하여, 이미 존재하는 사람인지 확인한다.
		List<UserDto> userList = userMapper.selectFindByEmail(userDto);
		
		// matches 메서드를 이용해 패스워드가 같은지 확인
		if(userList != null && userList.size()>0 && passwordEncoder.matches(userDto.getPassword(), userList.get(0).getPassword())) {
			//일치한다면, 유저정보 리턴해줌.
			return userList.get(0);
		}
		return null;
	}

	
}
