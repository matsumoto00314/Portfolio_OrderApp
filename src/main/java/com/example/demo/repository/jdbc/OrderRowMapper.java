package com.example.demo.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Order;

public class OrderRowMapper implements RowMapper<Order> {
	
	@Override
	public Order mapRow(ResultSet rs,int rowNum) throws SQLException{
		Order order = new Order();
		
		order.setFoodId(rs.getInt("key_id"));
		order.setFoodName(rs.getString("food_name"));
		order.setFoodValue(rs.getInt("food_value"));
		
		return order;
	}
}
