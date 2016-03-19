package jk.lab.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.mysema.scalagen.ConversionSettings;
import com.mysema.scalagen.Converter;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;


public class ScalaConverter {
	private static final String DEFAULT_ENCODING = "UTF-8";
	public static void main(String args[]){
		try {
			Files.walk(Paths.get("yourproject/src/main/java/packageToConvert")).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			    	File file = filePath.toFile();
			    	String javaFilePath = filePath.toString();
			    	String scalaFilePath = javaFilePath.replace("/java/", "/scala/");
			    	scalaFilePath = scalaFilePath.replace(".java", ".scala");
			    	System.out.println(scalaFilePath);
			    	try {
						CompilationUnit cu =  JavaParser.parse(new FileInputStream(file), DEFAULT_ENCODING);
						File f = new File(scalaFilePath);
						f.getParentFile().mkdirs();
						FileWriter fw = new FileWriter(f);
						String result = Converter.instance210().toScala(cu, ConversionSettings.defaultSettings());
						fw.write(result);
						fw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			        System.out.println(filePath);
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
