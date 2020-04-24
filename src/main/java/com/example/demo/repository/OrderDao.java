package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Register;

/**
 * データベースの内容変更を行う
 * @author matsumotonaoki
 *
 */
public interface OrderDao {
	
	/**
	 * ユーザー情報を登録する
	 * @param register　ユーザー情報
	 * @return　追加処理結果
	 * @throws DataAccessException
	 */
	public int insertRegisterOne(Register register) throws DataAccessException; 

	/**
	 * 注文前テーブルに追加する
	 * @param foodId　ID
	 * @return　追加処理結果
	 * @throws DataAccessException
	 */
	public int insertBefore(Integer foodId) throws DataAccessException;
	
	/**
	 * 注文前テーブルから注文後テーブルに内容を移す
	 * @param list　注文前テーブル
	 * @return　追加処理結果
	 * @throws DataAccessException
	 */
	public int insertMove() throws DataAccessException;
	
	/**
	 * 注文前テーブル内容を取得する
	 * @return　注文後テーブル
	 * @throws DataAccessException
	 */
	public List<Order> selectBeforeAll() throws DataAccessException;
	
	/**
	 * 注文前テーブルの注文数
	 * @return　注文数
	 * @throws DataAccessException
	 */
	public int count() throws DataAccessException;
	
	/**
	 * 注文前テーブルの内容を削除する
	 * @return　削除処理結果
	 * @throws DataAccessException
	 */
	public int deleteBeforeAll() throws DataAccessException;

	/**
	 * 注文前テーブルの内容を選択した1件を削除する
	 * @param userId　ID
	 * @return　削除処理結果
	 * @throws DataAccessException
	 */
	public int deleteOne(String userId) throws DataAccessException;
	
	/**
	 * カテゴリーの追加を行う
	 * @return　追加処理結果
	 * @throws DataAccessException
	 */
	public int insertCategory(String categoryName) throws DataAccessException;
	
	/**
	 * カテゴリーの検索を行う
	 * @return　検索処理結果
	 * @throws DataAccessException
	 */
	public List<String> selectCategory() throws DataAccessException;
	
	/**
	 * 商品の追加を行う
	 * @return　追加処理結果
	 * @throws DataAccessException
	 */
	public int insertProduct(Product product) throws DataAccessException;
	
	/**
	 * カテゴリー別で商品を検索する
	 * @return　検索処理結果
	 * @throws DataAccessException
	 */
	public List<Product> selectProduct(String categoryName) throws DataAccessException;
	
	/**
	 * 注文後テーブルを表示する
	 * @return　検索処理結果
	 * @throws DataAccessException
	 */
	public List<Order> selectAfterAll() throws DataAccessException;
}
