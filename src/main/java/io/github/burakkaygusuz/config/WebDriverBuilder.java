package io.github.burakkaygusuz.config;

import io.github.burakkaygusuz.listeners.CustomWebDriverListener;
import lombok.SneakyThrows;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverBuilder {

    private static final ThreadLocal<RemoteWebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();
    private final String browser;
    private final AbstractDriverOptions<?> options;
    private URL url;
    private boolean isTracingEnabled = true;

    public WebDriverBuilder(String browser) {
        this.browser = browser;
        ThreadContext.put("browser", browser.toUpperCase());
        this.options = Browser.valueOf(browser.toUpperCase()).getOptions();
    }

    @SneakyThrows(MalformedURLException.class)
    public WebDriverBuilder setUrl(String path) {
        url = new URL(path);
        return this;
    }

    public WebDriverBuilder enableTracing(boolean isTracingEnabled) {
        this.isTracingEnabled = isTracingEnabled;
        return this;
    }

    public WebDriverBuilder enableHeadless() {
        switch (browser) {
            case "chrome" -> {
                final ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("headless=new");
                options.merge(chromeOptions);
            }
            case "firefox" -> {
                final FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                options.merge(firefoxOptions);
            }
            case "edge" -> {
                final EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("headless=new");
                options.merge(edgeOptions);
            }
            default -> throw new IllegalStateException("Unsupported browser: %s".formatted(browser));
        }
        return this;
    }

    public WebDriver build() {
        DRIVER_THREAD_LOCAL.set(url != null ? new RemoteWebDriver(url, options, isTracingEnabled) : new RemoteWebDriver(options, isTracingEnabled));
        WebDriver original = DRIVER_THREAD_LOCAL.get();
        return new EventFiringDecorator<>(new CustomWebDriverListener()).decorate(original);
    }
}
