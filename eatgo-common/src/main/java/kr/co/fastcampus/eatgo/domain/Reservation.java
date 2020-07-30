package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id @GeneratedValue
    private Integer id;

    private Integer restaurantId;
    private Integer userId;
    private String name;
    @NotEmpty
    private String date;
    @NotEmpty
    private String time;
    @NotNull
    private Integer partySize;

}
