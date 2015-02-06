package br.com.compracerta;

import com.google.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends CaptureActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
	}

}
