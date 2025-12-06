package com.lb.job.simple.sink;

public interface ExternalSink {
    void send(String message, String messageLevel);
}
