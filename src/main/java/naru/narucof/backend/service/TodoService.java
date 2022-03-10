package naru.narucof.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import naru.narucof.backend.dao.TodoMapper;
import naru.narucof.backend.dto.TodoDto;
@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
	
	private final TodoMapper todoMapper;
	
	/*
	 * TODO리스트 조회
	 */
	public List<TodoDto> retrieve(final String userId) {
		
		List<TodoDto> todoList = todoMapper.selectTodoList(userId);

		return todoList;
		
	}	
	
	/*
	 * TODO 게시글 생성
	 */
	
	public List<TodoDto> insertTodo(final TodoDto todoDto) {
		

		validate(todoDto); //검증

		todoMapper.insertTodo(todoDto);
		
		return todoMapper.selectTodoList(todoDto.getUserId());
	}
	
	
	/*
	 * TODO 게시글 수정
	 */	
	public List<TodoDto> updateTodo(final TodoDto todoDto) {
		

		validate(todoDto); //검증

		todoMapper.updateTodo(todoDto);
		
		return todoMapper.selectTodoList(todoDto.getUserId());

	}

	/*
	 * TODO 게시글 삭제
	 */
	public List<TodoDto> deleteTodo(final TodoDto todoDto) {
		
		validate(todoDto); //검증
		
		try {
			todoMapper.deleteTodo(todoDto);
		} catch(Exception e) {

			log.error("error deleting entity ", todoDto.getId(), e);
			// (4) 컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
			throw new RuntimeException("error deleting entity " + todoDto.getId());
		}
		
		return todoMapper.selectTodoList(todoDto.getUserId());
	}

	
	
	//검증
	private void validate(final TodoDto todoDto) {
		//validations
		if(todoDto ==null) {
			log.warn("entity cannot be null.");
			throw new RuntimeException("entity cannot be null");
		}
		if(todoDto.getUserId() ==null) {
			log.warn("Unknown user.");
			throw new RuntimeException("unknown user.");
		}
	}
	

    
}
