SeletestUtils
=============

RESTFul web services for selenium node management

*********
How To
*********

Run mvn package and use created jar as standalone server on the remote machine (selenium node).<br><br>

Endpoints offered:<br><br>

* <b>http:/nodeIP:8000/registerNode/{nodeConfig}/{hubHost}/{hubPort}</b> : Register node to Selenium Grid using nodeConfig.json<br> nodeConfig: parameter that determines the json file to use for registration<br>
hubHost: parameter that determines the IP address of the hub<br>
hubPort: parameter that determines the port of the hub
* <b>http:/nodeIP:8000/node/output</b> : Returns as String the node output (output of java -jar selenium-standalone.jar)
* <b>http:/nodeIP:8000/screen/record/start/{file}</b>: Starting screen recording on remote node (on .AVI format)<br>
file: parameter that determines the directory in which the avi file is saved
* <b>http:/nodeIP:8000/screen/record/stop</b>: stops screen recording on remote node




