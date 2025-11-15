package com.todo.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.app.entity.Task;
import com.todo.app.entity.User;
import com.todo.app.form.TaskForm;
import com.todo.app.model.Account;
import com.todo.app.service.TaskServiceImpl;
import com.todo.app.service.UserServiceImpl;

// Todoコントローラ
@Controller
@RequestMapping("/todo")
public class TodoController {
	// セッション用アカウントモデル(ユーザ情報）
	private final Account account;
	
	private final TaskServiceImpl taskService;
	
	private final UserServiceImpl userService;
	// 状態メニュー
	private static Map<Integer, String> statusMenumap = new HashMap<>();

	// コンストラクタ
	public TodoController(TaskServiceImpl taskService, UserServiceImpl userService, Account account) {
		this.taskService = taskService;
		this.userService = userService;
		this.account = account;
		statusMenumap.put(0, "未着手");
    	statusMenumap.put(1, "実施中");
    	statusMenumap.put(2, "完了");
	}
	
	// 状態メニュー取得
    public static  Map<Integer, String> getStatusMenu() {
		return TodoController.statusMenumap;
    }
    
    // セッション用アカウントモデル取得
    public Account getAccount() {
		return this.account;
    }
    
    // Todoリスト更新
    private void updateList(Model model) {
    	User user = userService.findById(account.getUserId());
    	Long teamId = user.getTeam().getId();
    	List<Task> list = taskService.selectIncomplete(teamId);
		List<TaskForm> forms = new ArrayList<>();
		for (Task task:list) {
			TaskForm form = new TaskForm();
			form.setId(task.getId());
			form.setTaskContent(task.getTaskContent());
			form.setStatus(task.getStatus());
			form.setUserId(task.getUser().getId());
			form.setUserName(task.getUser().getUserName());
			form.setTeamName(task.getUser().getTeam().getTeamName());
			
			form.setDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			forms.add(form);
		}
		model.addAttribute("todos",forms);
		
		List<Task> doneList = taskService.selectComplete(teamId);
		List<TaskForm> doneForms = new ArrayList<>();
		for (Task task:doneList) {
			TaskForm form = new TaskForm();
			form.setId(task.getId());
			form.setTaskContent(task.getTaskContent());
			form.setStatus(task.getStatus());
			form.setUserId(task.getUser().getId());
			form.setUserName(task.getUser().getUserName());
			form.setTeamName(task.getUser().getTeam().getTeamName());
			
			form.setDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			doneForms.add(form);
		}

		model.addAttribute("doneTodos",doneForms);
		model.addAttribute("account",getAccount());
		model.addAttribute("statusMenu",getStatusMenu());
    }
    
    // Todoリスト表示
	@GetMapping("/list")
	public String showListPage(TaskForm todoForm, Model model) {
		updateList(model);
		return "Todo-list";
	}
	
	@GetMapping("/add")
	public String showAddTaskForm(TaskForm todoForm, Model model) {
		return "Todo-add";
	}
	
	// Todo追加
	@PostMapping("/add")
	public String addTask(
			@Validated TaskForm taskForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			return "Todo-add";
		}
		Task task = new Task();
		task.setTaskContent(taskForm.getTaskContent());
		task.setDueDate(LocalDate.parse(taskForm.getDueDate()));
		task.setStatus(0);
		User user = userService.findById(account.getUserId()); 

		task.setUser(user);
		taskService.add(task);
		
		return "redirect:/todo/list";
	}
	
	// Todo更新
	@PostMapping("/update/{id}")
	public String updateTask(
			@PathVariable Long id, 
			@Validated TaskForm todoForm) {

		Task task = new Task();
		task.setId(id);
		task.setTaskContent(todoForm.getTaskContent());
		task.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		
		task.setStatus(todoForm.getStatus());
		User user = userService.findById(account.getUserId()); 

		task.setUser(user);
		taskService.update(task);
		return "redirect:/todo/list";
	}
	
	// Todo削除
	@PostMapping("/delete/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskService.delete(id);
		return "redirect:/todo/list";
	}
}
