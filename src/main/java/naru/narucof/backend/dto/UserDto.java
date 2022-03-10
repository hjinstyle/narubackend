package naru.narucof.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private String userId;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String token;
	
		
	

}
