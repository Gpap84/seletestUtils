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
import java.io.IOException;
import java.util.Vector;

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

    public void setProcess(Process process){
    }
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

    /**
     * Start appium instance
     * @param host
     * @param port
     * @param json
     * @param platform
     * @return response
     */
    public String startAppiumServer(String host, String port, String json, String platform) {
        try {
            Vector<String> commands=new Vector<String>();
            commands.add("node");
            commands.add("Appium\\node_modules\\appium");
            commands.add("--address");
            commands.add(host);
            commands.add("--port");
            commands.add(port);
            commands.add("--nodeconfig");
            commands.add(new File(json+".json").getAbsolutePath());
            commands.add("--platform-name");
            commands.add(platform);
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.directory(new File("Appium").getAbsoluteFile().getParentFile());
            pb.redirectOutput(new File(new File("Appium").getAbsoluteFile().getParentFile()+"/node_"+json+".txt"));
            pb.start();
            return "Appium server started at port: "+port;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**Destroy node process*/
    public void stopNode(String platform){
        try {
            Vector<String> commands=new Vector<String>();
            if(platform.compareTo("WINDOWS")==0) {
                commands.add("taskkill");
                commands.add("/IM");
                commands.add("node");
                commands.add("/F");
            }else if(platform.compareTo("MAC")==0) {
                commands.add("kill");
                commands.add("-9");
                commands.add("node");
            }
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.start();
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
