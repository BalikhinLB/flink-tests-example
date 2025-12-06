package com.lb.job.simple.config;

import static com.lb.job.simple.utils.Environments.PROFILE;
import static com.lb.job.simple.utils.Environments.PROFILE_LOCAL;
import static com.lb.job.simple.utils.Environments.getEnv;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JobConfig implements Serializable {
    private final KafkaConfig kafkaConfig;
    private final SinkConfig sinkConfig;
    private final boolean local;

    public static JobConfig initialize() {
        return new JobConfig(
                KafkaConfig.initialize(),
                SinkConfig.initialize(),
                PROFILE_LOCAL.equals(getEnv(PROFILE))
        );
    }
}
