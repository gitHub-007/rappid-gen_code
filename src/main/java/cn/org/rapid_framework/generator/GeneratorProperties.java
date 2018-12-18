package cn.org.rapid_framework.generator;

import cn.org.rapid_framework.generator.util.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 生成器配置类
 * 用于装载generator.properties,generator.xml文件
 *
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class GeneratorProperties {
    private static Logger logger = LoggerFactory.getLogger(GeneratorProperties.class);
    static final String PROPERTIES_FILE_NAMES[] = new String[]{"generator.properties", "generator.xml",
            "default-generator.xml"};

    PropertiesHelper propertiesHelper = new PropertiesHelper(new Properties(), true);

    public GeneratorProperties() {
    }


    public  void load(String... files) throws InvalidPropertiesFormatException, IOException {
        putAll(PropertiesHelper.load(files));
    }


    public  void putAll(Properties props) {
        getHelper().putAll(props);
    }

    public  void clear() {
        getHelper().clear();
    }

    public  void reload() {
        try {
            logger.info("Start Load GeneratorPropeties from classpath:{}", Arrays.toString(PROPERTIES_FILE_NAMES));
            Properties p = new Properties();
            String[] loadedFiles = PropertiesHelper.loadAllPropertiesFromClassLoader(p, PROPERTIES_FILE_NAMES);
            logger.info("GeneratorPropeties Load Success,files:{}", Arrays.toString(loadedFiles));
            setSepicalProperties(p, loadedFiles);
            setProperties(p);
        } catch (IOException e) {
            throw new RuntimeException("Load " + PROPERTIES_FILE_NAMES + " error", e);
        }
    }

    public void reload(Map map) {
        Objects.requireNonNull(map);
        logger.info("Start Load GeneratorPropeties from map:{}", map.toString());
        Properties p = new Properties();
        map.forEach((k, v) -> p.setProperty(k.toString(), v.toString()));
        setSepicalProperties(p, null);

        setProperties(p);

    }

    private  void setSepicalProperties(Properties p, String[] loadedFiles) {
        if (loadedFiles != null && loadedFiles.length > 0) {
            String basedir = p.getProperty("basedir");
            if (basedir != null && basedir.startsWith(".")) {
                p.setProperty("basedir", new File(new File(loadedFiles[0]).getParent(), basedir).getAbsolutePath());
            }
        }
    }

    public  Properties getProperties() {
        return getHelper().getProperties();
    }

    private  PropertiesHelper getHelper() {
        Properties fromContext = GeneratorContext.getGeneratorProperties();
        if (fromContext != null) {
            return new PropertiesHelper(fromContext, true);
        }
        return propertiesHelper;
    }

    public  String getProperty(String key, String defaultValue) {
        return getHelper().getProperty(key, defaultValue);
    }

    public  String getProperty(String key) {
        return getHelper().getProperty(key);
    }

    public  String getProperty(GeneratorConstants key) {
        return getHelper().getProperty(key.code, key.defaultValue);
    }

    public  String getRequiredProperty(String key) {
        return getHelper().getRequiredProperty(key);
    }

    public  String getRequiredProperty(GeneratorConstants key) {
        return getHelper().getRequiredProperty(key.code);
    }

    public  int getRequiredInt(String key) {
        return getHelper().getRequiredInt(key);
    }

    public  boolean getRequiredBoolean(String key) {
        return getHelper().getRequiredBoolean(key);
    }

    public  String getNullIfBlank(String key) {
        return getHelper().getNullIfBlank(key);
    }

    public  String getNullIfBlank(GeneratorConstants key) {
        return getHelper().getNullIfBlank(key.code);
    }

    public  String[] getStringArray(String key) {
        return getHelper().getStringArray(key);
    }

    public  String[] getStringArray(GeneratorConstants key) {
        return getHelper().getStringArray(key.code);
    }

    public  int[] getIntArray(String key) {
        return getHelper().getIntArray(key);
    }

    public  boolean getBoolean(String key, boolean defaultValue) {
        return getHelper().getBoolean(key, defaultValue);
    }

    public  boolean getBoolean(GeneratorConstants key) {
        return getHelper().getBoolean(key.code, Boolean.parseBoolean(key.defaultValue));
    }

    public  void setProperty(GeneratorConstants key, String value) {
        setProperty(key.code, value);
    }

    public  void setProperty(String key, String value) {
        logger.debug("[setProperty()] {}={}", key, value);
        getHelper().setProperty(key, value);
    }

    public  void setProperties(Properties inputProps) {
        propertiesHelper = new PropertiesHelper(inputProps, true);
        for (Iterator it = propertiesHelper.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
//            assertPropertyKey(entry.getKey().toString());
            logger.debug("[Property] {}={}", entry.getKey(), entry.getValue());
        }

    }

}
