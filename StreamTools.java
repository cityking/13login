package com.example.login;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {
	public static String readStream(InputStream in) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len=in.read(buffer))!=-1){
			baos.write(buffer,0,len);
			
		}
		String content = new String(baos.toByteArray());
		return content;
		
	}
}
