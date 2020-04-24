package com.example.demo.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Register;
import com.example.demo.repository.OrderDao;

/**
 * データベースの内容変更を行う
 * @author matsumotonaoki
 *
 */
@Repository("UserDaoJdbcImpl")
public class OrderDaoJdbcImpl implements OrderDao{
	
	//コンテナからbeanを呼び出し注入する
	@Autowired
	JdbcTemplate jdbc;
	
	//コンテナからbeanを呼び出し注入する
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * 注文前のテーブルの中身の数を取り出す
	 */
	@Override
	public int count() throws DataAccessException{
		
		int count = jdbc.queryForObject("select count(food_id) as food_id from now_table", Integer.class);
		return count;
	}
	
	/**
	 * ユーザーの登録処理を行う
	 */
	@Override
	public int insertRegisterOne(Register register) throws DataAccessException{
		String password = passwordEncoder.encode(register.getPassword());
		
		int rowNumber = jdbc.update("INSERT INTO user_table(user_id,password,role)VALUES(?,?,'ROLE_GUEST')",register.getUserId(),password);
		
		return rowNumber;
	}
	
	/**
	 * 注文前テーブルに追加を行う
	 */
	@Override
	public int insertBefore(Integer foodId) throws DataAccessException{
		
		// FOODIDをINSERTする
		int rowNumber = jdbc.update("INSERT INTO now_table(food_id)VALUES(?)",foodId);
		return rowNumber;
	}
	
	/**
	 * 注文前テーブルの中身を取り出す
	 */
	@Override
	public List<Order> selectBeforeAll() throws DataAccessException{
		
		//main_tableのデータを全件取得
		List<Map<String,Object>> getList = jdbc.queryForList("SELECT NT.KEY_ID,PRODUCT_NAME,PRODUCT_VALUE FROM PRODUCT_TABLE PT JOIN NOW_TABLE NT ON PT.KEY_ID = NT.FOOD_ID");
		
		List<Order> userList = new ArrayList<>();
		
		for(Map<String,Object>  map: getList) {
			
			//Dataインスタンス化
			Order order = new Order();

			//取得したデータを代入
			order.setFoodId((Integer)map.get("KEY_ID"));
			order.setFoodName((String)map.get("PRODUCT_NAME"));
			order.setFoodValue((Integer)map.get("PRODUCT_VALUE"));
			//Listにセット
			userList.add(order);
		}
		return userList;
	}
	
	/**
	 * 注文後テーブルの中身を取り出す
	 */
	@Override
	public List<Order> selectAfterAll() throws DataAccessException{
		
		//main_tableのデータを全件取得
		List<Map<String,Object>> getList = jdbc.queryForList("SELECT NT.KEY_ID,PRODUCT_NAME,PRODUCT_VALUE FROM PRODUCT_TABLE PT JOIN AFTER_TABLE NT ON PT.KEY_ID = NT.FOOD_ID");
		
		List<Order> userList = new ArrayList<>();
		
		for(Map<String,Object>  map: getList) {
			
			//Dataインスタンス化
			Order order = new Order();

			//取得したデータを代入
			order.setFoodId((Integer)map.get("KEY_ID"));
			order.setFoodName((String)map.get("PRODUCT_NAME"));
			order.setFoodValue((Integer)map.get("PRODUCT_VALUE"));
			
			//Listにセット
			userList.add(order);
		}
		return userList;
	}
	
	/**
	 * 注文前テーブルの内容を全て削除
	 */
	@Override
	public int deleteBeforeAll() {
		int rowNumber = jdbc.update("DELETE FROM NOW_TABLE");
		return rowNumber;
	}
	
	/**
	 * 注文前テーブルの内容を1件削除
	 */
	@Override
	public int deleteOne(String foodId) {
		int rowNumber = jdbc.update("DELETE FROM NOW_TABLE WHERE key_id = ?",foodId);
		return rowNumber;
	}
	/**
	 * 注文前テーブルから注文後テーブルに内容を移動
	 */
	@Override
	public int insertMove() {
		
		//main_tableのデータを全件取得
		List<Map<String,Object>> getList = jdbc.queryForList("SELECT * FROM NOW_TABLE");
		
		List<Order> orderList = new ArrayList<>();
		
		for(Map<String,Object>  map: getList) {
			
			//Dataインスタンス化
			Order order = new Order();

			//取得したデータを代入
			order.setKeyId((Integer)map.get("KEY_ID"));
			order.setFoodId((Integer)map.get("FOOD_ID"));
			
			//Listにセット
			orderList.add(order);
		}
		int rowNumber = 0;
		
		for(int i = 0; i < orderList.size(); i++) {
			rowNumber = jdbc.update("INSERT INTO AFTER_TABLE(key_id,food_id)VALUES(?,?)",orderList.get(i).getKeyId(),orderList.get(i).getFoodId());
		}
			return rowNumber;	
	}
	
	/**
	 * カテゴリーに内容を追加する
	 */
	@Override
	public int insertCategory(String categoryName) throws DataAccessException{
		int rowNumber = jdbc.update("INSERT INTO CATEGORY_TABLE(CATEGORY_NAME)VALUES(?)",categoryName);
		return rowNumber;	
	}
	
	/**
	 * カテゴリーを検索する
	 */
	@Override
	public List<String> selectCategory() throws DataAccessException{
		List<Map<String,Object>> getList = jdbc.queryForList("SELECT CATEGORY_NAME FROM CATEGORY_TABLE");
		List<String> categoryList = new ArrayList<>();
	
		for(Map<String,Object>  map: getList) {
			Product product = new Product();
			
			//取得したデータを代入
			product.setProductCategory((String)map.get("CATEGORY_NAME"));
			
			//Listにセット
			categoryList.add(product.getProductCategory());
		}
		return categoryList;
	}
	/**
	 * 商品を追加する
	 */
	@Override
	public int insertProduct(Product product) {
		int rowNumber = jdbc.update("INSERT INTO PRODUCT_TABLE (PRODUCT_CATEGORY,PRODUCT_NAME,PRODUCT_VALUE,PRODUCT_IMAGE)VALUES(?,?,?,?)",product.getProductCategory(),product.getProductName(),product.getProductValue(),product.getImage().getOriginalFilename());
		return rowNumber;
	}
	
	@Override
	public List<Product> selectProduct(String categoryName){
		List<Map<String,Object>> getList = jdbc.queryForList("SELECT KEY_ID,PRODUCT_CATEGORY,PRODUCT_NAME,PRODUCT_VALUE,PRODUCT_IMAGE FROM PRODUCT_TABLE WHERE PRODUCT_CATEGORY = ?",categoryName);
		List<Product> productList = new ArrayList<>();
	
		for(Map<String,Object>  map: getList) {
			Product product = new Product();
			
			//取得したデータを代入
			product.setProductId((Integer)map.get("KEY_ID"));
			product.setProductCategory((String)map.get("PRODUCT_CATEGORY"));
			product.setProductName((String)map.get("PRODUCT_NAME"));
			product.setProductValue((Integer)map.get("PRODUCT_VALUE"));
			product.setProductImageName((String)map.get("PRODUCT_IMAGE"));
			//Listにセット
			productList.add(product);
		}
		return productList;
	}
}
