SeletestUtils
=============

RESTFul web services for selenium node management

Run mvn package and use created jar as standalone server on the remote machine (selenium node).<br><br>

Endpoints for Node Configuration:<br><br>

* <b>http:/nodeIP:8000/downloads/{version}/{type}/{name}</b> : Downloads webdriver executables<br>
version: the parameter that defines the version of executable<br>
type: the parameter that defines the name of executable<br>
name: the name of executable
* <b>http:/nodeIP:8000/registerNode/{selenium}/{nodeConfig}/{hubHost}/{hubPort}</b> : Register node to Selenium Grid using nodeConfig.json<br> 
selenium: the selenium standalone version to use for registration
nodeConfig: parameter that determines the json file to use for registration<br>
hubHost: parameter that determines the IP address of the hub<br>
hubPort: parameter that determines the port of the hub
* <b>http:/nodeIP:8000/registerNode/output/{selenium}</b> : Returns as String the node output (output of java -jar selenium-standalone.jar)<br>
selenium: the selenium standalone version
* <b>http:/nodeIP:8000/screen/record/start/{dir}</b> : Starting screen recording on remote node (on .avi format)<br>
dir: parameter that determines the directory in which the avi file is saved
* <b>http:/nodeIP:8000/screen/record/stop</b> : stops screen recording on remote node
* <b>http:/nodeIP:8000//registerNode/stop</b> : unregister node from hub

Endpoints for Appium Configuration:<br><br>

* <b>http:/nodeIP:8000/appium/Appium/download/{version}</b> : downloads version of appium from https://bitbucket.org/appium/appium.app/downloads/<br>
version: parameter that defines the version of appium server to download e.g AppiumForWindows-1.2.4.1 

* <b>http://nodeIP:8000/appium/start/{appiumHost}/{appiumPort}/{platform}/{nodeConfig}</b> : starts appium instance from command line using nodeJS.<br>
appiumHost: parameter that determines host of Appium server<br>
appiumPort: parameter that determines port of Appium server<br>
platform: parameter that determines the platform in which Appium server runs (WINDOWS or MAC)<br>
nodeConfig: parameter that determines the nodeConfig.json file that used to register to Grid server<br>

* <b>http:/nodeIP:8000/appium/stop/{platform}</b> :Stops all appium instances<br>
platform: parameter that determines the platform in which Appium server runs (WINDOWS or MAC)




