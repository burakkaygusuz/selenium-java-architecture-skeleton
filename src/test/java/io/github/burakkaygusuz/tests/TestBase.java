package io.github.burakkaygusuz.tests;

import io.github.burakkaygusuz.commands.CommandServiceImpl;
import io.github.burakkaygusuz.config.WebDriverBuilder;
import io.github.burakkaygusuz.listeners.CustomTestListener;
import io.github.burakkaygusuz.utils.ConfigurationUtil;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.time.Duration;

@Listeners({CustomTestListener.class})
public class TestBase {
    protected Configuration config = ConfigurationUtil.getInstance().getConfiguration();
    protected WebDriver driver;
    protected CommandServiceImpl commandService;

    @BeforeMethod
    @Parameters(value = "browser")
    public void setUp(String browser) {
        driver = new WebDriverBuilder(browser).build();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        commandService = new CommandServiceImpl(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));
    }

    @AfterMethod
    public synchronized void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }
}
