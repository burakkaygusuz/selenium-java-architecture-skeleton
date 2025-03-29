package io.github.burakkaygusuz.listeners;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

public class CustomTestListener implements ITestListener {

  private static final Logger LOGGER = LogManager.getLogger(CustomTestListener.class);

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.log(Level.INFO, "Test Name : {} started", result.getMethod().getMethodName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    long executionTime = result.getEndMillis() - result.getStartMillis();
    LOGGER.log(Level.INFO, "Test Name : {} => Status : SUCCESS ({}ms)",
        result.getMethod().getMethodName(), executionTime);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    LOGGER.log(Level.ERROR, "Test Name : {} => Status : FAILURE", result.getMethod().getMethodName());
    if (result.getThrowable() != null) {
      LOGGER.log(Level.ERROR, "Failure Reason: {}", result.getThrowable().getMessage());
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LOGGER.log(Level.WARN, "Test Name : {} => Status : SKIPPED", result.getMethod().getMethodName());
  }

  @Override
  public void onStart(ITestContext context) {
    LOGGER.log(Level.INFO, "Test Suite : {} started", context.getName());
  }

  @Override
  public void onFinish(ITestContext context) {
    LOGGER.log(Level.INFO, "Test Suite : {} finished", context.getName());
  }
}
