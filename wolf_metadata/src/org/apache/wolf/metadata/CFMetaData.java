package org.apache.wolf.metadata;

import org.apache.wolf.db.type.AbstractType;
import org.apache.wolf.db.type.BytesType;
import org.apache.wolf.db.type.ColumnFamilyType;
import org.apache.wolf.db.type.UTF8Type;
import org.apache.wolf.utils.StaticField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CFMetaData {

	private static Logger logger=LoggerFactory.getLogger(CFMetaData.class);

	public static CFMetaData VersionCf=newSystemMetadata(StaticField.VERSION_CF,7,"server version information",UTF8Type.instance,null);
	
	private String comment;
	private double readRepairChance;
	private int gcGraceSecond;
	private double mergeShardsChance;

	private final String ksName;

	private final String cfName;

	private final ColumnFamilyType cfType;
	
	public final AbstractType comparator;

	private final AbstractType subcolumnComparator;

	private final Integer cfId;

	private final static double DEFAULT_READ_REPAIR_CHANCE=0.1;

	private final static boolean DEFAULT_REPLICATE_ON_WRITE=true;

	private final static int DEFAULT_GC_GRACE_SECONDS=864000;

	private final static double DEFAULT_MERGE_SHARDS_CHANCE=0.1;

	private boolean replicateOnWrite; 
	
	public CFMetaData(String keyspace, String cfname, ColumnFamilyType type,
			AbstractType comparators, AbstractType subcc, int cfld) {
		ksName=keyspace;
		cfName=cfname;
		cfType=type;
		comparator=comparators;
		subcolumnComparator=enforceSubccDefault(type,subcc);
		cfId=cfld;
		this.init();
	}

	private void init() {
		readRepairChance=DEFAULT_READ_REPAIR_CHANCE;
		replicateOnWrite=DEFAULT_REPLICATE_ON_WRITE;
		gcGraceSecond=DEFAULT_GC_GRACE_SECONDS;
		mergeShardsChance=DEFAULT_MERGE_SHARDS_CHANCE;
		
	}

	private AbstractType enforceSubccDefault(ColumnFamilyType type,
			AbstractType subcc) {
		return (subcc==null)&&(type==ColumnFamilyType.Super)?BytesType.instance:subcc;
	}

	public CFMetaData comment(String prop){
		comment=enforceCommentNotNull(prop);
		return this;
	}
	
	private CFMetaData readRepairChance(int i) {
		readRepairChance=i;
		return this;
	}
	
	private CFMetaData gcGraceSeconds(int i) {
		gcGraceSecond=i;
		return this;
	}
	
	private CFMetaData mergeShardsChance(double d) {
		mergeShardsChance=d;
		return this;
	}
	
	private String enforceCommentNotNull(String comment) {
		return (comment==null)?"":comment.toString();
	}

	private static CFMetaData newSystemMetadata(String cfname,int cfld,String comment,AbstractType comparator,AbstractType subcc){
		ColumnFamilyType type=subcc==null?ColumnFamilyType.Standard:ColumnFamilyType.Super;
		CFMetaData newCFMD=new CFMetaData("system",cfname,type,comparator,subcc,cfld);
		return newCFMD.comment(comment)
				.readRepairChance(0)
				.gcGraceSeconds(0)
				.mergeShardsChance(0.0);
	}

	public String getCfName() {
		return cfName;
	}

	public Integer getCfId() {
		return cfId;
	}

	public String getKsName() {
		return ksName;
	}

}
