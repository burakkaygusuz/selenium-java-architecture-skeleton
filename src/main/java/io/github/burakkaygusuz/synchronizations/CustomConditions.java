package io.github.burakkaygusuz.synchronizations;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.text.MessageFormat;
import java.util.Set;

public class CustomConditions {

  private static final Set<String> TYPEABLE_INPUT_TYPES = Set.of(
          "text", "email", "password", "number", "tel", "url", "search",
          "date", "datetime-local", "month", "week", "time");

  private CustomConditions() {
  }

  /**
   * Checks if the element is in a basic typeable state (displayed, enabled, not
   * readonly).
   *
   * @param element The WebElement to check.
   * @return true if the element is displayed, enabled, and not readonly, false
   *         otherwise.
   */
  private static boolean isElementInBasicTypableState(WebElement element) {
    if (!element.isDisplayed() || !element.isEnabled()) {
      return false;
    }
    String readOnly = element.getDomAttribute("readonly");
    return readOnly == null || readOnly.isEmpty() || "false".equalsIgnoreCase(readOnly);
  }

  /**
   * Checks if the element's tag and type correspond to a typeable element.
   *
   * @param element The WebElement to check.
   * @return true if the element is a textarea or a supported input type, false
   *         otherwise.
   */
  private static boolean isElementTypableTagAndType(WebElement element) {
    String tagName = element.getTagName().toLowerCase();
    if ("textarea".equals(tagName)) {
      return true;
    }
    if ("input".equals(tagName)) {
      String inputType = element.getDomAttribute("type");
      return inputType != null && TYPEABLE_INPUT_TYPES.contains(inputType.toLowerCase());
    }
    return false;
  }

  /**
   * An expectation for checking if the given element is visible, enabled,
   * not readonly, and is either a textarea or a text-type input field.
   *
   * @param element the WebElement
   * @return The WebElement once it is typeable, otherwise null.
   */
  public static ExpectedCondition<WebElement> elementToBeTypeable(final WebElement element) {
    return new ExpectedCondition<>() {
      @Override
      public WebElement apply(WebDriver driver) {
        try {
          if (isElementInBasicTypableState(element) && isElementTypableTagAndType(element)) {
            return element;
          } else {
            return null;
          }
        } catch (StaleElementReferenceException e) {
          return null;
        }
      }

      @Override
      public String toString() {
        return MessageFormat.format("element to be typeable: {0}", element);
      }
    };
  }
}
