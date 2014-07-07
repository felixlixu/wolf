package org.apache.wolf.conf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SeedProviderDef {

	public String class_name;
	public Map<String,String> parameters;
	public List<String> list=new ArrayList<String>();;
	
	public SeedProviderDef(String ...ips){
		for(String ip:ips){
			list.add(ip);
		}
	}
	
	public SeedProviderDef(LinkedHashMap p){
		class_name=(String)p.get("class_name");
		parameters=(Map<String,String>)((List)p.get("parameters")).get(0);
	}
}
