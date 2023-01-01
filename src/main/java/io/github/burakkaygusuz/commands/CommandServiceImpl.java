package io.github.burakkaygusuz.commands;

import io.github.burakkaygusuz.synchronizations.CustomExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommandServiceImpl implements CommandService {
    private final WebDriverWait wait;
    private final WebDriver driver;

    public CommandServiceImpl(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Override
    public void click(By locator) {
        WebElement element = wait.until(driver -> ExpectedConditions.presenceOfElementLocated(locator).apply(driver));
        wait.until(driver -> ExpectedConditions.elementToBeClickable(element).apply(driver)).click();
    }

    @Override
    public void submit(By locator) {
        WebElement element = wait.until(driver -> ExpectedConditions.presenceOfElementLocated(locator).apply(driver));
        wait.until(driver -> ExpectedConditions.elementToBeClickable(element).apply(driver)).submit();
    }

    @Override
    public void clear(By locator) {
        wait.until(driver -> ExpectedConditions.visibilityOfElementLocated(locator)).apply(driver).clear();
    }

    @Override
    public String getText(By locator) {
        String expectedText = wait.until(driver -> ExpectedConditions.presenceOfElementLocated(locator).apply(driver)).getText();
        return wait.until(driver -> ExpectedConditions.textToBePresentInElementLocated(locator, expectedText).apply(driver)) ? expectedText : null;
    }

    @Override
    public String getAttribute(By locator, String attribute) {
        return driver.findElement(locator).getAttribute(attribute);
    }

    @Override
    public void sendKeys(By locator, CharSequence... keysToSend) {
        WebElement element = wait.until(driver -> ExpectedConditions.visibilityOfElementLocated(locator).apply(driver));
        wait.until(driver -> CustomExpectedConditions.elementToBeTypeable(element).apply(driver)).sendKeys(keysToSend);
    }

    @Override
    public boolean isDisplayed(By locator) {
        return wait.until(driver -> ExpectedConditions.visibilityOfElementLocated(locator).apply(driver)).isDisplayed();
    }

    @Override
    public boolean isSelected(By locator) {
        WebElement element = wait.until(driver -> ExpectedConditions.visibilityOfElementLocated(locator).apply(driver));
        return wait.until(driver -> ExpectedConditions.elementSelectionStateToBe(element, true).apply(driver));
    }
}
