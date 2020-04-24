package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Register;
import com.example.demo.service.OrderService;

/**
 * 初期画面処理、ログイン処理を行う
 * @author matsumotonaoki
 *
 */
@Controller
public class FirstController {
	
	//コンテナからbeanを呼び出し注入する
	@Autowired
	OrderService service;
	
	/**
	 * カテゴリーをセットする
	 * @return　カテゴリー
	 */
	@ModelAttribute("categoryList")
	public List<String> categoryList(){
		return service.selectCategory();
	}

	/**
	 * 初期画面へ遷移
	 * @return　初期画面
	 */
	@GetMapping("/index")
	public String getIndex() {
		return "html/index";
	}
	
	/**
	 * ログイン画面へ遷移
	 * @return　ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin() {
		return "login/login";
	}
	/**
	 * メイン画面へ遷移
	 * @param model　モデルオブジェクト
	 * @return　メイン画面
	 */
	@PostMapping("/main")
	public String getMain(Model model) {
		model.addAttribute("list_size",service.selectBeforeAll().size());
		model.addAttribute("contents","html/hello::hello_contents");
		return "html/main";
	}
	
	/**
	 * 新規登録画面へ遷移
	 * @param register　ユーザー情報
	 * @return　登録画面
	 */
	@GetMapping("/register")
	public String getRegister(@ModelAttribute Register register) {
		return "login/register";
	}
	
	/**
	 * 登録処理を行う
	 * @param register　ユーザー情報
	 * @param bindingResult　入力チェック
	 * @return　ログイン画面
	 */
	@PostMapping("/register")
	public String postRegister(@ModelAttribute Register register,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return getRegister(register);
		}
		service.insertRegister(register);
		return "redirect:login";
	}
	
}
