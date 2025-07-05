package io.github.burakkaygusuz.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.Collections;
import java.util.Map;

public enum Browser {

  CHROME {
    @Override
    protected ChromeOptions getOptions() {
      final ChromeOptions chromeOptions = new ChromeOptions();
      applyCommonOptions(chromeOptions);
      chromeOptions.setExperimentalOption("prefs", getCommonPrefs());
      return chromeOptions;
    }

    @Override
    protected String getHeadlessArgument() {
      return "headless=new";
    }

    @Override
    protected void addHeadlessArgument(AbstractDriverOptions<?> options) {
      ((ChromeOptions) options).addArguments(getHeadlessArgument());
    }
  },
  FIREFOX {
    @Override
    protected FirefoxOptions getOptions() {
      final FirefoxOptions firefoxOptions = new FirefoxOptions();
      final FirefoxProfile firefoxProfile = new FirefoxProfile();

      firefoxProfile.setAcceptUntrustedCertificates(true);
      firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
      firefoxOptions
          .enableBiDi()
          .addPreference("dom.webnotifications.enabled", false)
          .addPreference("gfx.direct2d.disabled", true)
          .addPreference("layers.acceleration.force-enabled", true)
          .addPreference("javascript.enabled", true)
          .setPageLoadStrategy(PageLoadStrategy.NORMAL)
          .setProfile(firefoxProfile);
      return firefoxOptions;
    }

    @Override
    protected String getHeadlessArgument() {
      return "-headless";
    }

    @Override
    protected void addHeadlessArgument(AbstractDriverOptions<?> options) {
      ((FirefoxOptions) options).addArguments(getHeadlessArgument());
    }
  },
  EDGE {
    @Override
    protected EdgeOptions getOptions() {
      final EdgeOptions edgeOptions = new EdgeOptions();
      applyCommonOptions(edgeOptions);
      edgeOptions.setExperimentalOption("prefs", getCommonPrefs());
      return edgeOptions;
    }

    @Override
    protected String getHeadlessArgument() {
      return "headless=new";
    }

    @Override
    protected void addHeadlessArgument(AbstractDriverOptions<?> options) {
      ((EdgeOptions) options).addArguments(getHeadlessArgument());
    }
  };

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }

  protected abstract AbstractDriverOptions<?> getOptions();

  protected abstract String getHeadlessArgument();

  protected abstract void addHeadlessArgument(AbstractDriverOptions<?> options);

  private static Map<String, Object> getCommonPrefs() {
    return Map.ofEntries(
        Map.entry("profile.default_content_setting_values.notifications", 2),
        Map.entry("profile.managed_default_content_settings.javascript", 1),
        Map.entry("credentials_enable_service", false),
        Map.entry("profile.password_manager_enabled", false));
  }

  private static void applyCommonOptions(ChromeOptions options) {
    options
        .enableBiDi()
        .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
        .addArguments("--disable-gpu", "--disable-logging", "--disable-dev-shm-usage")
        .setPageLoadStrategy(PageLoadStrategy.NORMAL)
        .setAcceptInsecureCerts(true);
  }

  private static void applyCommonOptions(EdgeOptions options) {
    options
        .enableBiDi()
        .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
        .addArguments("--disable-gpu", "--disable-logging", "--disable-dev-shm-usage")
        .setPageLoadStrategy(PageLoadStrategy.NORMAL)
        .setAcceptInsecureCerts(true);
  }
}
