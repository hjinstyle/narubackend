package naru.narucof.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

	private String id; //�� ������Ʈ�� ���̵�
	private String userId; //�������̵�
	private String title; //Todo Ÿ��Ʋ(��:��ϱ�)
	private boolean done; //true - todo�� �Ϸ��� ���(checked) 


}
