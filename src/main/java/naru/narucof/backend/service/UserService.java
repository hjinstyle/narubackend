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
@RequiredArgsConstructor    //userMapper�����Ҷ� ������ ���� �����ʿ��� ������.
public class UserService {

	private final UserMapper userMapper;

	//@Autowired
	private PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	
	/*
	 * ���� ȸ������
	 */
	public int signUp(final UserDto userDto) {
		
		//��ǲ���� ���̸� ����ó��
		if(userDto == null || userDto.getEmail() == null || userDto.getEmail().equals("") || userDto.getPassword().equals("") || userDto.getUserName().equals("")) {
			throw new RuntimeException("�Է°��� ��� �־��ּ���.");
		}

		//�̸��Ϸ� user���̺� ��ȸ�Ͽ�, �̹� �����ϴ� ������� Ȯ���Ѵ�.
		List<UserDto> userList = userMapper.selectFindByEmail(userDto);
		
		//������ �̹������Ұ�� ����ó�� ������.
		if(userList!=null && userList.size()>0) {
			log.warn("Email already exists {}", userList.get(0).getEmail());
			throw new RuntimeException("�̹� �����ϴ� �����Դϴ�.");
		}

		
		//�н����� ��ȣȭ
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
	
		//insertó�� (������ ���� ȸ���̶�� ȸ������ ó����) 
		return userMapper.insertSignUp(userDto);
		
		
		
	}	
	
	
	/*
	 * ���� �α���
	 */
	public UserDto signIn(final UserDto userDto) {
		
		
		//�̸��Ϸ� user���̺� ��ȸ�Ͽ�, �̹� �����ϴ� ������� Ȯ���Ѵ�.
		List<UserDto> userList = userMapper.selectFindByEmail(userDto);
		
		// matches �޼��带 �̿��� �н����尡 ������ Ȯ��
		if(userList != null && userList.size()>0 && passwordEncoder.matches(userDto.getPassword(), userList.get(0).getPassword())) {
			//��ġ�Ѵٸ�, �������� ��������.
			return userList.get(0);
		}
		return null;
	}

	
}
