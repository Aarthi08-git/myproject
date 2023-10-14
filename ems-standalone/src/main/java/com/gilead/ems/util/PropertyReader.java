/**
 * 
 */
package com.gilead.ems.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 */
public class PropertyReader {
	
	public static Properties loadProperties(String filePath) {
		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appProps;
	}

}
