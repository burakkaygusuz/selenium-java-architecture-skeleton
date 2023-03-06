package io.github.burakkaygusuz.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.WebDriverListener;

public class CustomWebDriverListener implements WebDriverListener {

    private static final Logger LOGGER = LogManager.getLogger(CustomWebDriverListener.class);

    @Override
    public void afterGet(WebDriver driver, String url) {
        LOGGER.info("Web page has been opened: %s".formatted(url));
    }

    @Override
    public void afterDeleteAllCookies(WebDriver.Options options) {
        LOGGER.info("All cookies has been purged...");
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        LOGGER.info("Terminating session id: %s".formatted(sessionId));
    }

    @Override
    public void afterQuit(WebDriver driver) {
        LOGGER.info("All windows closed and the webDriver sessions terminated...");
    }
}
