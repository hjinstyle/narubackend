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
@RequestMapping("todo") //리소스
public class TodoController {
	@Autowired
	private TodoService todoService;
	

	/*
	 * 게시글 목록 조회 (최초 localhost:3030/ 일때만 사용한다.
	 */
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
		System.out.println("UserID : " + userId);
		
		
		List<TodoDto> result = todoService.retrieve(userId);
		
		ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(result).build();

		// (7) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}	
	
	/*
	 * 게시글 추가버튼 클릭
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
	 * 게시글 수정 버튼 클릭
	 */
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto todoDto) {
		
		todoDto.setUserId(userId);
		
		List<TodoDto> result = todoService.updateTodo(todoDto);
		
		ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(result).build();
					
		return ResponseEntity.ok().body(response);
	}

	/*
	 * 게시글 옆 휴지통 아이콘 클릭 시 삭제
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
