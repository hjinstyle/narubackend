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
	 * TODO����Ʈ ��ȸ
	 */
	public List<TodoDto> retrieve(final String userId) {
		
		List<TodoDto> todoList = todoMapper.selectTodoList(userId);

		return todoList;
		
	}	
	
	/*
	 * TODO �Խñ� ����
	 */
	
	public List<TodoDto> insertTodo(final TodoDto todoDto) {
		

		validate(todoDto); //����

		todoMapper.insertTodo(todoDto);
		
		return todoMapper.selectTodoList(todoDto.getUserId());
	}
	
	
	/*
	 * TODO �Խñ� ����
	 */	
	public List<TodoDto> updateTodo(final TodoDto todoDto) {
		

		validate(todoDto); //����

		todoMapper.updateTodo(todoDto);
		
		return todoMapper.selectTodoList(todoDto.getUserId());

	}

	/*
	 * TODO �Խñ� ����
	 */
	public List<TodoDto> deleteTodo(final TodoDto todoDto) {
		
		validate(todoDto); //����
		
		try {
			todoMapper.deleteTodo(todoDto);
		} catch(Exception e) {

			log.error("error deleting entity ", todoDto.getId(), e);
			// (4) ��Ʈ�ѷ��� exception�� ������. �����ͺ��̽� ���� ������ ĸ��ȭ �ϱ� ���� e�� �������� �ʰ� �� exception ������Ʈ�� �����Ѵ�.
			throw new RuntimeException("error deleting entity " + todoDto.getId());
		}
		
		return todoMapper.selectTodoList(todoDto.getUserId());
	}

	
	
	//����
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
