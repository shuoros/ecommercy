package io.github.shuoros.allAboutSpring.model.schema;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * simple entity class with username as it's primary key (aka id if you're dealing with NoSQL)
 * @since v1.0.1
 */

/*
use lombok to further reduce boilerplate code
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString
public class User {

	@Id // used by Relational DBs
	private String userName;
	private String password;
}

