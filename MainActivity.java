package com.example.login;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText ed_username;
	EditText ed_password;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ed_username = (EditText) findViewById(R.id.ed_username);
		ed_password = (EditText) findViewById(R.id.ed_password);

	}

	public void click1(View v) {

		new Thread() {
			public void run() {
				String username = ed_username.getText().toString().trim();
				String password = ed_password.getText().toString().trim();
				System.out.println("username:"+username+" password:"+password);
				String path = "http://169.254.249.1:8080/mywe/login?username=" + username + "&&password=" + password;
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					int code = conn.getResponseCode();
					
					if(code == 200){
						
						InputStream in = conn.getInputStream();
						String content = StreamTools.readStream(in);
					
						showToast(content);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			};
		}.start();
	}

	public void click2(View v) {
		new Thread() {
			public void run() {
				String username = ed_username.getText().toString().trim();
				String password = ed_password.getText().toString().trim();
				
				//设置请求体
				String head = "username="+username+"&password="+password;
				
				String path = "http://169.254.249.1:8080/mywe/login";
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					//设置请求方式为post
					conn.setRequestMethod("POST");
					
					//设置头信息
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", head.length()+"");
					
					//发送请求信息
					conn.setDoOutput(true);
					OutputStream os = conn.getOutputStream();
					os.write(head.getBytes());
					os.close();
					
					int code = conn.getResponseCode();
					
					System.out.println("code="+code);
					
					if(code == 200){
						
						
						
						
						
						InputStream in = conn.getInputStream();
						String content = StreamTools.readStream(in);
					
						showToast(content);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			};
		}.start();

	}
	public void showToast(final String content){
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), content, 1).show();
			}
		});
	}

}
