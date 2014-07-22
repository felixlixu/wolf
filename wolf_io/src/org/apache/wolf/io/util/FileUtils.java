package org.apache.wolf.io.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

	private static Logger logger_ = LoggerFactory.getLogger(FileUtils.class);
	
	public static void createDirectory(String directory) throws IOException {
		createDirectory(new File(directory));
	}

	public static void createDirectory(File file) throws IOException {
		if(!file.exists()){
			if(!file.mkdirs()){
				throw new IOException("unable to mkdirs "+file);
			}
		}
	}

	public static void deleteRecursive(File dir) throws IOException {
		if(dir.isDirectory()){
			String[] children=dir.list();
			for(String child:children){
				deleteRecursive(new File(dir,child));
			}
			deleteWithConfirm(dir);
		}
	}

	private static void deleteWithConfirm(File dir) throws IOException {
		assert dir.exists():"attempted to delete non-existing file" +dir.getName();
		if(logger_.isDebugEnabled()){
			logger_.debug("Deleting"+dir.getName());
		}
		if(!dir.delete()){
			throw new IOException("Failed to delete"+dir.getAbsolutePath());
		}
	}

}
