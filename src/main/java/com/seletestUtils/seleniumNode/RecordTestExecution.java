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

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class RecordTestExecution {

    private static final Logger logger = LoggerFactory.getLogger(RecordTestExecution.class);

    private ExecutorService executor;

    protected ScreenRecorder recorder;

    public void setRecorder(ScreenRecorder recorder){
        this.recorder=recorder;
    }

    @PostConstruct
    public void start() {
        logger.info("{}@{} has been started ...", getClass().getSimpleName(), getClass().hashCode());
    }

    @PreDestroy
    public void stop() {
        logger.info("{}@{} has been stopped ...", getClass().getSimpleName(), getClass().hashCode());
    }

    /**Starts screen recording*/
    public void startScreenRecording(File fileOutput){
        executor = Executors.newFixedThreadPool(1);
        executor.submit(new Record(fileOutput));
    }

    /**Stops screen recording*/
    public void stopScreenRecording(){
        if(executor != null && !executor.isShutdown()) {
            try {
                recorder.stop();
            } catch (IOException e) {
                logger.error("Error after trying to stop recorder: "+e.getMessage());
            }
            executor.shutdownNow();
        }
    }

    /**
     * Record class.
     * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
     *
     */
    class Record implements Runnable {

        private final File file;

        public Record(File fileoutput){
            this.file=fileoutput;
        }

        @Override
        public void run() {

            System.setProperty("java.awt.headless", "false");

            //Create a instance of GraphicsConfiguration to get the Graphics configuration
            //of the Screen.
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

            try{
                //Create a instance of ScreenRecorder with the required configurations
                ScreenRecorder screenRecorder = new ScreenRecorder(gc,
                        null,
                        new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                                QualityKey, 1.0f,
                                KeyFrameIntervalKey, 15 * 60),
                                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
                                        FrameRateKey, Rational.valueOf(30)),
                                        null,
                                        file);

                //Call the start method of ScreenRecorder to begin recording
                screenRecorder.start();
                setRecorder(screenRecorder);

            } catch (Exception e) {
                logger.error("Error after trying to record test: "+e.getMessage());
            }

        }
    }
}
