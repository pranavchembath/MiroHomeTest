package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import util.ExtentReport;

public class TestListeners implements ITestListener {

	ExtentReports extentReport = ExtentReport.getExtentReport();
	ExtentTest extentTest;

	@Override
	public void onTestStart(ITestResult result) {

		extentTest = extentReport.createTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.log(Status.FAIL, "Test failed");
		extentTest.fail(result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
