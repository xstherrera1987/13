package net.joeherrera.Thirteen.rminet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.joeherrera.Thirteen.rminet.Io;

public class IoInteractiveTest {
	InputStream is;
	OutputStream os;
	Io testIo;
	
	public IoInteractiveTest() {
		os = new ByteArrayOutputStream();
	}
	
	public void testGetInteger() {
		testIo = new Io();
		
		int result = 0;
		try {
			result = testIo.getInteger("Enter Integer (1<x<5): ", "retry integer: ", 1, 5);		
		} catch(IOException e) { }
		
		System.out.println(result);
	}
}
