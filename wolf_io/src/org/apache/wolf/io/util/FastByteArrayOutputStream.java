package org.apache.wolf.io.util;

import java.io.IOException;
import java.io.OutputStream;

public class FastByteArrayOutputStream extends OutputStream{

	private int count;
	private byte[] buf;
	
	public FastByteArrayOutputStream(){
		buf=new byte[32];
	}

	@Override
	public void write(int oneByte) throws IOException {
		if(count==buf.length){
			expand(1);
		}
		buf[count++]=(byte)oneByte;
	}

	private void expand(int i) {
		if(count+i<=buf.length)
			return;
		byte[] newbuf=new byte[(count+i)*2];
		System.arraycopy(buf, 0, newbuf, 0, count);
		buf=newbuf;
	}

	public byte[] toByteArray() {
		byte[] newArray=new byte[count];
		System.arraycopy(buf, 0, newArray, 0, count);
		return newArray;
	}

}
