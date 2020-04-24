package com.example.demo.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

/**
 * データベースの内容変更を行う
 * @author matsumotonaoki
 *
 */
@Repository("UserDaoJdbcImpl2")
public class OrderDaoJdbcImpl2 extends OrderDaoJdbcImpl{

	//コンテナからbeanを呼び出し注入する
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * 注文前テーブルから内容を取り出す
	 * @param userId　ID
	 * @return　注文内容
	 */
	public List<Order> selectBeforeAll(String userId) {
		
		String sql = "SELECT key_id,food_name,food_value FROM main_table mt JOIN now_table nt ON mt.food_id = nt.food_id";
		
		RowMapper<Order> rowMapper = new OrderRowMapper();
		
		return jdbc.query(sql,rowMapper);
	}
}
