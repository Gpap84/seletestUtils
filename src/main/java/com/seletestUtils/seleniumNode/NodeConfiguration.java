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
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nodeConf.WebDriverOptions;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Class for node configuration
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class NodeConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NodeConfiguration.class);

    /**Version of executable*/
    private String version;

    /**Process for java -jar selenium...*/
    private Process process;

    public void setVersion(String version){
        this.version=version;
    }
    public void setProcess(Process process){
        this.process=process;
    }
    public Process getProcess(){
        return process;
    }

    @PostConstruct
    public void startNodeConfiguration() {
        logger.info("{}@{} has been started ...", getClass().getSimpleName(), getClass().hashCode());
    }

    @PreDestroy
    public void stopNodeConfiguration() {
        logger.info("{}@{} has been stopped ...", getClass().getSimpleName(), getClass().hashCode());
        removeExecutables();
    }

    /**Remove IEDriver,ChromeDriver,PhantomJSDriver executables*/
    private void removeExecutables(){
        new File("chromedriver.exe").getAbsoluteFile().delete();
        new File("selenium-server-standalone-"+version+".0.jar").getAbsoluteFile().delete();
        new File("phantomjs-"+version).getAbsoluteFile().delete();
        new File("IEDriverServer.exe").getAbsoluteFile().delete();
    }

    /**Download drivers*/
    public void downloadDrivers(String version, String type, String url) {
        if(type.contains("chrome")) {
            WebDriverOptions.downloadDriver(new File("chromedriver.exe").getAbsoluteFile(),"http://chromedriver.storage.googleapis.com/"+version+"/"+url+".zip");
        } else if(type.contains("selenium")) {
            WebDriverOptions.downloadDriver(new File("selenium-server-standalone-"+version+".0.jar").getAbsoluteFile(),"http://selenium-release.storage.googleapis.com/"+version+"/"+url+".jar");
        } else if(type.contains("phantom")) {
            WebDriverOptions.downloadDriver(new File("phantomjs-"+version).getAbsoluteFile(),"https://bitbucket.org/ariya/phantomjs/downloads/"+url+".zip");
        } else if(type.contains("ie")) {
            WebDriverOptions.downloadDriver(new File("IEDriverServer.exe").getAbsoluteFile(),"http://selenium-release.storage.googleapis.com/"+version+"/IEDriverServer_"+url+".zip");
        } else {
            throw new RuntimeException("The driver is not defined!!!");
        }
    }

    /**
     * Register node to grid
     * @param nodeConfig
     * @param hubHost
     * @param hubPort
     */
    public void registerNode(String selenium, String nodeConfig, String hubHost, String hubPort) {
        try {
            Vector<String> commands=new Vector<String>();
            commands.add("java");
            commands.add("-jar");
            commands.add(new File("selenium-server-standalone-"+selenium+".jar").getAbsolutePath());
            commands.add("-role");
            commands.add("node");
            commands.add("-hub");
            commands.add("http://"+hubHost+":"+hubPort+"/grid/register");
            commands.add("-nodeConfig");
            commands.add(nodeConfig+".json");
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.directory(new File("selenium-server-standalone-"+selenium+".jar").getAbsoluteFile().getParentFile());
            pb.redirectOutput(new File(new File("selenium-server-standalone-"+selenium+".jar").getAbsoluteFile().getParentFile()+"/node.txt"));
            setProcess(pb.start());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**Return selenium node output*/
    public String getNodeOutPut(String selenium) {
        try {
            return FileUtils.readFileToString(new File(new File("selenium-server-standalone-"+selenium+".jar").getAbsoluteFile().getParentFile()+"/node.txt").getAbsoluteFile());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    /**Destroy node process*/
    public void stopNode(){
        getProcess().destroy();
    }

}
