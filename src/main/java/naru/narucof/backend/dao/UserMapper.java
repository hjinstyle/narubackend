package naru.narucof.backend.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import naru.narucof.backend.dto.UserDto;

@Repository
@Mapper
public interface  UserMapper {
	 //�̸��Ͼ��̵�� ���̺� ��ȸ.
	 List<UserDto> selectFindByEmail(UserDto userDto);
	 
	 //ȸ������
	 int insertSignUp(UserDto userDto);

	 
	 
}
