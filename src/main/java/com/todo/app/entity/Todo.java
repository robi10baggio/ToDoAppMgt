package com.todo.app.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
// TODOエンティティ
@Entity
@Table(name="todos")
@Data
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="task_content", nullable = false)
	private String taskContent;
	
	private Integer status;
	
	@Column(name="due_date", nullable = false)
	private LocalDate dueDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
