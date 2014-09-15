package org.apache.wolf.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

//input Format startToken,EndToken,Length context;
//output Format stratToken,endToken,length,context
//Splite is used to split file.
public class ColumnFamilySplit extends InputSplit implements Writable,org.apache.hadoop.mapred.InputSplit {

	private String startToken;
	private String endToken;
	private String[] dataNodes;
	
	public ColumnFamilySplit(String strToken,String endToken,String[] datanodes){
		assert startToken!=null;
		assert endToken!=null;
		this.startToken=strToken;
		this.endToken=endToken;
		this.dataNodes=datanodes;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		startToken=in.readUTF();
		endToken=in.readUTF();
		
		int numOfEndpoints=in.readInt();
		dataNodes=new String[numOfEndpoints];
		for(int i=0;i<numOfEndpoints;i++){
			dataNodes[i]=in.readUTF();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(startToken);
		out.writeUTF(endToken);
		out.writeInt(dataNodes.length);
		for(String endpoints:dataNodes){
			out.writeUTF(endpoints);
		}
	}

	@Override
	public long getLength(){
		return Long.MAX_VALUE;
	}

	@Override
	public String[] getLocations(){
		return this.dataNodes;
	}

	@Override
	public String toString(){
	    return "ColumnFamilySplit{" +
	            "startToken='" + startToken + '\'' +
	            ", endToken='" + endToken + '\'' +
	            ", dataNodes=" + (dataNodes == null ? null : Arrays.asList(dataNodes)) +
	            '}';
	}
	
	protected ColumnFamilySplit(){}
	
	public static ColumnFamilySplit read(DataInput in) throws IOException{
		ColumnFamilySplit w=new ColumnFamilySplit();
		w.readFields(in);
		return w;
	}

}
