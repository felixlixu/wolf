package org.apache.wolf.sstable.data;

import com.google.common.base.Objects;


public class Component {
	
	public static final char separator = '-';

    private final Type type;
	private final int id;
	private final int hashCode;
	
	public Component(Type type, int id) {
    	this.type=type;
    	this.id=id;
    	this.hashCode=Objects.hashCode(type,id);
	}
	public final static Component DATA = new Component(Type.DATA, -1);
    public final static Component PRIMARY_INDEX = new Component(Type.PRIMARY_INDEX, -1);
    public final static Component FILTER = new Component(Type.FILTER, -1);
    public final static Component COMPACTED_MARKER = new Component(Type.COMPACTED_MARKER, -1);
    public final static Component COMPRESSION_INFO = new Component(Type.COMPRESSION_INFO, -1);
    public final static Component STATS = new Component(Type.STATS, -1);
    public final static Component DIGEST = new Component(Type.DIGEST, -1);

	public String name() {
		return type.repr;
	}
    

}
