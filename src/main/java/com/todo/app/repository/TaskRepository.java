package com.todo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.Task;

// Todoリポジトリ
public interface TaskRepository extends JpaRepository<Task, Long> {
	public List<Task> findByStatusEquals(Integer status);
	
	public List<Task> findByStatusEqualsAndUserTeamIdOrderByDueDate(Integer status, long team_id);
	
	public List<Task> findByStatusLessThanAndUserTeamIdOrderByDueDate(Integer status, long team_id);
}
