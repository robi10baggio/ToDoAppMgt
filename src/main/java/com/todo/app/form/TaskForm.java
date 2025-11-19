package com.todo.app.form;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;
// TODOフォーム
@Data
public class TaskForm {
	public Long id;
	
    @Size(min = 1, max = 200, message = "{0}は{1}文字以内{2}文字以上で入力してください。")
    private String taskContent;
	
    private Integer status;
	
	@NotEmpty
	private String dueDate;
	
	private Long userId;
	private String userName;
	
	private String teamName;
}
