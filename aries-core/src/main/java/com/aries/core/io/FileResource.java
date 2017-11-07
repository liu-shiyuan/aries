package com.aries.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResource implements Resource {

	private File file;

	public FileResource(File file) {
	    this.file = file;
	  }

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}
	
	public File getFile() {
		return file;
	}

	@Override
	public boolean exists() {
		return null == file ? false : file.exists();
	}

}
