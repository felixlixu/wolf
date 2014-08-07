package org.apache.wolf.data.basetype;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Map;

import javax.naming.ConfigurationException;



public abstract class AbstractType<T> implements Comparator<ByteBuffer> {

	@SuppressWarnings("rawtypes")
	public static AbstractType parseDefaultParameters(AbstractType baseType,
			TypeParser parser) throws ConfigurationException {
		Map<String,String> parameters=parser.getKeyValueParameters();
		String reversed=parameters.get("reversed");
		if(reversed!=null&&(reversed.isEmpty()||reversed.equals("true"))){
			return ReversedType.getInstance(baseType);
		}else{
			return baseType;
		}
	}

	public boolean isCommutative() {
		return false;
	}

}
