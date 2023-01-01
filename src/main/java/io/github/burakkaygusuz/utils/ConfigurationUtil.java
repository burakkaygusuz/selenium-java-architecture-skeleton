package io.github.burakkaygusuz.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConfigurationUtil {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(ConfigurationUtil.class.getName());
    private static ConfigurationUtil instance;
    private final FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    public ConfigurationUtil(String fileName) {
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(new Parameters().properties()
                        .setFileName(fileName));
    }

    public static synchronized ConfigurationUtil getInstance() {
        if (instance == null)
            instance = new ConfigurationUtil("config.properties");
        return instance;
    }

    public Configuration getConfiguration() {
        Configuration config;
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.error("Unable to find configuration file.");
            throw new RuntimeException(e);
        }
        return config;
    }
}
