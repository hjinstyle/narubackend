package naru.narucof.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import naru.narucof.backend.dto.ResponseDTO;
import naru.narucof.backend.dto.UserDto;
import naru.narucof.backend.security.TokenProvider;
import naru.narucof.backend.service.UserService;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	/*
	 * 회원 가입
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDTO) {
		try {

			//회원가입 로직 호출
			int registeredUser = userService.signUp(userDTO);			

			// 유저 정보는 항상 하나이므로 그냥 리스트로 만들어야하는 ResponseDTO를 사용하지 않고 그냥 UserDTO 리턴.
			return ResponseEntity.ok(registeredUser);

		} catch (Exception e) {
			// 예외가 나는 경우 bad 리스폰스 리턴.
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	
	/*
	 * 로그인
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDto userDTO) {
		
		//화면에서 넘어온 userDto를 service에 전달
		UserDto result = userService.signIn(userDTO);

		
		//DB에 유저정보가 존재한다면
		if(result != null) {

			//토큰 생성후 setToken으로 유저dto에 담아줌
			result.setToken(tokenProvider.create(result));
				
			//화면에 유저정보 리턴
			return ResponseEntity.ok().body(result);
	
		//DB에 유저정보가 없다면
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
							.error("계정이 없거나 패스워드가 틀렸습니다.")
							.build();
			return ResponseEntity
							.badRequest()
							.body(responseDTO);
		}
		
		
	}
}
