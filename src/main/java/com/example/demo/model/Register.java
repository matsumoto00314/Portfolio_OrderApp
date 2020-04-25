package com.example.demo.model;

import lombok.Data;
/**
 * ユーザーに関するデータの保存、取り出しを行う
 * @author matsumotonaoki
 *
 */
@Data
public class Register {

	//ユーザーID
	private String userId;
	
	//パスワード
	private String password;

}
