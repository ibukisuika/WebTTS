package com.thpiano.tts;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.SynthesisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioInputStream;
import java.util.Set;

/**
 * Created by zouxuan.zx on 2016/2/9.
 */
@Component
public class MaryTTSServer  {
    Logger logger = LoggerFactory.getLogger(MaryTTSServer.class);

    private MaryInterface marytts;

    @PostConstruct
    private void init(){
        try {
            marytts = new LocalMaryInterface();
            // 使用第一个可用的voice作为音源
            Set<String> voices = marytts.getAvailableVoices();
            marytts.setVoice(voices.iterator().next());
        } catch (Exception e){
            logger.error(e.toString(), e);
        }
    }

    public AudioInputStream speakText(String text){
        if(marytts == null){
            throw new RuntimeException("mary tts not inited!");
        }
        try {
            return marytts.generateAudio(text);
        } catch (SynthesisException e) {
            logger.error(e.toString(), e);
        }
        return null;
    }

}
