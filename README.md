This is a repo containing the automation for the website https://automationintesting.online/

You will need a brower installed on your computer. supported browsers are:

chrome, firefox and edge

########### instructions ###########
1. clone the repo - this can be done via the command line:
   git clone https://github.com/priceey/companiesHouseAutomation.git
   or by download in the code from the broser.
   
3. To run a test, navigate to the base folder and run:
    mvn clean test -Dcucumber.filter.tags="@Regression" -Dcucumber.publish.enabled=true

    This will run the entire suite of tests. If you want more specific tests there are:
   @EndToEnd - end to end tests - one includes some basic accessibility testing.
   @Messages - runs a messasge test
   @Validation - runs the validation test

4.  Currenlty the default brower is chrome. if you wish to change the browser or don't have chrome installed you can run

   mvn clean test -Dcucumber.filter.tags="@Regression" -Dcucumber.publish.enabled=true -Dbrowser=firefox

   which will launch the test in firefox. As mentioned previously, the options you can add for browser are chrome, firefox or edge.

########### Results ###########
Once a test run is completed the following text will be visible in the terminal window

│ View your Cucumber Report at:                                            │
│ https://reports.cucumber.io/reports/9a9f9660-540a-4a85-aa69-454ec643289a │
│                                                                          │
│ This report will self-destruct in 24h.                                   |

Clicking that link should display the cucumber results file. As stated this will only be available for 24 hours.

Also available is a accessibility report. If the scenario  "Basic end to end test" then some accessibilty report output is created.

At present this is only availalbe in json format, but it can be converted to a more readable html report. I haven't coded that section, but i will add an example of the report to the repo.

These are found in the folder /target/accessibility-reports-json/ and each page tested.
