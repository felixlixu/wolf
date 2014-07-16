package org.apache.wolf.io.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.ClosedChannelException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SequentialWriter extends OutputStream {

	private boolean skipIOCache;
	private String filePath;
	private byte[] buffer;
	protected long current=0;
	private MessageDigest digest;
	public final DataOutputStream stream;
	private final byte[] singleByteBuffer = new byte[1];
	private int validBufferBytes;
	private long bufferOffset;
	protected final RandomAccessFile out;

	public SequentialWriter(File file, int buffersize, boolean skipIOCache) throws FileNotFoundException {
		out = new RandomAccessFile(file, "rw");
		this.skipIOCache=skipIOCache;
		filePath=file.getAbsolutePath();
		buffer=new byte[buffersize];
		stream=new DataOutputStream(this);
		
	}

	@Override
	public void write(int value) throws IOException {
		singleByteBuffer[0]=(byte)value;
		write(singleByteBuffer,0,1);
	}
	
    public void write(byte[] data, int offset, int length) throws IOException
    {
        if (buffer == null)
            throw new ClosedChannelException();

        while (length > 0)
        {
            int n = writeAtMost(data, offset, length);
            offset += n;
            length -= n;
            //isDirty = true;
            //syncNeeded = true;
        }
    }
    
    private int writeAtMost(byte[] data, int offset, int length) throws IOException
    {
        //if (current >= bufferOffset + buffer.length)
            reBuffer();

        /*assert current < bufferOffset + buffer.length
                : String.format("File (%s) offset %d, buffer offset %d.", getPath(), current, bufferOffset);*/


        int toCopy = Math.min(length, buffer.length - bufferCursor());

        // copy bytes from external buffer
        System.arraycopy(data, offset, buffer, bufferCursor(), toCopy);

        /*assert current <= bufferOffset + buffer.length
                : String.format("File (%s) offset %d, buffer offset %d.", getPath(), current, bufferOffset);*/

        validBufferBytes = Math.max(validBufferBytes, bufferCursor() + toCopy);
        current += toCopy;

        return toCopy;
    }
    
    protected void reBuffer() throws IOException
    {
        flushInternal();
        resetBuffer();
    }
    
    private void flushInternal() throws IOException {
		flushData();
	}
    
    @Override
    public void flush() throws IOException
    {
        flushInternal();
    }

	private void flushData() throws IOException {
		 out.write(buffer, 0, validBufferBytes);
	}

	private int bufferCursor()
    {
        return (int) (current - bufferOffset);
    }

    
    protected void resetBuffer()
    {
        bufferOffset = current;
        validBufferBytes = 0;
    }

	public static SequentialWriter open(File file, boolean skipIOCache) throws FileNotFoundException {
		return open(file,RandomAccessReader.DEFAULT_BUFFER_SIZE,skipIOCache);
	}

	private static SequentialWriter open(File file, int buffersize,
			boolean skipIOCache) throws FileNotFoundException {
		return new SequentialWriter(file,buffersize,skipIOCache);
	}

	public void setComputeDigest() {
		if(current!=0){
			throw new IllegalStateException();
		}
		try{
			digest=MessageDigest.getInstance("SHA-1");
		}catch(NoSuchAlgorithmException e){
			throw new RuntimeException(e);
		}
	}

	public long getFilePointer() {
		return current;
	}
	
	 @Override
    public void close() throws IOException
    {
		 flushInternal();
		 flush();
		 out.close();
    }

}
