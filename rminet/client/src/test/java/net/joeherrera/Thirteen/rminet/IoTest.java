package net.joeherrera.Thirteen.rminet;

import static org.junit.Assert.*;
import net.joeherrera.Thirteen.rminet.Io;

import org.junit.*;

import java.io.*;

public class IoTest {
	InputStream is;
	OutputStream os;
	Io testIo;
	
	@Before
	public void init() {
		os = new ByteArrayOutputStream();
	}

	@Test
	public void testGetInteger() {
		String input = "1234\n\n";
		is = new ByteArrayInputStream( input.getBytes() );
		testIo = new Io(is, os);
		
		/*
		try {
			testIo.getInteger("", "", 1, 5);		
		} catch(IOException e) {
		
		}
		*/
		
		
		String result = os.toString();
		assertEquals(0,0);
	}

	@Test
	public void testGetString() {
	}

	@Test
	public void testGetCards() {
	}

}
