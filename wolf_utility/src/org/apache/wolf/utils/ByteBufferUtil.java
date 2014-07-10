package org.apache.wolf.utils;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import static com.google.common.base.Charsets.UTF_8;

public class ByteBufferUtil {

	public static String String(ByteBuffer buffer) throws CharacterCodingException {
		return string(buffer,UTF_8);
	}

	private static java.lang.String string(ByteBuffer buffer, Charset charset) throws CharacterCodingException {
		return charset.newDecoder().decode(buffer).duplicate().toString();
	}

}
