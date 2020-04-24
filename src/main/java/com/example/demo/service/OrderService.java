package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Register;
import com.example.demo.repository.OrderDao;
/**
 * リポジトリークラスなどを使ったサービスを行う
 * @author matsumotonaoki
 *
 */
@Service
public class OrderService {
	
	//コンテナからbeanを呼び出し注入する
	@Autowired
	@Qualifier("UserDaoJdbcImpl2")
	OrderDao dao;
	
	/**
	 * 注文前テーブルの注文数のカウントを行う
	 * @return　注文数
	 */
	public int count() {
		return dao.count();
	}
	
	/**
	 * ユーザー登録を行う
	 * @param register　ユーザー情報
	 * @return　追加処理の結果
	 */
	public boolean insertRegister(Register register) {
		int rowNumber = dao.insertRegisterOne(register);
		
		boolean result = false;
		
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 注文前テーブルに追加処理を行う
	 * @param foodId　ID
	 * @return　追加処理の結果
	 */
	public boolean insertBefore(Integer foodId) {
		int rowNumber = dao.insertBefore(foodId);
		
		//判定用変数を宣言
		boolean result = false;
		
		if(rowNumber > 0) {
			
			result = true;
		}
		return result;
	}
	
	/**
	 * 注文前テーブル内容を取り出す
	 * @return 注文内容
	 */
	public List<Order> selectBeforeAll(){
		return dao.selectBeforeAll();
	}
	
	/**
	 * 注文後テーブル内容を取り出す
	 * @return 注文内容
	 */
	public List<Order> selectAfterAll(){
		return dao.selectAfterAll();
	}
	
	/**
	 * 注文前テーブルの内容を全件削除を行う
	 * @return　注文前テーブル
	 */
	public int deleteBeforeAll() {
		return dao.deleteBeforeAll();
	}
	
	/**
	 * 注文前テーブルから1件の削除を行う
	 * @param foodId　ID
	 * @return　注文前テーブル
	 */
	public int deleteOne(String foodId) {
		return dao.deleteOne(foodId);
	}
	
	/**
	 * 注文前テーブルから注文後テーブルに内容を移す
	 * @param list　注文前テーブル
	 * @return　注文後テーブル
	 */
	public int insertMove() {
		return dao.insertMove();
	}
	
	/**
	 * カテゴリーを追加する
	 * @param categoryName　カテゴリ名
	 * @return　追加処理結果
	 */
	public int insertCategory(String categoryName) {
		return dao.insertCategory(categoryName);
	}
	
	/**
	 * カテゴリーをlistに格納
	 * @return　カテゴリー内容
	 */
	public List<String> selectCategory(){
		return dao.selectCategory();
	}
	
	/**
	 * 商品を追加する
	 * @param product　商品情報
	 * @return　追加処理結果
	 */
	public int insertPruduct(Product product) {
		return dao.insertProduct(product);
	}
	
	/**
	 * 商品をカテゴリー別で検索する
	 * @param categoryName
	 * @return　検索処理結果
	 */
	public List<Product> selectProduct(String categoryName){
		return dao.selectProduct(categoryName);
	}
}
