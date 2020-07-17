package kr.co.fastcampus.eatgo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String city;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MenuItem> menuItems;
	
	public void setMenuItems(List<MenuItem> menuItems) {
		menuItems = new ArrayList<>(menuItems);
	}
	public void updateInformation(String name, String city) {
		this.name = name;
		this.city = city;
	}
}
