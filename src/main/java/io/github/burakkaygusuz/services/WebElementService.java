package io.github.burakkaygusuz.services;

import io.github.burakkaygusuz.synchronizations.CustomConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebElementService implements SearchContext {

  private final WebDriverWait wait;

  public WebElementService(WebDriverWait wait) {
    this.wait = wait;
  }

  @Override
  public List<WebElement> findElements(By by) {
    return wait.until(webDriver -> ExpectedConditions.presenceOfAllElementsLocatedBy(by).apply(webDriver));
  }

  @Override
  public WebElement findElement(By by) {
    return wait.until(webDriver -> ExpectedConditions.presenceOfElementLocated(by).apply(webDriver));
  }

  public WebElement findButton(By by) {
    WebElement webElement = this.findElement(by);
    return wait.until(webDriver -> ExpectedConditions.elementToBeClickable(webElement).apply(webDriver));
  }

  public WebElement findInput(By by) {
    WebElement webElement = this.findElement(by);
    return wait.until(webDriver -> CustomConditions.elementToBeTypeable(webElement).apply(webDriver));
  }

  public String findAttribute(By by, String attribute) {
    WebElement webElement = this.findElement(by);
    return webElement.getAttribute(attribute);
  }
}
