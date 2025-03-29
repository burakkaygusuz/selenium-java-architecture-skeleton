package io.github.burakkaygusuz.synchronizations;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

  private CustomConditions() {
  }

  /**
   * An expectation for checking if all selectable options are present.
   *
   * @param element the Web element to typing into it.
   * @return The Web element to a text-typeable.
   */
  public static ExpectedCondition<WebElement> elementToBeTypeable(final WebElement element) {
    return new ExpectedCondition<>() {
      @Override
      public WebElement apply(WebDriver driver) {
        String inputType = element.getDomProperty("type");
        String inputName = element.getDomProperty("name");
        try {
          if ((inputType != null && inputType.matches("text|mail|password|number")) ||
              (inputName != null && inputName.contains("message"))) {
            return element;
          }
          return null;
        } catch (StaleElementReferenceException e) {
          return null;
        }
      }

      @Override
      public String toString() {
        return "Element to be text typeable: %s".formatted(element);
      }
    };
  }
}
