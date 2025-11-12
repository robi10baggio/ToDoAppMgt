package com.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.User;

// ユーザリポジトリ
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserIdAndPassword(String userId, String password);
}
