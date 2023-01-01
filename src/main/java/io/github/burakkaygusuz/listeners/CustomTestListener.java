package io.github.burakkaygusuz.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomTestListener implements ITestListener {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(CustomTestListener.class.getName());

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("%s started".formatted(context.getCurrentXmlTest().getSuite().getName()));
        LOGGER.info("OS : %s | OS Version: %s | Arch : %s".formatted(System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")));
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("%s finished".formatted(context.getCurrentXmlTest().getSuite().getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test Name : %s | Status : SUCCESS".formatted(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test Name : %s | Status : FAILURE".formatted(result.getMethod().getMethodName()));
    }
}
