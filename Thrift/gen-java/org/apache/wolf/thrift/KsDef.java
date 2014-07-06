/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.wolf.thrift;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KsDef implements org.apache.thrift.TBase<KsDef, KsDef._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("KsDef");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField STRATEGY_CLASS_FIELD_DESC = new org.apache.thrift.protocol.TField("strategy_class", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField STRATEGY_OPTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("strategy_options", org.apache.thrift.protocol.TType.MAP, (short)3);
  private static final org.apache.thrift.protocol.TField REPLICATION_FACTOR_FIELD_DESC = new org.apache.thrift.protocol.TField("replication_factor", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CF_DEFS_FIELD_DESC = new org.apache.thrift.protocol.TField("cf_defs", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField DURABLE_WRITES_FIELD_DESC = new org.apache.thrift.protocol.TField("durable_writes", org.apache.thrift.protocol.TType.BOOL, (short)6);

  public String name;
  public String strategy_class;
  public Map<String,String> strategy_options;
  /**
   * @deprecated
   */
  public int replication_factor;
  public List<CfDef> cf_defs;
  public boolean durable_writes;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    STRATEGY_CLASS((short)2, "strategy_class"),
    STRATEGY_OPTIONS((short)3, "strategy_options"),
    /**
     * @deprecated
     */
    REPLICATION_FACTOR((short)4, "replication_factor"),
    CF_DEFS((short)5, "cf_defs"),
    DURABLE_WRITES((short)6, "durable_writes");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NAME
          return NAME;
        case 2: // STRATEGY_CLASS
          return STRATEGY_CLASS;
        case 3: // STRATEGY_OPTIONS
          return STRATEGY_OPTIONS;
        case 4: // REPLICATION_FACTOR
          return REPLICATION_FACTOR;
        case 5: // CF_DEFS
          return CF_DEFS;
        case 6: // DURABLE_WRITES
          return DURABLE_WRITES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __REPLICATION_FACTOR_ISSET_ID = 0;
  private static final int __DURABLE_WRITES_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STRATEGY_CLASS, new org.apache.thrift.meta_data.FieldMetaData("strategy_class", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STRATEGY_OPTIONS, new org.apache.thrift.meta_data.FieldMetaData("strategy_options", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.REPLICATION_FACTOR, new org.apache.thrift.meta_data.FieldMetaData("replication_factor", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CF_DEFS, new org.apache.thrift.meta_data.FieldMetaData("cf_defs", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CfDef.class))));
    tmpMap.put(_Fields.DURABLE_WRITES, new org.apache.thrift.meta_data.FieldMetaData("durable_writes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(KsDef.class, metaDataMap);
  }

  public KsDef() {
    this.durable_writes = true;

  }

  public KsDef(
    String name,
    String strategy_class,
    List<CfDef> cf_defs)
  {
    this();
    this.name = name;
    this.strategy_class = strategy_class;
    this.cf_defs = cf_defs;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public KsDef(KsDef other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetStrategy_class()) {
      this.strategy_class = other.strategy_class;
    }
    if (other.isSetStrategy_options()) {
      Map<String,String> __this__strategy_options = new HashMap<String,String>();
      for (Map.Entry<String, String> other_element : other.strategy_options.entrySet()) {

        String other_element_key = other_element.getKey();
        String other_element_value = other_element.getValue();

        String __this__strategy_options_copy_key = other_element_key;

        String __this__strategy_options_copy_value = other_element_value;

        __this__strategy_options.put(__this__strategy_options_copy_key, __this__strategy_options_copy_value);
      }
      this.strategy_options = __this__strategy_options;
    }
    this.replication_factor = other.replication_factor;
    if (other.isSetCf_defs()) {
      List<CfDef> __this__cf_defs = new ArrayList<CfDef>();
      for (CfDef other_element : other.cf_defs) {
        __this__cf_defs.add(new CfDef(other_element));
      }
      this.cf_defs = __this__cf_defs;
    }
    this.durable_writes = other.durable_writes;
  }

  public KsDef deepCopy() {
    return new KsDef(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.strategy_class = null;
    this.strategy_options = null;
    setReplication_factorIsSet(false);
    this.replication_factor = 0;
    this.cf_defs = null;
    this.durable_writes = true;

  }

  public String getName() {
    return this.name;
  }

  public KsDef setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getStrategy_class() {
    return this.strategy_class;
  }

  public KsDef setStrategy_class(String strategy_class) {
    this.strategy_class = strategy_class;
    return this;
  }

  public void unsetStrategy_class() {
    this.strategy_class = null;
  }

  /** Returns true if field strategy_class is set (has been assigned a value) and false otherwise */
  public boolean isSetStrategy_class() {
    return this.strategy_class != null;
  }

  public void setStrategy_classIsSet(boolean value) {
    if (!value) {
      this.strategy_class = null;
    }
  }

  public int getStrategy_optionsSize() {
    return (this.strategy_options == null) ? 0 : this.strategy_options.size();
  }

  public void putToStrategy_options(String key, String val) {
    if (this.strategy_options == null) {
      this.strategy_options = new HashMap<String,String>();
    }
    this.strategy_options.put(key, val);
  }

  public Map<String,String> getStrategy_options() {
    return this.strategy_options;
  }

  public KsDef setStrategy_options(Map<String,String> strategy_options) {
    this.strategy_options = strategy_options;
    return this;
  }

  public void unsetStrategy_options() {
    this.strategy_options = null;
  }

  /** Returns true if field strategy_options is set (has been assigned a value) and false otherwise */
  public boolean isSetStrategy_options() {
    return this.strategy_options != null;
  }

  public void setStrategy_optionsIsSet(boolean value) {
    if (!value) {
      this.strategy_options = null;
    }
  }

  /**
   * @deprecated
   */
  public int getReplication_factor() {
    return this.replication_factor;
  }

  /**
   * @deprecated
   */
  public KsDef setReplication_factor(int replication_factor) {
    this.replication_factor = replication_factor;
    setReplication_factorIsSet(true);
    return this;
  }

  public void unsetReplication_factor() {
    __isset_bit_vector.clear(__REPLICATION_FACTOR_ISSET_ID);
  }

  /** Returns true if field replication_factor is set (has been assigned a value) and false otherwise */
  public boolean isSetReplication_factor() {
    return __isset_bit_vector.get(__REPLICATION_FACTOR_ISSET_ID);
  }

  public void setReplication_factorIsSet(boolean value) {
    __isset_bit_vector.set(__REPLICATION_FACTOR_ISSET_ID, value);
  }

  public int getCf_defsSize() {
    return (this.cf_defs == null) ? 0 : this.cf_defs.size();
  }

  public java.util.Iterator<CfDef> getCf_defsIterator() {
    return (this.cf_defs == null) ? null : this.cf_defs.iterator();
  }

  public void addToCf_defs(CfDef elem) {
    if (this.cf_defs == null) {
      this.cf_defs = new ArrayList<CfDef>();
    }
    this.cf_defs.add(elem);
  }

  public List<CfDef> getCf_defs() {
    return this.cf_defs;
  }

  public KsDef setCf_defs(List<CfDef> cf_defs) {
    this.cf_defs = cf_defs;
    return this;
  }

  public void unsetCf_defs() {
    this.cf_defs = null;
  }

  /** Returns true if field cf_defs is set (has been assigned a value) and false otherwise */
  public boolean isSetCf_defs() {
    return this.cf_defs != null;
  }

  public void setCf_defsIsSet(boolean value) {
    if (!value) {
      this.cf_defs = null;
    }
  }

  public boolean isDurable_writes() {
    return this.durable_writes;
  }

  public KsDef setDurable_writes(boolean durable_writes) {
    this.durable_writes = durable_writes;
    setDurable_writesIsSet(true);
    return this;
  }

  public void unsetDurable_writes() {
    __isset_bit_vector.clear(__DURABLE_WRITES_ISSET_ID);
  }

  /** Returns true if field durable_writes is set (has been assigned a value) and false otherwise */
  public boolean isSetDurable_writes() {
    return __isset_bit_vector.get(__DURABLE_WRITES_ISSET_ID);
  }

  public void setDurable_writesIsSet(boolean value) {
    __isset_bit_vector.set(__DURABLE_WRITES_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case STRATEGY_CLASS:
      if (value == null) {
        unsetStrategy_class();
      } else {
        setStrategy_class((String)value);
      }
      break;

    case STRATEGY_OPTIONS:
      if (value == null) {
        unsetStrategy_options();
      } else {
        setStrategy_options((Map<String,String>)value);
      }
      break;

    case REPLICATION_FACTOR:
      if (value == null) {
        unsetReplication_factor();
      } else {
        setReplication_factor((Integer)value);
      }
      break;

    case CF_DEFS:
      if (value == null) {
        unsetCf_defs();
      } else {
        setCf_defs((List<CfDef>)value);
      }
      break;

    case DURABLE_WRITES:
      if (value == null) {
        unsetDurable_writes();
      } else {
        setDurable_writes((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case STRATEGY_CLASS:
      return getStrategy_class();

    case STRATEGY_OPTIONS:
      return getStrategy_options();

    case REPLICATION_FACTOR:
      return new Integer(getReplication_factor());

    case CF_DEFS:
      return getCf_defs();

    case DURABLE_WRITES:
      return new Boolean(isDurable_writes());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case STRATEGY_CLASS:
      return isSetStrategy_class();
    case STRATEGY_OPTIONS:
      return isSetStrategy_options();
    case REPLICATION_FACTOR:
      return isSetReplication_factor();
    case CF_DEFS:
      return isSetCf_defs();
    case DURABLE_WRITES:
      return isSetDurable_writes();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof KsDef)
      return this.equals((KsDef)that);
    return false;
  }

  public boolean equals(KsDef that) {
    if (that == null)
      return false;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_strategy_class = true && this.isSetStrategy_class();
    boolean that_present_strategy_class = true && that.isSetStrategy_class();
    if (this_present_strategy_class || that_present_strategy_class) {
      if (!(this_present_strategy_class && that_present_strategy_class))
        return false;
      if (!this.strategy_class.equals(that.strategy_class))
        return false;
    }

    boolean this_present_strategy_options = true && this.isSetStrategy_options();
    boolean that_present_strategy_options = true && that.isSetStrategy_options();
    if (this_present_strategy_options || that_present_strategy_options) {
      if (!(this_present_strategy_options && that_present_strategy_options))
        return false;
      if (!this.strategy_options.equals(that.strategy_options))
        return false;
    }

    boolean this_present_replication_factor = true && this.isSetReplication_factor();
    boolean that_present_replication_factor = true && that.isSetReplication_factor();
    if (this_present_replication_factor || that_present_replication_factor) {
      if (!(this_present_replication_factor && that_present_replication_factor))
        return false;
      if (this.replication_factor != that.replication_factor)
        return false;
    }

    boolean this_present_cf_defs = true && this.isSetCf_defs();
    boolean that_present_cf_defs = true && that.isSetCf_defs();
    if (this_present_cf_defs || that_present_cf_defs) {
      if (!(this_present_cf_defs && that_present_cf_defs))
        return false;
      if (!this.cf_defs.equals(that.cf_defs))
        return false;
    }

    boolean this_present_durable_writes = true && this.isSetDurable_writes();
    boolean that_present_durable_writes = true && that.isSetDurable_writes();
    if (this_present_durable_writes || that_present_durable_writes) {
      if (!(this_present_durable_writes && that_present_durable_writes))
        return false;
      if (this.durable_writes != that.durable_writes)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(KsDef other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    KsDef typedOther = (KsDef)other;

    lastComparison = Boolean.valueOf(isSetName()).compareTo(typedOther.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStrategy_class()).compareTo(typedOther.isSetStrategy_class());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStrategy_class()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.strategy_class, typedOther.strategy_class);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStrategy_options()).compareTo(typedOther.isSetStrategy_options());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStrategy_options()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.strategy_options, typedOther.strategy_options);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReplication_factor()).compareTo(typedOther.isSetReplication_factor());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReplication_factor()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.replication_factor, typedOther.replication_factor);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCf_defs()).compareTo(typedOther.isSetCf_defs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCf_defs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cf_defs, typedOther.cf_defs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDurable_writes()).compareTo(typedOther.isSetDurable_writes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDurable_writes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.durable_writes, typedOther.durable_writes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    org.apache.thrift.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // NAME
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.name = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // STRATEGY_CLASS
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.strategy_class = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // STRATEGY_OPTIONS
          if (field.type == org.apache.thrift.protocol.TType.MAP) {
            {
              org.apache.thrift.protocol.TMap _map56 = iprot.readMapBegin();
              this.strategy_options = new HashMap<String,String>(2*_map56.size);
              for (int _i57 = 0; _i57 < _map56.size; ++_i57)
              {
                String _key58;
                String _val59;
                _key58 = iprot.readString();
                _val59 = iprot.readString();
                this.strategy_options.put(_key58, _val59);
              }
              iprot.readMapEnd();
            }
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // REPLICATION_FACTOR
          if (field.type == org.apache.thrift.protocol.TType.I32) {
            this.replication_factor = iprot.readI32();
            setReplication_factorIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // CF_DEFS
          if (field.type == org.apache.thrift.protocol.TType.LIST) {
            {
              org.apache.thrift.protocol.TList _list60 = iprot.readListBegin();
              this.cf_defs = new ArrayList<CfDef>(_list60.size);
              for (int _i61 = 0; _i61 < _list60.size; ++_i61)
              {
                CfDef _elem62;
                _elem62 = new CfDef();
                _elem62.read(iprot);
                this.cf_defs.add(_elem62);
              }
              iprot.readListEnd();
            }
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // DURABLE_WRITES
          if (field.type == org.apache.thrift.protocol.TType.BOOL) {
            this.durable_writes = iprot.readBool();
            setDurable_writesIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.name != null) {
      oprot.writeFieldBegin(NAME_FIELD_DESC);
      oprot.writeString(this.name);
      oprot.writeFieldEnd();
    }
    if (this.strategy_class != null) {
      oprot.writeFieldBegin(STRATEGY_CLASS_FIELD_DESC);
      oprot.writeString(this.strategy_class);
      oprot.writeFieldEnd();
    }
    if (this.strategy_options != null) {
      if (isSetStrategy_options()) {
        oprot.writeFieldBegin(STRATEGY_OPTIONS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, this.strategy_options.size()));
          for (Map.Entry<String, String> _iter63 : this.strategy_options.entrySet())
          {
            oprot.writeString(_iter63.getKey());
            oprot.writeString(_iter63.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
    }
    if (isSetReplication_factor()) {
      oprot.writeFieldBegin(REPLICATION_FACTOR_FIELD_DESC);
      oprot.writeI32(this.replication_factor);
      oprot.writeFieldEnd();
    }
    if (this.cf_defs != null) {
      oprot.writeFieldBegin(CF_DEFS_FIELD_DESC);
      {
        oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, this.cf_defs.size()));
        for (CfDef _iter64 : this.cf_defs)
        {
          _iter64.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (isSetDurable_writes()) {
      oprot.writeFieldBegin(DURABLE_WRITES_FIELD_DESC);
      oprot.writeBool(this.durable_writes);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("KsDef(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("strategy_class:");
    if (this.strategy_class == null) {
      sb.append("null");
    } else {
      sb.append(this.strategy_class);
    }
    first = false;
    if (isSetStrategy_options()) {
      if (!first) sb.append(", ");
      sb.append("strategy_options:");
      if (this.strategy_options == null) {
        sb.append("null");
      } else {
        sb.append(this.strategy_options);
      }
      first = false;
    }
    if (isSetReplication_factor()) {
      if (!first) sb.append(", ");
      sb.append("replication_factor:");
      sb.append(this.replication_factor);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("cf_defs:");
    if (this.cf_defs == null) {
      sb.append("null");
    } else {
      sb.append(this.cf_defs);
    }
    first = false;
    if (isSetDurable_writes()) {
      if (!first) sb.append(", ");
      sb.append("durable_writes:");
      sb.append(this.durable_writes);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'name' was not present! Struct: " + toString());
    }
    if (strategy_class == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'strategy_class' was not present! Struct: " + toString());
    }
    if (cf_defs == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'cf_defs' was not present! Struct: " + toString());
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

