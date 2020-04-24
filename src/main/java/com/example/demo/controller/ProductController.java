package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
/**
 * 注文の処理を行う
 * @author matsumotonaoki
 *
 */
@Controller
public class ProductController {
	
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
	 * 注文内容を受け取り処理を行う
	 * @param food　メニュー内容
	 * @param order　メニュー情報
	 * @param model　モデルオブジェクト
	 * @return　メインページへ遷移
	 */
	@PostMapping("/food")
	public String postMain(@RequestParam("foodId")String food,@RequestParam("categoryName")String categoryName,Order order,Model model){

		//メニュー番号を受け取る
		order.setFoodId(Integer.parseInt(food));
		
		//注文数をcountに代入する
		int count = service.count();
		
		//注文数が５以下だった場合
		if(count < 5) {
			
			//注文前テーブルにインサートする
			service.insertBefore(order.getFoodId());
			
		}else {
			
			//エラーメッセージをセットする
			model.addAttribute("errMsg","一度注文を行って下さい");
		}
	
		//商品リストをセット
		model.addAttribute("productList",service.selectProduct(categoryName));
		
		//メインメニューリストのhtmlをセット
		model.addAttribute("contents","html/mainList::mainList_contents");
		
		//注文前のテーブルに入っている数を渡す
		model.addAttribute("list_size",service.selectBeforeAll().size());
		return "html/main";
	}
	/**
	 * 注文前テーブル内容を確認を行う
	 * @param model　モデルオブジェクト
	 * @return　メインページへ遷移
	 */
	@GetMapping("/cart")
	public String getCart(Model model) {
	
		//注文前テーブル内容を取り出す
		model.addAttribute("list",service.selectBeforeAll());
		
		//注文前テーブル内容をチェックするhtmlをセット
		model.addAttribute("contents","html/cart::cart_contents");
		
		return "html/main";
	}
	
	/**
	 * お会計の画面へ遷移
	 * @param model
	 * @return
	 */
	@GetMapping("/check")
	public String getCheck(Model model) {
		
		//注文後テーブルをセットする
		model.addAttribute("list",service.selectAfterAll());
		
		//お会計画面の内容のhtmlをセット
		model.addAttribute("contents","html/check::check_contents");
		return "html/main";
	}
	
	/**
	 * 注文前テーブルに入っている内容の削除を行う
	 * @param foodId　ID
	 * @param model　モデルオブジェクト
	 * @return　メインページへ遷移
	 */
	@PostMapping(value="/divide",params="delete")
	public String postDelete(@RequestParam("foodId")String foodId,Model model) {
		System.out.println(foodId);
		//IDを受け取り削除を行う
		service.deleteOne(foodId);
		
		//注文前テーブル内容のhtmlをセット
		model.addAttribute("contents","html/cart::cart_contents");
		
		//注文前テーブル内容を取り出す
		model.addAttribute("list",service.selectBeforeAll());
		
		return "html/main";
	}
	
	/**
	 * 注文後テーブルに内容の移動を行う
	 * @param model　モデルオブジェクト
	 * @return　メインページへ遷移
	 */
	@PostMapping(value="/divide",params="insert")
	public String postInsert(Model model) {
		
		//注文前テーブルから注文後テーブルに内容を移す
		service.insertMove();
		
		//注文前テーブルの内容を削除する
		service.deleteBeforeAll();
		
		//注文完了の確認のhtmlをセット
		model.addAttribute("successMsg","注文が完了しました");
		model.addAttribute("contents","html/doneOrder::doneOrder_contents");
		
		return "html/main";
	}
	/**
	 * 会計終了画面へ遷移
	 * @param model
	 * @return
	 */
	@PostMapping(value="/divide",params="paid")
	public String postPaid(Model model) {
		
		//会計のメッセージをセット
		model.addAttribute("successMsg","お会計をします、お待ちください。");
		
		//会計完了のhtmlをセット
		model.addAttribute("contents","html/doneOrder::doneOrder_contents");
		
		return "html/main";
	}
}
