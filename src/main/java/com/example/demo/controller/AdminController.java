package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
 * 管理画面から追加削除を行う
 * @author matsumotonaoki
 *
 */
@Controller
public class AdminController {

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
	 * 内容の追加を行う
	 * @return　メイン画面
	 */
	@PostMapping("/admin") 
	public String postAdmin(@RequestParam("categoryName") String categoryName,Product product,Model model){
		try {
			String uploadPath = "src/main/resources/static/img/";
			String fileName = product.getImage().getOriginalFilename();
			byte[] bytes = product.getImage().getBytes();
			
			File uploadFile = new File(uploadPath+fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			
			stream.write(bytes);
			stream.close();
			product.setProductCategory(categoryName);
			service.insertPruduct(product);
			model.addAttribute("successMsg","登録しました");
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("contents","html/adminList::adminList_contents");
		return "html/main";
	}
	
	/**
	 * カテゴリー追加画面に遷移
	 * @param model　モデルオブジェクト
	 * @return　カテゴリー追加画面
	 */
	@GetMapping("/category")
	public String getCategory(Model model) {
		model.addAttribute("contents","html/category::category_contents");
		return "html/main";
	}
	
	/**
	 * 
	 * @param categoryName
	 * @param model
	 * @return
	 */
	@PostMapping("/addCategory")
	public String postAddCategory(@RequestParam("categoryName")String categoryName,Model model) {
		service.insertCategory(categoryName);
		model.addAttribute("successMsg","登録しました");
		model.addAttribute("contents","html/category::category_contents");
		
		return "html/main";
	}
}
