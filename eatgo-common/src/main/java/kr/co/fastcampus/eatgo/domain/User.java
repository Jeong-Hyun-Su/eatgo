package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Setter
    @NotEmpty
    private String name;
    @Setter
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @Setter
    @NotNull
    private Integer level;

    @Setter
    private Integer restaurantId;

    public boolean isAdmin(){
        return level == 3;
    }

    public boolean isActive() {
        return level != 0;
    }

    public void deactive(){
        level = 0;
    }

    public boolean isRestaurantOwner(){
        return level == 2;
    }
}

