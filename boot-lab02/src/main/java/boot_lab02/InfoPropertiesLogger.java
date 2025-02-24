package boot_lab02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class InfoPropertiesLogger {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @PostConstruct
    public void logInfoProperties() {
        logger.debug("Info Properties:");
        logger.debug("App Name: " + appName);
        logger.debug("App Description: " + appDescription);
        logger.debug("App Version: " + appVersion);
    }

}
