/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.wolf.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
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

/**
 * The semantics of start keys and tokens are slightly different.
 * Keys are start-inclusive; tokens are start-exclusive.  Token
 * ranges may also wrap -- that is, the end token may be less
 * than the start one.  Thus, a range from keyX to keyX is a
 * one-element range, but a range from tokenY to tokenY is the
 * full ring.
 */
public class KeyRange implements org.apache.thrift.TBase<KeyRange, KeyRange._Fields>, java.io.Serializable, Cloneable, Comparable<KeyRange> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("KeyRange");

  private static final org.apache.thrift.protocol.TField START_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("start_key", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField END_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("end_key", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField START_TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("start_token", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField END_TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("end_token", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new KeyRangeStandardSchemeFactory());
    schemes.put(TupleScheme.class, new KeyRangeTupleSchemeFactory());
  }

  public ByteBuffer start_key; // optional
  public ByteBuffer end_key; // optional
  public String start_token; // optional
  public String end_token; // optional
  public int count; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    START_KEY((short)1, "start_key"),
    END_KEY((short)2, "end_key"),
    START_TOKEN((short)3, "start_token"),
    END_TOKEN((short)4, "end_token"),
    COUNT((short)5, "count");

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
        case 1: // START_KEY
          return START_KEY;
        case 2: // END_KEY
          return END_KEY;
        case 3: // START_TOKEN
          return START_TOKEN;
        case 4: // END_TOKEN
          return END_TOKEN;
        case 5: // COUNT
          return COUNT;
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
  private static final int __COUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.START_KEY,_Fields.END_KEY,_Fields.START_TOKEN,_Fields.END_TOKEN};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.START_KEY, new org.apache.thrift.meta_data.FieldMetaData("start_key", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.END_KEY, new org.apache.thrift.meta_data.FieldMetaData("end_key", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.START_TOKEN, new org.apache.thrift.meta_data.FieldMetaData("start_token", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.END_TOKEN, new org.apache.thrift.meta_data.FieldMetaData("end_token", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(KeyRange.class, metaDataMap);
  }

  public KeyRange() {
    this.count = 100;

  }

  public KeyRange(
    int count)
  {
    this();
    this.count = count;
    setCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public KeyRange(KeyRange other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetStart_key()) {
      this.start_key = org.apache.thrift.TBaseHelper.copyBinary(other.start_key);
;
    }
    if (other.isSetEnd_key()) {
      this.end_key = org.apache.thrift.TBaseHelper.copyBinary(other.end_key);
;
    }
    if (other.isSetStart_token()) {
      this.start_token = other.start_token;
    }
    if (other.isSetEnd_token()) {
      this.end_token = other.end_token;
    }
    this.count = other.count;
  }

  public KeyRange deepCopy() {
    return new KeyRange(this);
  }

  @Override
  public void clear() {
    this.start_key = null;
    this.end_key = null;
    this.start_token = null;
    this.end_token = null;
    this.count = 100;

  }

  public byte[] getStart_key() {
    setStart_key(org.apache.thrift.TBaseHelper.rightSize(start_key));
    return start_key == null ? null : start_key.array();
  }

  public ByteBuffer bufferForStart_key() {
    return start_key;
  }

  public KeyRange setStart_key(byte[] start_key) {
    setStart_key(start_key == null ? (ByteBuffer)null : ByteBuffer.wrap(start_key));
    return this;
  }

  public KeyRange setStart_key(ByteBuffer start_key) {
    this.start_key = start_key;
    return this;
  }

  public void unsetStart_key() {
    this.start_key = null;
  }

  /** Returns true if field start_key is set (has been assigned a value) and false otherwise */
  public boolean isSetStart_key() {
    return this.start_key != null;
  }

  public void setStart_keyIsSet(boolean value) {
    if (!value) {
      this.start_key = null;
    }
  }

  public byte[] getEnd_key() {
    setEnd_key(org.apache.thrift.TBaseHelper.rightSize(end_key));
    return end_key == null ? null : end_key.array();
  }

  public ByteBuffer bufferForEnd_key() {
    return end_key;
  }

  public KeyRange setEnd_key(byte[] end_key) {
    setEnd_key(end_key == null ? (ByteBuffer)null : ByteBuffer.wrap(end_key));
    return this;
  }

  public KeyRange setEnd_key(ByteBuffer end_key) {
    this.end_key = end_key;
    return this;
  }

  public void unsetEnd_key() {
    this.end_key = null;
  }

  /** Returns true if field end_key is set (has been assigned a value) and false otherwise */
  public boolean isSetEnd_key() {
    return this.end_key != null;
  }

  public void setEnd_keyIsSet(boolean value) {
    if (!value) {
      this.end_key = null;
    }
  }

  public String getStart_token() {
    return this.start_token;
  }

  public KeyRange setStart_token(String start_token) {
    this.start_token = start_token;
    return this;
  }

  public void unsetStart_token() {
    this.start_token = null;
  }

  /** Returns true if field start_token is set (has been assigned a value) and false otherwise */
  public boolean isSetStart_token() {
    return this.start_token != null;
  }

  public void setStart_tokenIsSet(boolean value) {
    if (!value) {
      this.start_token = null;
    }
  }

  public String getEnd_token() {
    return this.end_token;
  }

  public KeyRange setEnd_token(String end_token) {
    this.end_token = end_token;
    return this;
  }

  public void unsetEnd_token() {
    this.end_token = null;
  }

  /** Returns true if field end_token is set (has been assigned a value) and false otherwise */
  public boolean isSetEnd_token() {
    return this.end_token != null;
  }

  public void setEnd_tokenIsSet(boolean value) {
    if (!value) {
      this.end_token = null;
    }
  }

  public int getCount() {
    return this.count;
  }

  public KeyRange setCount(int count) {
    this.count = count;
    setCountIsSet(true);
    return this;
  }

  public void unsetCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  /** Returns true if field count is set (has been assigned a value) and false otherwise */
  public boolean isSetCount() {
    return EncodingUtils.testBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  public void setCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case START_KEY:
      if (value == null) {
        unsetStart_key();
      } else {
        setStart_key((ByteBuffer)value);
      }
      break;

    case END_KEY:
      if (value == null) {
        unsetEnd_key();
      } else {
        setEnd_key((ByteBuffer)value);
      }
      break;

    case START_TOKEN:
      if (value == null) {
        unsetStart_token();
      } else {
        setStart_token((String)value);
      }
      break;

    case END_TOKEN:
      if (value == null) {
        unsetEnd_token();
      } else {
        setEnd_token((String)value);
      }
      break;

    case COUNT:
      if (value == null) {
        unsetCount();
      } else {
        setCount((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case START_KEY:
      return getStart_key();

    case END_KEY:
      return getEnd_key();

    case START_TOKEN:
      return getStart_token();

    case END_TOKEN:
      return getEnd_token();

    case COUNT:
      return Integer.valueOf(getCount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case START_KEY:
      return isSetStart_key();
    case END_KEY:
      return isSetEnd_key();
    case START_TOKEN:
      return isSetStart_token();
    case END_TOKEN:
      return isSetEnd_token();
    case COUNT:
      return isSetCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof KeyRange)
      return this.equals((KeyRange)that);
    return false;
  }

  public boolean equals(KeyRange that) {
    if (that == null)
      return false;

    boolean this_present_start_key = true && this.isSetStart_key();
    boolean that_present_start_key = true && that.isSetStart_key();
    if (this_present_start_key || that_present_start_key) {
      if (!(this_present_start_key && that_present_start_key))
        return false;
      if (!this.start_key.equals(that.start_key))
        return false;
    }

    boolean this_present_end_key = true && this.isSetEnd_key();
    boolean that_present_end_key = true && that.isSetEnd_key();
    if (this_present_end_key || that_present_end_key) {
      if (!(this_present_end_key && that_present_end_key))
        return false;
      if (!this.end_key.equals(that.end_key))
        return false;
    }

    boolean this_present_start_token = true && this.isSetStart_token();
    boolean that_present_start_token = true && that.isSetStart_token();
    if (this_present_start_token || that_present_start_token) {
      if (!(this_present_start_token && that_present_start_token))
        return false;
      if (!this.start_token.equals(that.start_token))
        return false;
    }

    boolean this_present_end_token = true && this.isSetEnd_token();
    boolean that_present_end_token = true && that.isSetEnd_token();
    if (this_present_end_token || that_present_end_token) {
      if (!(this_present_end_token && that_present_end_token))
        return false;
      if (!this.end_token.equals(that.end_token))
        return false;
    }

    boolean this_present_count = true;
    boolean that_present_count = true;
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(KeyRange other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetStart_key()).compareTo(other.isSetStart_key());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStart_key()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.start_key, other.start_key);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEnd_key()).compareTo(other.isSetEnd_key());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnd_key()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.end_key, other.end_key);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStart_token()).compareTo(other.isSetStart_token());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStart_token()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.start_token, other.start_token);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEnd_token()).compareTo(other.isSetEnd_token());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnd_token()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.end_token, other.end_token);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCount()).compareTo(other.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, other.count);
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
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("KeyRange(");
    boolean first = true;

    if (isSetStart_key()) {
      sb.append("start_key:");
      if (this.start_key == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.start_key, sb);
      }
      first = false;
    }
    if (isSetEnd_key()) {
      if (!first) sb.append(", ");
      sb.append("end_key:");
      if (this.end_key == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.end_key, sb);
      }
      first = false;
    }
    if (isSetStart_token()) {
      if (!first) sb.append(", ");
      sb.append("start_token:");
      if (this.start_token == null) {
        sb.append("null");
      } else {
        sb.append(this.start_token);
      }
      first = false;
    }
    if (isSetEnd_token()) {
      if (!first) sb.append(", ");
      sb.append("end_token:");
      if (this.end_token == null) {
        sb.append("null");
      } else {
        sb.append(this.end_token);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("count:");
    sb.append(this.count);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'count' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
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
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class KeyRangeStandardSchemeFactory implements SchemeFactory {
    public KeyRangeStandardScheme getScheme() {
      return new KeyRangeStandardScheme();
    }
  }

  private static class KeyRangeStandardScheme extends StandardScheme<KeyRange> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, KeyRange struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // START_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.start_key = iprot.readBinary();
              struct.setStart_keyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // END_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.end_key = iprot.readBinary();
              struct.setEnd_keyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // START_TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.start_token = iprot.readString();
              struct.setStart_tokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.end_token = iprot.readString();
              struct.setEnd_tokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.count = iprot.readI32();
              struct.setCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetCount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'count' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, KeyRange struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.start_key != null) {
        if (struct.isSetStart_key()) {
          oprot.writeFieldBegin(START_KEY_FIELD_DESC);
          oprot.writeBinary(struct.start_key);
          oprot.writeFieldEnd();
        }
      }
      if (struct.end_key != null) {
        if (struct.isSetEnd_key()) {
          oprot.writeFieldBegin(END_KEY_FIELD_DESC);
          oprot.writeBinary(struct.end_key);
          oprot.writeFieldEnd();
        }
      }
      if (struct.start_token != null) {
        if (struct.isSetStart_token()) {
          oprot.writeFieldBegin(START_TOKEN_FIELD_DESC);
          oprot.writeString(struct.start_token);
          oprot.writeFieldEnd();
        }
      }
      if (struct.end_token != null) {
        if (struct.isSetEnd_token()) {
          oprot.writeFieldBegin(END_TOKEN_FIELD_DESC);
          oprot.writeString(struct.end_token);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(COUNT_FIELD_DESC);
      oprot.writeI32(struct.count);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class KeyRangeTupleSchemeFactory implements SchemeFactory {
    public KeyRangeTupleScheme getScheme() {
      return new KeyRangeTupleScheme();
    }
  }

  private static class KeyRangeTupleScheme extends TupleScheme<KeyRange> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, KeyRange struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.count);
      BitSet optionals = new BitSet();
      if (struct.isSetStart_key()) {
        optionals.set(0);
      }
      if (struct.isSetEnd_key()) {
        optionals.set(1);
      }
      if (struct.isSetStart_token()) {
        optionals.set(2);
      }
      if (struct.isSetEnd_token()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetStart_key()) {
        oprot.writeBinary(struct.start_key);
      }
      if (struct.isSetEnd_key()) {
        oprot.writeBinary(struct.end_key);
      }
      if (struct.isSetStart_token()) {
        oprot.writeString(struct.start_token);
      }
      if (struct.isSetEnd_token()) {
        oprot.writeString(struct.end_token);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, KeyRange struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.count = iprot.readI32();
      struct.setCountIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.start_key = iprot.readBinary();
        struct.setStart_keyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.end_key = iprot.readBinary();
        struct.setEnd_keyIsSet(true);
      }
      if (incoming.get(2)) {
        struct.start_token = iprot.readString();
        struct.setStart_tokenIsSet(true);
      }
      if (incoming.get(3)) {
        struct.end_token = iprot.readString();
        struct.setEnd_tokenIsSet(true);
      }
    }
  }

}

