package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	public String postAdmin(@RequestParam("categoryName") String categoryName,
			@ModelAttribute @Validated Product product,BindingResult bindingResult,Model model){
	
		try {
			
			//バインドエラーが合った場合
			if(bindingResult.hasErrors()) {
				
				//メインコントローラークラスをインスタンス化
				MainController main = new MainController();
				
				//管理画面へ遷移
				return main.getAdmin(product, model);
			}
			
			//画像の保存先を選択
			String uploadPath = "src/main/resources/static/img/";
			String fileName = product.getImage().getOriginalFilename();
			
			//画像のバイトを取得
			byte[] bytes = product.getImage().getBytes();
			
			//ファイルを指定先へ保存
			File uploadFile = new File(uploadPath+fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			stream.write(bytes);
			stream.close();
			
			//カテゴリーの値をセット
			product.setProductCategory(categoryName);
			
			//商品の当路処理を行う
			service.insertPruduct(product);
			
			//登録成功のメッセージをセット
			model.addAttribute("successMsg","登録しました");
		}catch(Exception e) {
			
			//エラーメッセージをセット
			model.addAttribute("errMsg","画像を選択して下さい");
			e.printStackTrace();
		}
		
		//管理画面へのhtmlをセット
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
		
		//カテゴリー追加画面へのhtmlをセット
		model.addAttribute("contents","html/category::category_contents");
	
		return "html/main";
	}
	
	/**
	 * カテゴリー追加処理を行う
	 * @param categoryName　カテゴリー
	 * @param model　モデルオブジェクト
	 * @return　カテゴリー追加画面へ遷移
	 */
	@PostMapping("/addCategory")
	public String postAddCategory(@RequestParam("categoryName")String categoryName,Model model) {
		
		//カテゴリーの追加を行う
		service.insertCategory(categoryName);
		
		//登録成功のメッセージをセット
		model.addAttribute("successMsg","登録しました");
		
		//カテゴリー追加画面へのhtmlをセット
		model.addAttribute("contents","html/category::category_contents");
		
		return "html/main";
	}
}
