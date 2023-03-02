package io.github.burakkaygusuz.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ConfigurationUtil {

    private static final Logger LOGGER = LogManager.getLogger(ConfigurationUtil.class);
    private static ConfigurationUtil instance;
    private final FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    private Configuration config;

    private ConfigurationUtil(File configFile) {
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(new Parameters().properties()
                        .setFile(configFile));
    }

    public static synchronized ConfigurationUtil getInstance() {
        if (instance == null)
            instance = new ConfigurationUtil(new File("config.properties"));
        return instance;
    }

    public static synchronized ConfigurationUtil getInstance(String fileName) {
        if (instance == null)
            instance = new ConfigurationUtil(new File(fileName));
        return instance;
    }

    public Configuration getConfiguration() {
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.error("Unable to find configuration file: %s".formatted(e.getMessage()), e);
            throw new RuntimeException(e);
        }
        return config;
    }

    public void saveConfiguration() {
        try {
            FileHandler handler = new FileHandler((PropertiesConfiguration) config);
            handler.save();
        } catch (ConfigurationException e) {
            LOGGER.error("Unable to save configuration file: %s".formatted(e.getMessage()), e);
            throw new RuntimeException(e);
        }
    }
}
