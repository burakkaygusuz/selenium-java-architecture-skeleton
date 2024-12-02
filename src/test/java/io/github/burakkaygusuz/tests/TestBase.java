package io.github.burakkaygusuz.tests;

import io.github.burakkaygusuz.config.WebDriverBuilder;
import io.github.burakkaygusuz.listeners.CustomTestListener;
import io.github.burakkaygusuz.services.WebElementService;
import io.github.burakkaygusuz.utils.PropertyUtil;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;

@Listeners({ CustomTestListener.class })
public class TestBase {

  protected PropertyUtil props = PropertyUtil.getInstance("config.properties");
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected WebElementService webElementService;

  @BeforeMethod
  @Parameters(value = "browser")
  public void setUp(String browser) throws MalformedURLException, URISyntaxException {
    ThreadContext.put("browser", browser.toUpperCase());
    driver = new WebDriverBuilder(browser)
        .setUrl(props.getProperty("GRID_URL"))
        .enableHeadless()
        .enableTracing(false)
        .build();
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    webElementService = new WebElementService(wait);
  }

  @AfterMethod
  public synchronized void tearDown() {
    if (driver != null)
      driver.quit();
  }
}
