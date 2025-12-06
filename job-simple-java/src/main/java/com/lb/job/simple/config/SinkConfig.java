package com.lb.job.simple.config;

import static com.lb.job.simple.utils.Environments.Sink.SINK_MESSAGE_LEVEL;
import static com.lb.job.simple.utils.Environments.getEnv;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SinkConfig implements Serializable {
    private final String messageLevel;

    public static SinkConfig initialize() {
       return new SinkConfig(getEnv(SINK_MESSAGE_LEVEL));
    }
}
