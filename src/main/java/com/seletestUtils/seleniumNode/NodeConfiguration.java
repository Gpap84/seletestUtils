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
package com.seletestUtils.seleniumNode;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nodeConf.WebDriverOptions;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Class for node configuration
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class NodeConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NodeConfiguration.class);

    @Autowired
    Environment env;

    @PostConstruct
    public void startNodeConfiguration() {
        logger.info("{}@{} has been started ...", getClass().getSimpleName(), getClass().hashCode());
        downloadWebDriverExecutables();
    }

    @PreDestroy
    public void stop() {
        logger.info("{}@{} has been stopped ...", getClass().getSimpleName(), getClass().hashCode());
        removeExecutables();
    }

    /**Download IEDriver,ChromeDriver,PhantomJSDriver executables*/
    private void downloadWebDriverExecutables(){
        WebDriverOptions.downloadDriver(new File(env.getProperty("chromedriver.path")),env.getProperty("chromedriver.version"));
        WebDriverOptions.downloadDriver(new File(env.getProperty("iedriver.path")),env.getProperty("iedriver.version"));
        WebDriverOptions.downloadDriver(new File(env.getProperty("phantomjsdriver.path")),env.getProperty("phantomjsdriver.version"));
        WebDriverOptions.downloadDriver(new File(env.getProperty("selenium.path")),env.getProperty("selenium.version"));
    }

    /**Remove IEDriver,ChromeDriver,PhantomJSDriver executables*/
    private void removeExecutables(){
        new File("chromedriver.path").delete();
        new File("iedriver.path").delete();
        new File("phantomjsdriver.path").delete();
        new File("selenium.path").delete();
    }

    /**
     * Register node to grid
     * @param nodeConfig
     * @param hubHost
     * @param hubPort
     */
    public void registerNode(String nodeConfig, String hubHost, String hubPort) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java","-jar",new File(env.getProperty("selenium.path")).getAbsolutePath().substring(new File(env.getProperty("selenium.path")).getAbsolutePath().lastIndexOf("\\")).replace("\\",""),"-role","node","-hub","http://"+hubHost+":"+hubPort+"/grid/register","-nodeConfig",nodeConfig+".json");
            pb.directory(new File(env.getProperty("selenium.path")).getParentFile());
            pb.redirectOutput(new File(new File(env.getProperty("selenium.path")).getParentFile()+"/node.txt"));
            pb.start();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**Return selenium node output*/
    public String getNodeOutPut() {
        try {
            return FileUtils.readFileToString(new File(new File(env.getProperty("selenium.path")).getParentFile()+"/node.txt").getAbsoluteFile());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String stopNode(){
     //TODO
     return null;
    }

}
