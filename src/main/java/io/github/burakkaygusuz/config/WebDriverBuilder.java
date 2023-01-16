package io.github.burakkaygusuz.config;

import io.github.burakkaygusuz.listeners.CustomWebDriverListener;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverBuilder {

    private static final ThreadLocal<RemoteWebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();
    private final String browser;
    private URL url;
    private boolean isTracingEnabled = true;

    public WebDriverBuilder(String browser) {
        this.browser = browser;
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

    public WebDriver build() {
        final AbstractDriverOptions<?> options = Browser.valueOf(browser.toUpperCase()).getOptions();
        return new EventFiringDecorator<>(new CustomWebDriverListener()).decorate(DRIVER_THREAD_LOCAL.get());
        DRIVER_THREAD_LOCAL.set(url != null ? new RemoteWebDriver(url, options, isTracingEnabled) : new RemoteWebDriver(options, isTracingEnabled));
    }
}
