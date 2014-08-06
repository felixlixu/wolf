package org.apache.wolf.metadata;

import java.util.Collections;

import org.apache.wolf.db.type.AbstractType;
import org.apache.wolf.db.type.BytesType;
import org.apache.wolf.db.type.ColumnFamilyType;
import org.apache.wolf.db.type.TypeParser;
import org.apache.wolf.db.type.UTF8Type;
import org.apache.wolf.thrift.CfDef;
import org.apache.wolf.thrift.InvalidRequestException;
import org.apache.wolf.utils.StaticField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CFMetaData {

	private static Logger logger=LoggerFactory.getLogger(CFMetaData.class);

	public static CFMetaData VersionCf=newSystemMetadata(StaticField.VERSION_CF,7,"server version information",UTF8Type.instance,null);

	private static String DEFAULT_COMPACTION_STRATEGY_CLASS="SizeTieredCompactionStrategy";

	private final static int DEFAULT_MIN_COMPACTION_THRESHOLD=4;
	
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

	private static final int DEFAULT_MAX_COMPACTION_THRESHOLD = 32;

	private boolean replicateOnWrite;
	private int minCompactionThreshlod;

	private int maxCompactionThreshold;
	
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

	public int getMinCompactionThreshold() {
		return minCompactionThreshlod;
	}

	public int getMaxCompactionThreshold() {
		return maxCompactionThreshold;
	}

	public static CFMetaData fromThrift(CfDef cf_def) throws InvalidRequestException  {
		ColumnFamilyType cfType=ColumnFamilyType.create(cf_def.column_type);
		if(cfType==null){
			throw new InvalidRequestException("Invalid column type"+cf_def.column_type);
		}
		applyImplicitDefaults(cf_def);
		CFMetaData newCFMD=new CFMetaData(cf_def.keyspace,
				cf_def.name,
				cfType,
				TypeParser.parse(cf_def.comparator_type),
				cf_def.subcomparator_type==null?null:TypeParser.parse(cf_def.subcomparator_type),
				cf_def.isSetId()?cf_def.id:Schema.instances.nextCFId());
		return null;
	}

	private static void applyImplicitDefaults(CfDef cf_def) {
		if(!cf_def.isSetComment()){
			System.out.println("The set comment value is false");
			cf_def.setComment("");
		}
		if(!cf_def.isSetReplicate_on_write()){
			System.out.println("The replicate value is false");
			cf_def.setReplicate_on_write(DEFAULT_REPLICATE_ON_WRITE);
		}
		if(!cf_def.isSetMin_compaction_threshold()){
			System.out.println("The compaction is false");
			cf_def.setMin_compaction_threshold(DEFAULT_MIN_COMPACTION_THRESHOLD);
		}
		if(!cf_def.isSetMax_compaction_threshold()){
			System.out.println("The compaction max is false");
			cf_def.setMax_compaction_threshold(DEFAULT_MAX_COMPACTION_THRESHOLD);
		}
		if(!cf_def.isSetMerge_shards_chance()){
			System.out.println("The merge shards chance is false");
			cf_def.setMerge_shards_chance(DEFAULT_MERGE_SHARDS_CHANCE);
		}
		if(null==cf_def.compaction_strategy){
			System.out.println("The compaction strategy is false.");
			cf_def.compaction_strategy=DEFAULT_COMPACTION_STRATEGY_CLASS;
		}
		if(null==cf_def.compaction_strategy_options){
			System.out.println("The compression is false");
			cf_def.compaction_strategy_options=Collections.emptyMap();
		}
	}

}
