package com.lb.job.simple.sink;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

@Slf4j
public class ExternalSinkImpl implements ExternalSink {
    @Override
    public void send(String message, String messageLevel) {
        log.makeLoggingEventBuilder(Level.valueOf(messageLevel)).log("Sending message: {}", message);
    }
}
