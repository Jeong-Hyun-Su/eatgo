package kr.co.fastcampus.eatgo.interfaces;

import lombok.Data;
import lombok.Getter;

@Data
public class SessionRequestDto {
    private String email;
    private String password;
}
