package naru.narucof.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import naru.narucof.backend.dto.ResponseDTO;
import naru.narucof.backend.dto.TodoDto;
import naru.narucof.backend.service.TodoService;


@RestController
@RequestMapping("todo") //���ҽ�
public class TodoController {
	@Autowired
	private TodoService todoService;
	

	/*
	 * �Խñ� ��� ��ȸ (���� localhost:3030/ �϶��� ����Ѵ�.
	 */
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
		System.out.println("UserID : " + userId);
		
		
		List<TodoDto> result = todoService.retrieve(userId);
		
		ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(result).build();

		// (7) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}	
	
	/*
	 * �Խñ� �߰���ư Ŭ��
	 */
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto todoDto) {
		try {
			
			
			todoDto.setUserId(userId);
			
			List<TodoDto> result = todoService.insertTodo(todoDto);
			
			ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(result).build();
						
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			String error=e.getMessage();
			ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	


	/*
	 * �Խñ� ���� ��ư Ŭ��
	 */
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto todoDto) {
		
		todoDto.setUserId(userId);
		
		List<TodoDto> result = todoService.updateTodo(todoDto);
		
		ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(result).build();
					
		return ResponseEntity.ok().body(response);
	}

	/*
	 * �Խñ� �� ������ ������ Ŭ�� �� ����
	 */
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto todoDto) {
		try {
			todoDto.setUserId(userId);
			
			List<TodoDto> result = todoService.deleteTodo(todoDto);
			
			ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(result).build();
						
			return ResponseEntity.ok().body(response);		
			
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}


	  
}
