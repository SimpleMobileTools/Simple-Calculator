# Simple-Calculator
A simple Android opensource calculator with a widget, functional and UI tests

<h3>Running Espresso UI tests</h3>
1. at Build Variants select "Android Instrumentation Tests"
2. Run -> Edit Configurations
3. create a new "Android Tests" configuration, give it a name (i.e. "MainActivityEspressoTest")
4. choose the "app" module
5. OK
6. make sure MainActivityEspressoTest is selected near the Run button
7. Run

<h3>Running Robolectric tests</h3>
1. at Build Variant select "Unit Tests"
2. at the Project tab right click the folder containing the tests (i.e. "calculator.simplemobiletools.com.simple_calculator (test)")
3. select Run 'Tests in 'calculator.simplemob...' to run all the tests
4. if you are on Linux or Mac, go to Run -> Edit Configurations, select the new JUnit configuration and change the "Working Directory" item to "$MODULE_DIR" (without quotes)
5. OK
5. Run
