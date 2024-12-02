package io.github.burakkaygusuz.listeners;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomTestListener implements ITestListener {

  private static final Logger LOGGER = LogManager.getLogger(CustomTestListener.class);

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.log(Level.INFO, "Test Name : {} started", result.getMethod().getMethodName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LOGGER.log(Level.INFO, "Test Name : {} => Status : SUCCESS", result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    LOGGER.log(Level.ERROR, "Test Name : {} => Status : FAILURE", result.getMethod().getMethodName());
  }
}
