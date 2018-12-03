package br.com.calcard.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RepositoryRestConfigurer extends RepositoryRestConfigurerAdapter {

	private void addClassesForPackageName(ClassLoader classLoader, List<Class<?>> classes, String packageName) {
		try {
			List<File> dirs = new ArrayList<>();
			Enumeration<URL> resources = getResources(packageName, classLoader);
			Collections.list(resources).forEach(resource -> dirs.add(new File(resource.getFile())));
			dirs.forEach(directory -> classes.addAll(findClasses(directory, packageName)));
		} catch (IOException e) {
			log.error("ERROR  while findClasses for :: " + packageName, e);
		}
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(getClasses());
		config.setBasePath("/api/v1");
	}

	private List<Class<?>> findClasses(File directory, String packageName) {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				try {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				} catch (ClassNotFoundException e) {
					log.error("ERROR  while findClasses", e);
				}
			}
		}
		return classes;
	}

	private Class<?>[] getClasses() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			return new Class[0];
		}

		List<Class<?>> classes = new ArrayList<>();
		Arrays.asList(new String[] {
				"br.com.oms.core.model",
				"br.com.oms.generator.model",
				"br.com.oms.generated.model" })
				.forEach(packageName -> {
					addClassesForPackageName(classLoader, classes, packageName);
				});

		return classes.toArray(new Class[classes.size()]);
	}

	private Enumeration<URL> getResources(String packageName, ClassLoader classLoader) throws IOException {
		return classLoader.getResources(packageName.replace('.', '/'));
	}
}
