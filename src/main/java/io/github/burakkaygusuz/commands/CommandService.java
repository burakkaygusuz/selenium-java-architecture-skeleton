package io.github.burakkaygusuz.commands;

public interface CommandService {

    void click();

    void submit();

    void clear();

    String getText();

    String getAttribute(String attribute);

    void sendKeys(CharSequence... keysToSend);

    boolean isDisplayed();

    boolean isSelected();
}
