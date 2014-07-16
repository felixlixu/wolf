package org.apache.wolf.utils;

import java.io.DataOutputStream;
import java.io.IOException;
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

	public static ByteBuffer bytes(String s) {
		return ByteBuffer.wrap(s.getBytes(UTF_8));
	}

	public static void writeWithShortLength(ByteBuffer buffer,
			DataOutputStream stream) {
		int length=buffer.remaining();
		try{
			stream.writeByte((length>>8)&0xFF);
			stream.writeByte(length&0XFF);
			write(buffer,stream);
		}catch(IOException e){
			throw new RuntimeException();
		}
	}

	public static void write(ByteBuffer buffer, DataOutputStream out) throws IOException {
        if (buffer.hasArray())
        {
            out.write(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining());
        }
        else
        {
            for (int i = buffer.position(); i < buffer.limit(); i++)
            {
                out.writeByte(buffer.get(i));
            }
        }
	}

}
