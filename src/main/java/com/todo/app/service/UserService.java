package com.todo.app.service;

import com.todo.app.entity.User;

public interface UserService {
	public User loginAuth(String userName, String password);
	
	public User findById(Long id);
	
	public void regist(User user) ;
}
