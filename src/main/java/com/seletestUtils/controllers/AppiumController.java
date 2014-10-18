/*
This file is part of the Seletest by Giannis Papadakis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Giannis Papadakis <gpapadakis84@gmail.com>
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seletestUtils.seleniumNode.AppiumConfiguration;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@RestController
public class AppiumController {

    @Autowired
    AppiumConfiguration appium;

    /**
     * Download appium server
     * @param url
     */
    @RequestMapping(method=RequestMethod.GET, value="/appium/download/{version:.+}")
    public void downloadAppium(@PathVariable("version") String url) {
        appium.downloadAppium(url);
    }

    /**
     * Start appium instance
     * @param host
     * @param port
     * @param platform
     * @param json
     * @return response
     */
    @RequestMapping(method=RequestMethod.GET, value="/appium/start/{appiumHost}/{appiumPort}/{platform}/{nodeConfig}")
    public String startAppiumServer(
            @PathVariable("appiumHost") String host,
            @PathVariable("appiumPort") String port,
            @PathVariable("platform") String platform,
            @PathVariable("nodeConfig") String json) {
        return appium.startAppiumServer(host, port, json, platform);
    }

    /**
     * Stop appium
     */
    @RequestMapping(method=RequestMethod.GET, value="/appium/stop/{platform}")
    public void stopNode(@PathVariable("platform") String platform) {
        appium.stopNode(platform);
    }
}
