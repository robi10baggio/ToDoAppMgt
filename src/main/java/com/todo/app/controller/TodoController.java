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

import com.todo.app.entity.Todo;
import com.todo.app.entity.User;
import com.todo.app.form.TodoForm;
import com.todo.app.model.Account;
import com.todo.app.service.TodoService;
import com.todo.app.service.UserService;

// Todoコントローラ
@Controller
@RequestMapping("/todo")
public class TodoController {
	// セッション用アカウントモデル(ユーザ情報）
	private final Account account;
	
	private final TodoService todoService;
	
	private final UserService userService;
	// 状態メニュー
	private static Map<Integer, String> statusMenumap = new HashMap<>();

	// コンストラクタ
	public TodoController(TodoService todoService, UserService userService, Account account) {
		this.todoService = todoService;
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
    	List<Todo> list = todoService.selectIncomplete(teamId);
		List<TodoForm> forms = new ArrayList<>();
		for (Todo todo:list) {
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTaskContent(todo.getTaskContent());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamName(todo.getUser().getTeam().getTeamName());
			
			form.setDueDate(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			forms.add(form);
		}
		model.addAttribute("todos",forms);
		
		List<Todo> doneList = todoService.selectComplete(teamId);
		List<TodoForm> doneForms = new ArrayList<>();
		for (Todo todo:doneList) {
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTaskContent(todo.getTaskContent());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamName(todo.getUser().getTeam().getTeamName());
			
			form.setDueDate(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			doneForms.add(form);
		}

		model.addAttribute("doneTodos",doneForms);
		model.addAttribute("account",getAccount());
		model.addAttribute("statusMenu",getStatusMenu());
    }
    
    // Todoリスト表示
	@GetMapping("/list")
	public String list(TodoForm todoForm, Model model) {
		updateList(model);
		return "Todo-list";
	}

	// Todo追加
	@PostMapping("/add")
	public String add(
			@Validated TodoForm todoForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			updateList(model);
			return "Todo-list";
		}
		Todo todo = new Todo();
		todo.setTaskContent(todoForm.getTaskContent());
		todo.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		todo.setStatus(0);
		User user = userService.findById(account.getUserId()); 

		todo.setUser(user);
		todoService.add(todo);
		
		return "redirect:/todo/list";
	}
	
	// Todo更新
	@PostMapping("/update/{id}")
	public String update(
			@PathVariable Long id, 
			@Validated TodoForm todoForm) {

		Todo todo = new Todo();
		todo.setId(id);
		todo.setTaskContent(todoForm.getTaskContent());
		todo.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		
		todo.setStatus(todoForm.getStatus());
		User user = userService.findById(account.getUserId()); 

		todo.setUser(user);
		todoService.update(todo);
		return "redirect:/todo/list";
	}
	
	// Todo削除
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		todoService.delete(id);
		return "redirect:/todo/list";
	}
}
