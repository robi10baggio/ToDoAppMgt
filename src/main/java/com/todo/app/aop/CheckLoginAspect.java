package com.todo.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.todo.app.model.Account;


@Aspect
@Component
public class CheckLoginAspect {
	// セッション用アカウントモデル(ユーザ情報）
	private final Account account;
	
	// コンストラクタ
	public CheckLoginAspect(Account account) {
		this.account = account;
	}

	// 未ログインの場合ログインページにリダイレクト
	@Around("execution(* com.todo.app.controller.TodoController.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getUserName() == null
				|| account.getUserName().length() == 0) {
			System.err.println("ログインしていません!");
			// リダイレクト先を指定する
			// パラメータを渡すことでログインControllerで
			// 個別のメッセージをThymeleafに渡すことも可能
			return "redirect:/login?error=notLoggedIn";
		}
		// Controller内のメソッドの実行
		return jp.proceed();
	}

}
