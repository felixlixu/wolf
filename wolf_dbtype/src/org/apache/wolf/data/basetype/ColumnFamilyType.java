package org.apache.wolf.data.basetype;

public enum ColumnFamilyType {

	Standard,
	Super;
	
    public static ColumnFamilyType create(String name)
    {
        try
        {
            // TODO thrift optional parameter in CfDef is leaking down here which it shouldn't
            return name == null ? ColumnFamilyType.Standard : ColumnFamilyType.valueOf(name);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }
}
