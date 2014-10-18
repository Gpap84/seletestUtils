/*
This file is part of the SeletestUtils by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.seletestUtils.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seletestUtils.seleniumNode.NodeConfiguration;
import com.seletestUtils.seleniumNode.RecordTestExecution;

/**
 * DriverController class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 */
@RestController
public class WebDriverController {

    @Autowired
    NodeConfiguration node;

    @Autowired
    RecordTestExecution record;

    /**
     * Download executables...
     * @param version
     * @param type
     * @param url
     */
    @RequestMapping(method=RequestMethod.GET, value="/downloads/{version}/{type}/{name:.+}")
    public void downloadExecutables(
            @PathVariable("version") String version,
            @PathVariable("type") String type,
            @PathVariable("name") String url) {
        node.downloadDrivers(version, type, url);
    }

    /**
     * Register a node to Selenium Grid
     * @param nodeConfig
     * @param hubHost
     * @param hubPort
     */
    @RequestMapping(method=RequestMethod.GET, value="/registerNode/{selenium}/{nodeConfig}/{hubHost}/{hubPort:.+}")
    public void registerNode(
            @PathVariable("selenium") String selenium,
            @PathVariable("nodeConfig") String nodeConfig,
            @PathVariable("hubHost") String hubHost,
            @PathVariable("hubPort") String hubPort) {
        node.registerNode(selenium,nodeConfig, hubHost, hubPort);
    }

    /**
     * Get the output of selenium node for debugging
     * @return String the output of node
     */
    @RequestMapping(method=RequestMethod.GET, value="/registerNode/output/{selenium:.+}")
    public String getNodeOutPut(@PathVariable("selenium") String selenium) {
        return node.getNodeOutPut(selenium);
    }

    /**
     * Start screen recording of test execution
     * @param file
     */
    @RequestMapping(method=RequestMethod.GET, value="/screen/record/start/{dir:.+}")
    public void startscreenRecord(@PathVariable("dir") String file) {
        File videoOutPut=new File(file);
        record.startScreenRecording(videoOutPut);
    }

    /**
     * Stop screen recording
     */
    @RequestMapping(method=RequestMethod.GET, value="/screen/record/stop")
    public void stopscreenRecord() {
        record.stopScreenRecording();
    }

    /**
     * Stop selenium node
     */
    @RequestMapping(method=RequestMethod.GET, value="/registerNode/stop")
    public void stopNode() {
        node.stopNode();
    }

}
