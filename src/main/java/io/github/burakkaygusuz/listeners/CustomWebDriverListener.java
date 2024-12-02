package io.github.burakkaygusuz.listeners;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.WebDriverListener;

public class CustomWebDriverListener implements WebDriverListener {

  private static final Logger LOGGER = LogManager.getLogger(CustomWebDriverListener.class);

  @Override
  public void beforeGet(WebDriver driver, String url) {
    LOGGER.log(Level.INFO, "Navigating to URL: {}", url);
  }

  @Override
  public void afterGet(WebDriver driver, String url) {
    LOGGER.log(Level.INFO, "Navigated to URL: {}", url);
  }

  @Override
  public void afterDeleteAllCookies(WebDriver.Options options) {
    LOGGER.log(Level.INFO, "All cookies has been purged...");
  }

  @Override
  public void beforeQuit(WebDriver driver) {
    SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
    LOGGER.log(Level.INFO, "Terminating session id: {}", sessionId);
  }

  @Override
  public void afterQuit(WebDriver driver) {
    LOGGER.log(Level.INFO, "All windows closed and the webDriver sessions terminated...");
  }
}
