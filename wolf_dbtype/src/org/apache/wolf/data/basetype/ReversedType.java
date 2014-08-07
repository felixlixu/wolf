package org.apache.wolf.data.basetype;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;


public class ReversedType<T> extends AbstractType<T> {

	@SuppressWarnings("rawtypes")
	private static final Map<AbstractType,ReversedType> instance=new HashMap<AbstractType,ReversedType>();
	@SuppressWarnings({ "rawtypes", "unused" })
	private final AbstractType baseType;
	
	@SuppressWarnings("rawtypes")
	public ReversedType(AbstractType baseType) {
		this.baseType=baseType;
	}

	@Override
	public int compare(ByteBuffer arg0, ByteBuffer arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized<T> AbstractType<T> getInstance(AbstractType baseType) {
		ReversedType type=instance.get(baseType);
		if(type==null){
			type=new ReversedType(baseType);
			instance.put(baseType, type);
		}
		return (ReversedType<T>)type;
	}

}
