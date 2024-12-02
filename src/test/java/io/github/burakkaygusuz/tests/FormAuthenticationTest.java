package io.github.burakkaygusuz.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class FormAuthenticationTest extends TestBase {

  @Test(testName = "Login Test")
  void loginTest() {
    driver.get(props.getProperty("BASE_URL"));
    assertThat(driver.getTitle()).isEqualTo("The Internet");

    final By loginPageLinkTextLocator = By.linkText("Form Authentication");
    final By usernameInputLocator = By.id("username");
    final By passwordInputLocator = By.id("password");
    final By loginButtonLocator = By.cssSelector("button[type='submit']");
    final By securePageMessageLocator = By.id("flash");

    webElementService.findButton(loginPageLinkTextLocator).click();
    assertThat(webElementService.findElement(By.tagName("h2")).getText()).isEqualTo("Login Page");
    webElementService.findInput(usernameInputLocator).sendKeys("tomsmith");
    webElementService.findInput(passwordInputLocator).sendKeys("SuperSecretPassword!");

    assertSoftly(soft -> {
      soft.assertThat(webElementService.findDomProperty(usernameInputLocator, "value")).isEqualTo("tomsmith");
      soft.assertThat(webElementService.findDomProperty(passwordInputLocator, "value"))
          .isEqualTo("SuperSecretPassword!");
    });

    webElementService.findButton(loginButtonLocator).submit();
    wait.until(ExpectedConditions.urlContains("/secure"));
    assertThat(webElementService.findElement(securePageMessageLocator).getText().trim())
        .contains("You logged into a secure area!");
  }
}
