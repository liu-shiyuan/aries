package com.aries.core.io;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class AriesEnv extends AbstractAriesProperties {

	@Override
	public List<Resource> getPropertyResources() {
		return x();
	}
	
	private List<Resource> x() {
		Path p;
		try {
			p = Paths.get(AriesEnv.class.getClassLoader().getResource("").toURI());
			List<File> ls = Files.list(p).map(path -> path.toFile()).filter(f -> f.getName().endsWith(".properties"))
					.collect(Collectors.toList());
			List<Resource> ret = Lists.newArrayListWithCapacity(ls.size());
			for (File f : ls) {
				ret.add(new FileResource(f));
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
//		Enumeration<URL> es = Thread.currentThread().getContextClassLoader().getResources("/xxx.properties");
		/*Enumeration<URL> es = AriesEnv.class.getClassLoader().getResources("/xxx.properties");
		while (es.hasMoreElements()) {
			URL url = es.nextElement();
			System.out.println(url.toString());
		}*/
		AriesEnv e = new AriesEnv();
		System.out.println(e.getProperty("x"));
	}

}
