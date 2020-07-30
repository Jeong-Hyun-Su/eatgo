package kr.co.fastcampus.eatgo.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RestaurantVO implements Serializable {
    private Integer category;
    private String region;
}
