package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
	@Id
	@GeneratedValue
	@Setter
	private Integer id;

	@NotNull
	private Integer categoryId;

	@NotEmpty
	private String name;
	@NotEmpty
	private String city;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MenuItem> menuItems;

	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ArrayList<Review> reviews;

	public void updateInformation(String name, String city) {
		this.name = name;
		this.city = city;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = new ArrayList<>(menuItems);
	}

    public void setReviews(List<Review> reviews) {
		this.reviews = new ArrayList<>(reviews);
	}
}
