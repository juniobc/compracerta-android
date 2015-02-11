package br.com.compracerta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ScannerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
	}
		
	public void scan(View view){
		
		Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
		intent.setPackage(getPackageName());
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE"); 
		//intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
		startActivityForResult(intent, 0);
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		
		if(requestCode == 0){
			
			if(resultCode == RESULT_OK){
				
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Log.i("xZing", "contents: "+contents+" format: "+format);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				
				builder.setMessage(contents).create().show();
				
			}else if(resultCode == RESULT_CANCELED){
				
				Log.i("xZing", "Cancelled");
				
			}
			
		}
		
	}

}
