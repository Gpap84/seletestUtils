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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nodeConf.WebDriverOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Class for node configuration
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class NodeConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NodeConfiguration.class);

    @PostConstruct
    public void startNodeConfiguration() {
        logger.info("{}@{} has been started ...", getClass().getSimpleName(), getClass().hashCode());
    }

    @PreDestroy
    public void stop() {
        logger.info("{}@{} has been stopped ...", getClass().getSimpleName(), getClass().hashCode());
    }

    /***************************************
     * Driver Properties********************
     ***************************************
     */
    @Value("${chromedriver.path}")
    private String chromeDriverPath;

    @Value("${iedriver.path}")
    private String ieDriverPath;

    @Value("${phantomjsdriver.path}")
    private String phantomJSDriverPath;

    @Value("${selenium.path}")
    private String seleniumPath;

    @Value("${chromedriver.version}")
    private String chromedriverVersion;

    @Value("${iedriver.version}")
    private String iedriverVersion;

    @Value("${phantomjsdriver.version}")
    private String phantomJsdriverVersion;

    @Value("${selenium.version}")
    private String seleniumVersion;

    /**
     * Enum class for downloading and return WebDriver version on remote machine
     * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
     */
    public enum DriverType{
        IE("ie"){
            @Override
            public String downloadDriver(String file, String Url) {
                File ieDriverExecutable=new File(Url);
                WebDriverOptions.downloadDriver(ieDriverExecutable,file);
                return "IEDriver version on remote node: "+file;
            }
        },
        CHROME("chrome"){
            @Override
            public String downloadDriver(String file, String Url) {
                File chromeDriverExecutable=new File(Url);
                WebDriverOptions.downloadDriver(chromeDriverExecutable,file);
                return "ChromeDriver version on remote node: "+Url;
            }
        },
        SELENIUM("selenium"){
            @Override
            public String downloadDriver(String file, String Url) {
                File chromeDriverExecutable=new File(Url);
                WebDriverOptions.downloadDriver(chromeDriverExecutable,file);
                return "Selenium standalone version on remote node: "+Url;
            }
        },
        PHANTOMJS("phantomJS"){
            @Override
            public String downloadDriver(String file, String Url) {
                File phantomDriverExecutable=new File(Url);
                WebDriverOptions.downloadDriver(phantomDriverExecutable,file);
                return "PhantomJsDriver version on remote node: "+Url;
            }
        };
        private final String driverType;

        DriverType(String driver) {
            driverType = driver;
        }

        public String get() {
            return driverType;
        }

        public abstract String downloadDriver(String path, String file);

    }

    /**
     * This method called from Rest Client to download driver to remote machine
     * @param type
     */
    public String downloadWebDriver(String type){
        String localFile="";
        String Url="";
        if(type.contains("chrome")){
            Url=chromedriverVersion;
            localFile=chromeDriverPath;
        } else if(type.contains("ie")){
            Url=iedriverVersion;
            localFile=ieDriverPath;
        } else if(type.contains("phantom")){
            Url=phantomJsdriverVersion;
            localFile=phantomJSDriverPath;
        } else if(type.contains("selenium")){
            Url=seleniumVersion;
            localFile=seleniumPath;
        }
        return findByDriverType(type).downloadDriver(localFile,Url);
    }

    /**
     *Finds enum constant
     * @param type
     * @return DriverType
     */
    static synchronized public DriverType findByDriverType(String type) {
        if (type != null) {
            for (DriverType driver : DriverType.values()) {
                if (type.startsWith(driver.get())) {
                    return driver;
                }
            }
        }
        return null;
    }

}
