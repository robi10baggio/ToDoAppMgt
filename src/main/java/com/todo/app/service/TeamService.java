package com.todo.app.service;

import java.util.List;

import com.todo.app.entity.Team;

public interface TeamService {

	public List<Team> findAll();
	
	public Team findById(Long id) ;
}
