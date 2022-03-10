package naru.narucof.backend.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import naru.narucof.backend.dto.TodoDto;

@Repository
@Mapper
public interface  TodoMapper {
	 //todo select (이메일아이디로 테이블 조회)
	 List<TodoDto> selectTodoList(String userId);
	 
	 int insertTodo(TodoDto todoDto);

	 int updateTodo(TodoDto todoDto);
	 
	 int deleteTodo(TodoDto todoDto);
	 
	 
}
