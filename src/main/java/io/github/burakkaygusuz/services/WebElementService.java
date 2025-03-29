package io.github.burakkaygusuz.services;

import io.github.burakkaygusuz.synchronizations.CustomConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Service class for working with WebElements with built-in wait conditions.
 * Implements SearchContext to provide enhanced element finding capabilities.
 */
public class WebElementService implements SearchContext {

  private final WebDriverWait wait;

  public WebElementService(WebDriverWait wait) {
    this.wait = wait;
  }

  /**
   * Finds all elements matching the given locator with built-in wait.
   *
   * @param by the locator to find elements
   * @return a list of matching WebElements
   */
  @Override
  public List<WebElement> findElements(By by) {
    return wait.until(webDriver -> ExpectedConditions.presenceOfAllElementsLocatedBy(by).apply(webDriver));
  }

  /**
   * Finds an element matching the given locator with built-in wait.
   *
   * @param by the locator to find the element
   * @return the matching WebElement
   * @throws TimeoutException       if the element is not
   *                                found within the timeout
   *                                period
   * @throws NoSuchElementException if the element cannot be
   *                                located
   */
  @Override
  public WebElement findElement(By by) {
    try {
      return wait.until(webDriver -> ExpectedConditions.presenceOfElementLocated(by).apply(webDriver));
    } catch (Exception e) {
      throw new NoSuchElementException("Element not found with locator: " + by.toString(), e);
    }
  }

  /**
   * Finds a button element and waits until it's clickable.
   *
   * @param by the locator to find the button
   * @return the clickable button WebElement
   */
  public WebElement findButton(By by) {
    WebElement webElement = this.findElement(by);
    return wait.until(webDriver -> ExpectedConditions.elementToBeClickable(webElement).apply(webDriver));
  }

  /**
   * Finds an input element and waits until it's typeable.
   *
   * @param by the locator to find the input
   * @return the typeable input WebElement
   */
  public WebElement findInput(By by) {
    WebElement webElement = this.findElement(by);
    return wait.until(webDriver -> CustomConditions.elementToBeTypeable(webElement).apply(webDriver));
  }

  /**
   * Finds an element and retrieves one of its DOM properties.
   *
   * @param by       the locator to find the element
   * @param property the DOM property name to retrieve
   * @return the value of the specified DOM property
   */
  public String findDomProperty(By by, String property) {
    WebElement webElement = this.findElement(by);
    return webElement.getDomProperty(property);
  }
}
