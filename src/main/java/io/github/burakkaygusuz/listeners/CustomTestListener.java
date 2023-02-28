package io.github.burakkaygusuz.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomTestListener implements ITestListener {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(CustomTestListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Test Name : %s started".formatted(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test Name : %s => Status : SUCCESS".formatted(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test Name : %s => Status : FAILURE".formatted(result.getMethod().getMethodName()));
    }
}
