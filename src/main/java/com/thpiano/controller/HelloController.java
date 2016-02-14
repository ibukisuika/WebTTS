package com.thpiano.controller;

import com.thpiano.tts.MaryTTSServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.spi.AudioFileWriter;
import java.io.*;

@Controller
@RequestMapping("/")
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Resource
    MaryTTSServer maryTTSServer;
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "hello";
	}

    @RequestMapping(value = "/speak", method = RequestMethod.GET)
    public String speak(String word, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("audio/mpeg");
            ServletOutputStream out = response.getOutputStream();
            ByteArrayOutputStream soundByteOutputStream = new ByteArrayOutputStream();
            // TODO:输出格式由wav切换为ogg / mp3
            AudioSystem.write(maryTTSServer.speakText(word), javax.sound.sampled.AudioFileFormat.Type.WAVE, soundByteOutputStream);
            out.write(soundByteOutputStream.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }
        return null;
    }
}