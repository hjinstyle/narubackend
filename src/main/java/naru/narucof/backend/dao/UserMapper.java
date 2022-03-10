package naru.narucof.backend.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import naru.narucof.backend.dto.UserDto;

@Repository
@Mapper
public interface  UserMapper {
	 //이메일아이디로 테이블 조회.
	 List<UserDto> selectFindByEmail(UserDto userDto);
	 
	 //회원가입
	 int insertSignUp(UserDto userDto);

	 
	 
}
