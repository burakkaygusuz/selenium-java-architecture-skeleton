package io.github.burakkaygusuz.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class FormAuthenticationTest extends TestBase {

    @Test(testName = "Login Test")
    void loginTest() {
        driver.get(config.getString("BASE_URL"));

        final By loginPageLinkTextLocator = By.linkText("Form Authentication");
        final By usernameInputLocator = By.id("username");
        final By passwordInputLocator = By.id("password");
        final By loginButtonLocator = By.cssSelector("button[type='submit']");
        final By securePageMessageLocator = By.id("flash");

        commandService.locator(loginPageLinkTextLocator).click();
        commandService.locator(usernameInputLocator).sendKeys("tomsmith");
        commandService.locator(passwordInputLocator).sendKeys("SuperSecretPassword!");

        assertSoftly(soft -> {
            soft.assertThat(commandService.locator(usernameInputLocator).getAttribute("value")).isEqualTo("tomsmith");
            soft.assertThat(commandService.locator(passwordInputLocator).getAttribute("value")).isEqualTo("SuperSecretPassword!");
        });

        commandService.locator(loginButtonLocator).submit();
        wait.until(ExpectedConditions.urlContains("/secure"));
        assertThat(commandService.locator(securePageMessageLocator).getText().trim()).contains("You logged into a secure area!");
    }
}
