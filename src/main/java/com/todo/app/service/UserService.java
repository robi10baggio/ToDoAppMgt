package com.todo.app.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.repository.UserRepository;

// ユーザサービス
@Service
public class UserService {
	private final UserRepository loginRepository;

	public UserService(UserRepository loginRepository) {
		this.loginRepository = loginRepository;
	}
	
	public User loginAuth(String userName, String password) {
		return loginRepository.findByUserIdAndPassword(userName, password);
	}
	
	public User findById(Long id) {
		return loginRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void regist(User user) {
		loginRepository.save(user);
	}
}
