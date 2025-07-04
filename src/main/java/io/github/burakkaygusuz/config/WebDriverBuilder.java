package io.github.burakkaygusuz.config;

import io.github.burakkaygusuz.exceptions.UnsupportedBrowserException;
import io.github.burakkaygusuz.listeners.CustomWebDriverListener;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WebDriverBuilder {

  private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();
  private final String browser;
  private final AbstractDriverOptions<?> options;
  private URL url;
  private boolean isTracingEnabled = true;

  public WebDriverBuilder(String browser) {
    this.browser = browser;
    ThreadContext.put("browser", browser.toUpperCase());
    this.options = Browser.valueOf(browser.toUpperCase()).getOptions();
  }

  public WebDriverBuilder setUrl(String path) throws URISyntaxException, MalformedURLException {
    this.url = new URI(path).toURL();
    return this;
  }

  public WebDriverBuilder enableTracing(boolean isTracingEnabled) {
    this.isTracingEnabled = isTracingEnabled;
    return this;
  }

  public WebDriverBuilder enableHeadless() {
    try {
      Browser.valueOf(browser.toUpperCase()).addHeadlessArgument(options);
    } catch (IllegalArgumentException e) {
      throw new UnsupportedBrowserException("Browser does not exist: %s".formatted(browser));
    }
    return this;
  }

  public WebDriver build() {
    try {
      DRIVER_THREAD_LOCAL.set(url != null ? new RemoteWebDriver(url, options, isTracingEnabled)
          : new RemoteWebDriver(options, isTracingEnabled));
      WebDriver original = DRIVER_THREAD_LOCAL.get();
      return new EventFiringDecorator<>(new CustomWebDriverListener()).decorate(original);
    } catch (Exception e) {
      throw new WebDriverException("Failed to build WebDriver", e);
    }
  }
}
