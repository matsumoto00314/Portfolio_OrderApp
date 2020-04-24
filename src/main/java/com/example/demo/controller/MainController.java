package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Product;
import com.example.demo.service.OrderService;

/**
 * メイン画面のコンテンツ部分を動的に表示を行う
 * @author matsumotonaoki
 *
 */
@Controller
public class MainController {
	
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
	 * メニューを動的に表示を行う
	 * @param model　モデルオブジェクト
	 * @return　メイン画面
	 */
	@PostMapping("/mainList")
	public String postMainList(@RequestParam("categoryName")String categoryName,Model model) {
		System.out.println(categoryName);
		model.addAttribute("productList",service.selectProduct(categoryName));
		model.addAttribute("list_size",service.selectBeforeAll().size());
		model.addAttribute("contents","html/mainList::mainList_contents");
		return "html/main";
	}
	/**
	 * 管理画面へ遷移
	 * @param product　メニュー
	 * @param model　モデルオブジェクト
	 * @return
	 */
	@GetMapping("/adminList")
	public String getAdmin(@ModelAttribute Product product,Model model) {
		model.addAttribute("contents","html/adminList::adminList_contents");
		return "html/main";
	}
}
