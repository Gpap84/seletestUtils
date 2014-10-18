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
package com.seletestUtils.seleniumNode;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nodeConf.WebDriverOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class AppiumConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NodeConfiguration.class);


    @PostConstruct
    public void startAppiumConfiguration() {
        logger.info("{}@{} has been started ...", getClass().getSimpleName(), getClass().hashCode());
    }

    @PreDestroy
    public void stopAppiumConfiguration() {
        logger.info("{}@{} has been stopped ...", getClass().getSimpleName(), getClass().hashCode());
    }

    /**Downloads Appium server*/
    public void downloadAppium(String url) {
        if(url.contains("Windows")) {
            WebDriverOptions.downloadDriver(new File("Appium").getAbsoluteFile(),"https://bitbucket.org/appium/appium.app/downloads/"+url+".zip");
        }
    }


}
