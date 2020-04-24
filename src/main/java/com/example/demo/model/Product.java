package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
/**
 * 商品に関するデータの保存、取り出しを行う
 * @author matsumotonaoki
 *
 */
@Data
public class Product {
	
	//ID
	private int productId;
	
	//カテゴリー
	private String productCategory;

	//商品名
	private String productName;
	
	//商品金額
	private int productValue;
	
	//商品画像
	private MultipartFile image;
	
	private String productImageName;
}
