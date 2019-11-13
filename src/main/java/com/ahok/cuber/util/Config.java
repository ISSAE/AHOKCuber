package com.ahok.cuber.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private Properties prop;

    public Config (String configName) {
        InputStream input = null;
        this.prop = new Properties();
        try {
            input = Config.class.getClassLoader().getResourceAsStream(configName + ".properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String configName, String propertyName) {
        Config config = new Config(configName);

        return config.prop.getProperty(propertyName);
    }

    public static String getProperty(String propertyName) {
        Config config = new Config("config");

        return config.prop.getProperty(propertyName);
    }
}
