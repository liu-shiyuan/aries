package com.aries.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResource implements Resource {
	
	private URL url;
	
	public URLResource(URL url) {
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return url.openStream();
	}

	@Override
	public boolean exists() {
		return true;
	}
}
