package io.github.burakkaygusuz.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class FormAuthenticationTest extends TestBase {

    @Test(testName = "Login Test")
    void loginTest() {
        driver.get(config.getString("BASE_URL"));

        final By loginPageLinkTextLocator = By.linkText("Form Authentication");
        final By usernameInputLocator = By.id("username");
        final By passwordInputLocator = By.id("password");
        final By loginButtonLocator = By.cssSelector("button[type='submit']");

        commandService.click(loginPageLinkTextLocator);
        commandService.sendKeys(usernameInputLocator, "tomsmith");
        commandService.sendKeys(passwordInputLocator, "SuperSecretPassword!");

        assertSoftly(soft -> {
            soft.assertThat(commandService.getAttribute(usernameInputLocator, "value")).isEqualTo("tomsmith");
            soft.assertThat(commandService.getAttribute(passwordInputLocator, "value")).isEqualTo("SuperSecretPassword!");
        });

        commandService.submit(loginButtonLocator);

        final By securePageMessageLocator = By.id("flash");
        assertSoftly(soft -> {
            soft.assertThat(driver.getCurrentUrl()).contains("/secure");
            soft.assertThat(commandService.getText(securePageMessageLocator).trim()).contains("You logged into a secure area!");
        });
    }
}
