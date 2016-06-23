package com.example.login;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml.Encoding;
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
				String path;
				try {
					
					//对中文username和password进行URLEncode编码避免提交到服务器产生乱码
					path = "http://192.168.1.103:8080/mywe/login?username=" + URLEncoder.encode(username, "utf-8") + "&&password=" + URLEncoder.encode(password, "utf-8");
				
					//以httpclient方式进行get提交
					DefaultHttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(path);
					HttpResponse response = client.execute(get);
					int code = response.getStatusLine().getStatusCode();
					if(code == 200){
						InputStream inputStream = response.getEntity().getContent();
						String content = StreamTools.readStream(inputStream);
						showToast(content);
					}
					
					
					
					
//					URL url = new URL(path);
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					conn.setConnectTimeout(5000);
//					conn.setRequestMethod("GET");
//					int code = conn.getResponseCode();
//					
//					if(code == 200){
//						
//						InputStream in = conn.getInputStream();
//						String content = StreamTools.readStream(in);
//					
//						showToast(content);
//					}
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
				
				String path = "http://192.168.1.103:8080/mywe/login";
				
//				//设置请求体
//				String head = "username="+username+"&password="+password;
				
				
				try {
					
					DefaultHttpClient client = new DefaultHttpClient();
					HttpPost post = new HttpPost(path);
					
					List<BasicNameValuePair> lists = new ArrayList<BasicNameValuePair>();
					BasicNameValuePair nameValuePair = new BasicNameValuePair("username", username);
					BasicNameValuePair pwdValuePair = new BasicNameValuePair("password", password);
					lists.add(nameValuePair);
					lists.add(pwdValuePair);
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(lists);
					post.setEntity(entity );
					HttpResponse response = client.execute(post);
					int code = response.getStatusLine().getStatusCode();
					if(code == 200){
						InputStream inputStream = response.getEntity().getContent();
						String content = StreamTools.readStream(inputStream);
						showToast(content);
					}
					
					
					
					
//					URL url = new URL(path);
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					conn.setConnectTimeout(5000);
//					//设置请求方式为post
//					conn.setRequestMethod("POST");
//					
//					//设置头信息
//					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//					conn.setRequestProperty("Content-Length", head.length()+"");
//					
//					//发送请求信息
//					conn.setDoOutput(true);
//					OutputStream os = conn.getOutputStream();
//					os.write(head.getBytes());
//					os.close();
//					
//					int code = conn.getResponseCode();
//					
//					
//					
//					if(code == 200){
//						InputStream in = conn.getInputStream();
//						String content = StreamTools.readStream(in);
//					
//						showToast(content);
//					}
					
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
