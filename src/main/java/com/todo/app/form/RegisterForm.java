package com.todo.app.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;
// ユーザ登録フォーム
@Data
public class RegisterForm {
	@NotEmpty
	@Size(max = 40, message = "{0}は{1}文字以内で入力してください。")
	@Email(message = "{0}はメールアドレスの形式で入力してください。")
	private String userId;
	
	@NotEmpty
	@Size(max = 40, message = "{0}は{1}文字以内で入力してください。")
	private String userName;
	
	@Size(min = 5, max = 40, message = "{0}は{1}文字以内{2}文字以上で入力してください。")
	private String password;
	
	private Integer teamId;
	
	@Size(max = 40, min = 5,  message = "{0}は{1}文字以内{2}文字以上で入力してください。")
	private String checkPassword;
}
