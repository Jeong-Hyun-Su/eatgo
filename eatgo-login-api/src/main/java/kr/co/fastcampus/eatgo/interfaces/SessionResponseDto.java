package kr.co.fastcampus.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SessionResponseDto {
    private String accessToken;
}
