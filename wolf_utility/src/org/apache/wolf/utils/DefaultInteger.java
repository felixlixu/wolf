package org.apache.wolf.utils;

public class DefaultInteger {
	
	private final int originalValue;
	private int currentValue;
	
	public DefaultInteger(int value){
		this.originalValue=value;
		this.currentValue=value;
	}
	
	public int value(){
		return currentValue;
	}
	
	public void set(int i){
		currentValue=i;
	}
	
	public boolean isModified(){
		return originalValue!=currentValue;
	}
}
