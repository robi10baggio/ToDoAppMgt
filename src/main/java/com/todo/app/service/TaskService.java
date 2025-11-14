package com.todo.app.service;

import java.util.List;

import com.todo.app.entity.Task;

public interface TaskService {

	public List<Task> selectAll();

	public List<Task> selectIncomplete(long team_id);

	public List<Task> selectComplete(long team_id);

	public void add(Task task);

	public void update(Task task);

	public void delete(long id);
}
