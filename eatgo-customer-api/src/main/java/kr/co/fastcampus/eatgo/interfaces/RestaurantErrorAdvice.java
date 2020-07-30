package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestaurantErrorAdvice {
		
	// ReponseBody를 통해 Body의 값을 반환시켜줌
	// ReponseStatus를 통해 404 상태를 돌려줌
	// 예외처리 Handler를 할 클래스를 지정
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RestaurantNotFoundException.class)
	public String handleNotFound() {
		// ResponseBody에 의해 반환할 값을 설정할 수 있음.  Body값으로 넘겨짐.
		return "{}";
	}
}
