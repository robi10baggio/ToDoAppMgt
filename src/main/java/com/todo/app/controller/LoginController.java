package com.todo.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.app.entity.Team;
import com.todo.app.entity.User;
import com.todo.app.form.LoginForm;
import com.todo.app.form.RegisterForm;
import com.todo.app.model.Account;
import com.todo.app.service.TeamServiceImpl;
import com.todo.app.service.UserServiceImpl;
// ログインコントローラ
@Controller
public class LoginController {
	// セッション用アカウントモデル(ユーザ情報）
	private final Account account;
	
	private final UserServiceImpl userService;
	
	private final TeamServiceImpl teamService;
	
	// コンストラクタ
	public LoginController(UserServiceImpl userService, TeamServiceImpl teamService, Account account) {
		this.userService = userService;
		this.teamService = teamService;
		this.account = account;
	}
	
	// チームメニュー取得
    public Map<Integer, String> getTeamsMenu() {
		Map<Integer, String> teamMap = new HashMap<>();
		List<Team> teams = teamService.findAll();
		for (Team team:teams) {
			teamMap.put((int) team.getId(), team.getTeamName());
		}
		return teamMap;
    }
	
    // セッション用アカウントモデル取得
	public Account getAccount() {
		return this.account;
	}
	  
	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String showLoginPage(
			LoginForm loginForm,
			@RequestParam(name = "error", defaultValue = "") String error,
			HttpSession session,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();
		// エラーパラメータのチェック
		if (error.equals("notLoggedIn")) {
			model.addAttribute("message", "ログインしてください");
		}

		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String loginUser(
			@Validated LoginForm loginForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "login";
		}
		User user = userService.loginAuth(loginForm.getUserId(), loginForm.getPassword());
		
		if (user == null) {
			return "login";
		}
		// セッション管理されたアカウント情報に名前をセット
		account.setUserId(user.getId());
		account.setUserName(user.getUserName());
		account.setTeamName(user.getTeam().getTeamName());
		
		// 「/todo」へのリダイレクト
		return "redirect:/todo/list";
	}
	
	// 会員登録画面を表示
	@GetMapping("/regist")
	public String showRegistForm(
			RegisterForm registerForm,
			Model model) {
		model.addAttribute("teamMenu",getTeamsMenu());
		return "regist";
		
	}
	
	// 会員登録を実行
	@PostMapping("/regist")
	public String registUser(
			@Validated RegisterForm registerForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}
		if (!registerForm.getPassword().equals(registerForm.getCheckPassword())) {
			model.addAttribute("message", "パスワードが一致しません。");
			return "register";
		}
		User user = new User();
		user.setUserId(registerForm.getUserId());
		user.setUserName(registerForm.getUserName());
		user.setPassword(registerForm.getPassword());
		Team team = teamService.findById((long)registerForm.getTeamId());
		user.setTeam(team);
		try {
			userService.regist(user);
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("message", "既にユーザIDは登録されています。");
			return "register";
		}
		model.addAttribute("teamMenu",getTeamsMenu());
		return "redirect:/login";
		
	}
}
