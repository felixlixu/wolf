package org.apache.wolf.sstable.data;

import java.io.File;
import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.wolf.conf.Config;
import org.apache.wolf.sstable.SSTable;
import org.apache.wolf.utils.Pair;

import static org.apache.wolf.sstable.data.Component.separator;

public class Descriptor {
	
	public static final String LEGACY_VERSION="a";
	
	public static final String CURRENT_VERSION="hc";
	
	public final File directory;
	public final String version;
	public final String ksname;
	public final String cfname;
	public final int generation;
	public final int hashCode;
	
	public final boolean temp;
	
	public Descriptor(File directory,String ksname,String cfname,int generation,boolean temp){
		this(CURRENT_VERSION,directory,ksname,cfname,generation,temp);
	}

	public Descriptor(String version, File directory, String ksname,
			String cfname, int generation, boolean temp) {
		this.version=version;
		this.directory=directory;
		this.ksname=ksname;
		this.cfname=cfname;
		this.generation=generation;
		this.temp=temp;
		hashCode=Objects.hash(directory,generation,ksname,cfname);
	}
	
    public String baseFilename() {
		StringBuilder buff=new StringBuilder();
		buff.append(directory).append(File.pathSeparatorChar);
		buff.append(cfname).append(separator);
		if(temp){
			buff.append(SSTable.TEMPFILE_MARKER).append(separator);
		}
		if(!LEGACY_VERSION.equals(version)){
			buff.append(version).append(separator);
		}
		buff.append(generation);
		return buff.toString();
	}
	
    @Override
    public String toString()
    {
        return baseFilename();
    }

	@Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof Descriptor))
            return false;
        Descriptor that = (Descriptor)o;
        return that.directory.equals(this.directory) && that.generation == this.generation && that.ksname.equals(this.ksname) && that.cfname.equals(this.cfname);
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

	public String filenameFor(Component component) {
		return filenameFor(component.name());
	}

	public String filenameFor(String suffix) {
		return baseFilename()+separator+suffix;
	}

	public static Descriptor fromFilename(String filename) {
		File file=new File(filename);
		assert file.getParentFile()!=null:"Filename must include parent directory.";
		return fromFilename(file.getParentFile(),file.getName()).left;
	}

	private static Pair<Descriptor,String> fromFilename(File directory, String name) {
		String ksname=extractKeyspaceName(directory);
		StringTokenizer st=new StringTokenizer(name,String.valueOf(separator));
		String nexttok;
		
		String cfname=st.nextToken();
		
		nexttok=st.nextToken();
		boolean temporary=false;
		if(nexttok.equals(SSTable.TEMPFILE_MARKER)){
			temporary=true;
			nexttok=st.nextToken();
		}
		String version=LEGACY_VERSION;
		if(versionValidate(nexttok)){
			version=nexttok;
			nexttok=st.nextToken();
		}
		int generation=Integer.parseInt(nexttok);
		String component=st.nextToken();
		return new Pair<Descriptor,String>(new Descriptor(version,directory,ksname,cfname,generation,temporary),component);
	}

	private static boolean versionValidate(String ver) {
		return ver!=null&&ver.matches("[a-z]+");
	}

	private static String extractKeyspaceName(File directory) {
		if(isSnapshotInPath(directory)){
			return directory.getParentFile().getParentFile().getName();
		}
		return directory.getName();
	}

	private static boolean isSnapshotInPath(File directory) {
		File curDirectory=directory;
		while(curDirectory!=null){
			if(curDirectory.getName().equals(Config.SNAPSHOT_SUBDIR_NAME)){
				return true;
			}
			curDirectory=curDirectory.getParentFile();
		}
		return false;
	}
}
