package com.br.api.models.addon;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 150, nullable = false, unique = false)
	private String username;

	@Column(length = 150, nullable = false, unique = false)
	private String password;
	
//	@Column(nullable = false)
//	private String company;
//	
//	@Column(nullable = false)
//	private String application;
	
	@Column(nullable = false)
	private String role;
	
	

}
