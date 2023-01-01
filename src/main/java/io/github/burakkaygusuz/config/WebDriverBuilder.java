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

    private final ThreadLocal<RemoteWebDriver> driverThreadLocal = new ThreadLocal<>();
    private final String browser;
    private URL url;

    public WebDriverBuilder(String browser) {
        this.browser = browser;
    }

    @SneakyThrows(MalformedURLException.class)
    public WebDriverBuilder setUrl(String path) {
        url = new URL(path);
        return this;
    }

    public WebDriver build() {
        final AbstractDriverOptions<?> options = Browser.valueOf(browser.toUpperCase()).getOptions();
        driverThreadLocal.set(url != null ? new RemoteWebDriver(url, options, false) : new RemoteWebDriver(options, false));
        return new EventFiringDecorator<>(new CustomWebDriverListener()).decorate(driverThreadLocal.get());
    }
}
