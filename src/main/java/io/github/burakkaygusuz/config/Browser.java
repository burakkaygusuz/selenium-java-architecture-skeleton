package io.github.burakkaygusuz.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Browser {

  CHROME {
    @Override
    protected ChromeOptions getOptions() {
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("profile.default_content_setting_values.notifications", 2);
      prefs.put("profile.managed_default_content_settings.javascript", 1);
      prefs.put("credentials_enable_service", false);
      prefs.put("profile.password_manager_enabled", false);

      final ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions
          .enableBiDi()
          .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
          .setExperimentalOption("prefs", prefs)
          .addArguments("--disable-gpu", "--disable-logging", "--disable-dev-shm-usage")
          .setPageLoadStrategy(PageLoadStrategy.NORMAL)
          .setAcceptInsecureCerts(true);
      return chromeOptions;
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
  },
  EDGE {
    @Override
    protected EdgeOptions getOptions() {
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("profile.default_content_setting_values.notifications", 2);
      prefs.put("profile.managed_default_content_settings.javascript", 1);
      prefs.put("credentials_enable_service", false);
      prefs.put("profile.password_manager_enabled", false);

      final EdgeOptions edgeOptions = new EdgeOptions();
      edgeOptions
          .enableBiDi()
          .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
          .setExperimentalOption("prefs", prefs)
          .addArguments("--disable-gpu", "--disable-logging", "--disable-dev-shm-usage")
          .setPageLoadStrategy(PageLoadStrategy.NORMAL)
          .setAcceptInsecureCerts(true);
      return edgeOptions;
    }
  };

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }

  protected abstract AbstractDriverOptions<?> getOptions();
}
