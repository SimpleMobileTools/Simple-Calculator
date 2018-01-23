# Simple Calculator
<img alt="Logo" src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="80">

A calculator with the basic functions and a customizable widget.

You can copy the result or formula to clipboard by long pressing it.

The text color of the widget can be customized, as well as the color and the alpha of the background. Press the result or formula in the widget to open the app.

Contains no ads or unnecessary permissions. It is fully opensource, provides customizable colors.

This app is just one piece of a bigger series of apps. You can find the rest of them at http://www.simplemobiletools.com

<a href='https://play.google.com/store/apps/details?id=com.simplemobiletools.calculator'><img src='http://simplemobiletools.github.io/assets/public/google-play.png' alt='Get it on Google Play' height=45/></a>
<a href='https://f-droid.org/packages/com.simplemobiletools.calculator'><img src='http://simplemobiletools.github.io/assets/public/f-droid.png' alt='Get it on F-Droid' height=45 ></a>

<div style="display:flex;">
<img alt="App image" src="fastlane/metadata/android/en-US/images/phoneScreenshots/app.png" width="30%">
<img alt="App image" src="fastlane/metadata/android/en-US/images/phoneScreenshots/app_2.png" width="30%">
<img alt="App image" src="fastlane/metadata/android/en-US/images/phoneScreenshots/widget.png" width="30%">
</div>

</br>
It contains a couple UI and unit tests, they can be ran with the following instructions.

<h3>Running Espresso UI tests</h3>
<p>1. Run -> Edit Configurations</p>
<p>2. Create a new "Android Instrumentation Tests" configuration, give it a name (i.e. "MainActivityEspressoTest")</p>
<p>3. Choose the "app" module</p>
<p>4. OK</p>
<p>5. Make sure MainActivityEspressoTest is selected near the Run button</p>
<p>6. Run</p>

<h3>Running Robolectric tests</h3>
<p>1. At the Project tab right click the folder containing the tests (i.e. "calculator.simplemobiletools.com.simple_calculator (test)")</p>
<p>2. select Run 'Tests in 'calculator.simplemob...' to run all the tests</p>
<p>3. if you are on Linux or Mac, go to Run -> Edit Configurations, select the new JUnit configuration and change the "Working Directory" item to "$MODULE_DIR$" (without quotes)</p>
<p>4. OK</p>
<p>5. Run</p>

License
-------
    Copyright 2016-present SimpleMobileTools
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
