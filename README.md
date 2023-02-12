# MiroHomeTest

Creating E2E tests that can cover sign up functions in Miro.

Assignment requirements and Tips:

* Feel free to choose a coding language among Javascript/Typescript/Java. We would suggest picking Typescript since our team is using TS as the main E2E UI
test language.
* You could choose any test framework such as selenium, playwright, cypress or testcafe etc.
* Please avoid using some BDD Framework such as Cucumber.
* Please add a Readme within the project
* We can consider a successful registration when the "Check your email" screen is visible.
* The way to manage test structure is also as important as designing the test cases from all possible perspectives.
* We suggest the code of the implemented assignment should not be shared publicly. Please host your code within Gitlab and share the project with
qa.review@miro.com as a “developer” (not as a guest).

Solution:
----------------------------------------------
The solution is build using Selenium with java in data driven framework and solution is uploaded to Github location:

* Created Maven Project with Selenium WebDriver and other dependencies
* Defined Project Structure with Data Driven Framework Setup
* Created excel data for the parameterization for multiple runs
* Implemented Data driven Mechanism and parameterization to run tests with multiple data sets in multiple browsers using Apache POI.
* Generated detailed html reports for Test Execution results
* Defined Global properties for driving all the global variables from Properties
* Developed Test with all validation and assertions


Important Paths:
----------------------------
* Working Directory:com.miro
* Properties file : /src/test/resources/
* Parameterization Excel input: /src/test/resources/
* Reports : /reports/


Improvement:
--------------------------------------------
* Test Coverage -Automated only the mentioned one end to end scenario to demonstrate the framework.
* Logging is not implemented
