package com.todo.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
// ユーザーエンティティ
@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="user_id") 
	private String userId;
	
	@Column(name="user_name") 
	private String userName;
	
	private String password;
	
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;
}
