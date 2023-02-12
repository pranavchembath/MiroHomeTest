package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {

	private static ExtentReports extentReport;
	private static String extentReportFilePath;

	public static ExtentReports getExtentReport() {

		extentReportFilePath = System.getProperty("user.dir") + "\\reports\\extendreport.html";
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFilePath);
		sparkReporter.config().setReportName("Miro Web Automation Results");
		sparkReporter.config().setDocumentTitle("Miro");

		extentReport = new ExtentReports();
		extentReport.attachReporter(sparkReporter);

		return extentReport;

	}
}
