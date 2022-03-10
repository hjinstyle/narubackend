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
	 * ȸ�� ����
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDTO) {
		try {

			//ȸ������ ���� ȣ��
			int registeredUser = userService.signUp(userDTO);			

			// ���� ������ �׻� �ϳ��̹Ƿ� �׳� ����Ʈ�� �������ϴ� ResponseDTO�� ������� �ʰ� �׳� UserDTO ����.
			return ResponseEntity.ok(registeredUser);

		} catch (Exception e) {
			// ���ܰ� ���� ��� bad �������� ����.
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	
	/*
	 * �α���
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDto userDTO) {
		
		//ȭ�鿡�� �Ѿ�� userDto�� service�� ����
		UserDto result = userService.signIn(userDTO);

		
		//DB�� ���������� �����Ѵٸ�
		if(result != null) {

			//��ū ������ setToken���� ����dto�� �����
			result.setToken(tokenProvider.create(result));
				
			//ȭ�鿡 �������� ����
			return ResponseEntity.ok().body(result);
	
		//DB�� ���������� ���ٸ�
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
							.error("������ ���ų� �н����尡 Ʋ�Ƚ��ϴ�.")
							.build();
			return ResponseEntity
							.badRequest()
							.body(responseDTO);
		}
		
		
	}
}
