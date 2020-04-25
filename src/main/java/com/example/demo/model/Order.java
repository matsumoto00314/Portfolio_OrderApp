package com.example.demo.model;

import lombok.Data;
/**
 * 注文に関するデータの保存、取り出しを行う
 * @author matsumotonaoki
 *
 */
@Data
public class Order {
	
	//ID
	private int keyId;
	
	//メニューID
	private int foodId;
	
	//メニュー名
	private String foodName;
	
	//メニュー金額
	private int foodValue;
	
}
