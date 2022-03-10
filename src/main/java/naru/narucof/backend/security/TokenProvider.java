package naru.narucof.backend.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import naru.narucof.backend.dto.UserDto;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY = "NMA8JPctFuna59f5";

	/*�α��� �� ��� ��ȣȭ ���� �޼���
	 * 
	 */
	public String create(UserDto userDto) {
		// ���� �������κ��� 1�Ϸ� ����
		Date expiryDate = Date.from(
						Instant.now()
						.plus(1, ChronoUnit.DAYS));

		/*
		{ // header
		  "alg":"HS512"
		}.
		{ // payload
		  "sub":"40288093784915d201784916a40c0001",
		  "iss": "demo app",
		  "iat":1595733657,
		  "exp":1596597657
		}.
		// SECRET_KEY�� �̿��� ������ �κ�
		Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWkRv50_l7bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg
		 */
		// JWT Token ����
		return Jwts.builder()
						// header�� �� ���� �� ������ �ϱ� ���� SECRET_KEY
						.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
						// payload�� �� ����
						.setSubject(userDto.getUserId()) // sub     //subject�� ���̵� ���� �����. ���� ���ڵ� �� subject �����Ѵ�.
						.setIssuer("demo app") // iss
						.setIssuedAt(new Date()) // iat
						.setExpiration(expiryDate) // exp
						.compact();
	}

	/*ȭ������ �� ���� ����Ǵ� ��ū ��ȣȭ �Ľ� �޼���
	 * 
	 * */
	public String validateAndGetUserId(String token) {
		/*������ : ȭ�鿡������ ��ū����-> ���ͼ��Ϳ��� ��ū������ �Ǵ�-> ��ū �����Ѵٸ� 
		 *     -> �̸޼��忡�� �ش� ��ū�� ���ڷ� �޾Ƽ� �Ľ��Ѵ�(�Ľ̹��:SECRET_KEY����� ��ū�� ��ȣȭ��) -> ��ȣȭ�� �� �� userId�� �������ش�.
		 */
		
		// parseClaimsJws�޼��尡 Base 64�� ���ڵ� �� �Ľ�.
		// ��, ����� ���̷ε带 setSigningKey�� �Ѿ�� ��ũ���� �̿� �� ���� ��, token�� ���� �� ��.
		// �������� �ʾҴٸ� ���̷ε�(Claims) ����
		// �� �� �츮�� userId�� �ʿ��ϹǷ� getBody�� �θ���.
		Claims claims = Jwts.parser()
						.setSigningKey(SECRET_KEY)  //���� �ۼ��� ���Ű�� ����
						.parseClaimsJws(token)  //base64�� ���ڵ� �� �Ľ�
						.getBody(); //�ٵ� ���� ����

		return claims.getSubject();    //���ڵ� �� ������Ʈ�� ���̵� ����������� �����Ѵ�.
	}
}
