package io.github.burakkaygusuz.commands;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.github.burakkaygusuz.synchronizations.CustomConditions.elementToBeTypeable;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class DefaultCommandService implements CommandService {
    private final WebDriverWait wait;
    private final WebDriver driver;
    private By locator;

    public DefaultCommandService(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public DefaultCommandService locator(By locator) {
        this.locator = locator;
        return this;
    }

    @Override
    public void click() {
        WebElement element = wait.until(driver -> presenceOfElementLocated(locator).apply(driver));
        wait.until(driver -> elementToBeClickable(element).apply(driver)).click();
    }

    @Override
    public void submit() {
        WebElement element = wait.until(driver -> presenceOfElementLocated(locator).apply(driver));
        wait.until(driver -> elementToBeClickable(element).apply(driver)).submit();
    }

    @Override
    public void clear() {
        wait.until(driver -> visibilityOfElementLocated(locator)).apply(driver).clear();
    }

    @Override
    public String getText() {
        String expectedText = wait.until(driver -> presenceOfElementLocated(locator).apply(driver)).getText();
        return wait.until(driver -> textToBePresentInElementLocated(locator, expectedText).apply(driver)) ? expectedText
                : null;
    }

    @Override
    public String getAttribute(String attribute) {
        return driver.findElement(locator).getAttribute(attribute);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        WebElement element = wait.until(driver -> visibilityOfElementLocated(locator).apply(driver));
        wait.until(driver -> elementToBeTypeable(element).apply(driver)).sendKeys(keysToSend);
    }

    @Override
    public boolean isDisplayed() {
        return wait.until(driver -> visibilityOfElementLocated(locator).apply(driver)).isDisplayed();
    }

    @Override
    public boolean isSelected() {
        WebElement element = wait.until(driver -> visibilityOfElementLocated(locator).apply(driver));
        return wait.until(driver -> elementSelectionStateToBe(element, true).apply(driver));
    }
}
