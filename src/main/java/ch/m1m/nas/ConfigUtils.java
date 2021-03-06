package ch.m1m.nas;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

// https://commons.apache.org/proper/commons-configuration/userguide/user_guide.html
// https://commons.apache.org/proper/commons-configuration/userguide/quick_start.html

public class ConfigUtils {

    private static final String KEY_BROADCAST_ADDRESS = "network.broadcast_address";
    private static final String KEY_NAS_MAC_ADDRESS = "nas.mac_address";
    private static final String KEY_NAS_ADMINUI_URL = "nas.adminui_url";
    private static final String KEY_NAS_USER_ID = "nas.user_id";
    private static final String KEY_NAS_USER_PASSWORD = "nas.user_password";

    private static Logger log = LoggerFactory.getLogger(ConfigUtils.class);


    public static Config loadConfiguration() {

        Config config = new Config();

        File userDir = FileUtils.getUserDirectory();
        log.info("user directory: {}", userDir.toString());

        List<String> fileNameList = Arrays.asList(".nascontrol.conf", "nascontrol.conf", "nascontrol.cfg");

        try {

            for (String fileName : fileNameList) {

                String configFileName = userDir + "/" + fileName;

                File configFile = new File(configFileName);
                if (!configFile.exists()) {
                    log.info("config file does not exist: {}", configFileName);
                } else {
                    log.info("load existing configuration from: {}", configFileName);
                    Configurations configs = new Configurations();
                    Configuration apacheConfig = configs.properties(new File(configFileName));
                    mapConfigItems(apacheConfig, config);
                    break;
                }
            }

        } catch (ConfigurationException e) {
            log.error("apache configuration failed", e);
        }

        return config;
    }

    private static void mapConfigItems(Configuration apacheConfig, Config config) {
        String key;
        String valString;

        key = KEY_BROADCAST_ADDRESS;
        valString = apacheConfig.getString(key);
        log.info("mapped key={} value={}", key, valString);
        config.setBroadcastAddress(valString);

        key = KEY_NAS_MAC_ADDRESS;
        valString = apacheConfig.getString(key);
        log.info("mapped key={} value={}", key, valString);
        config.setMacAddress(valString);

        key = KEY_NAS_ADMINUI_URL;
        valString = apacheConfig.getString(key);
        log.info("mapped key={} value={}", key, valString);
        config.setNasAdminUI(valString);

        key = KEY_NAS_USER_ID;
        valString = apacheConfig.getString(key);
        log.info("mapped key={} value={}", key, valString);
        config.setNasUserId(valString);

        key = KEY_NAS_USER_PASSWORD;
        valString = apacheConfig.getString(key);
        log.info("mapped key={} value={}", key, valString);
        config.setNasUserPassword(valString);
    }
}
