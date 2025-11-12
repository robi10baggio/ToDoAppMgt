package com.todo.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.app.entity.Team;
import com.todo.app.repository.TeamRepository;

// チームサービス
@Service
public class TeamService {
	private final TeamRepository teamRepository;
	
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public List<Team> findAll() {
		return   teamRepository.findAll();
	}
	
	public Team findById(Long id) {
		return teamRepository.findById(id).orElse(null);
	}
}
