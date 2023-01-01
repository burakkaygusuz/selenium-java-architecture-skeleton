package io.github.burakkaygusuz.commands;

import org.openqa.selenium.By;

public interface CommandService {

    void click(By locator);

    void submit(By locator);

    void clear(By locator);

    String getText(By locator);

    String getAttribute(By locator, String attribute);

    void sendKeys(By locator, CharSequence... keysToSend);

    boolean isDisplayed(By locator);

    boolean isSelected(By locator);
}
