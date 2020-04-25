$(function(){

	/*
	 * 削除チェックを行う
	 */
	$('input[name="delete"]').on('click',function(){
		
		//return結果を代入する
		var result = false;
		
		//ラジオボタンにチェックがあった場合
		if($("input[name='foodId']:checked").val()){
			
			//削除の最終確認を行う
			if(confirm("本当に削除しますか？")){
				result = true;
			}
			
		//ラジオボタンにチェックがなかった場合
		}else{
			alert("削除する内容を選択して下さい");
		}
		
		//結果に応じて遷移先をコントロール
		return result;
	});
	
	
});