package com.example.demo.model;

import lombok.Data;
/**
 * 注文に関するデータの保存、取り出しを行う
 * @author matsumotonaoki
 *
 */
@Data
public class Order {
	
	private int keyId;
	private int foodId;
	private String foodName;
	private int foodValue;
	
}
