package com.lb.job.simple.config;

import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_BOOTSTRAP_SERVERS;
import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_INPUT_GROUP_ID;
import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_INPUT_TOPIC_NAME;
import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_PASS;
import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_SASL_MECHANISM;
import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_SECURITY_PROTOCOL;
import static com.lb.job.simple.utils.Environments.Kafka.KAFKA_USER;
import static com.lb.job.simple.utils.Environments.getEnv;

import java.io.Serializable;
import java.util.Properties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class KafkaConfig implements Serializable {
    private final String bootstrapServers;
    private final String topicName;
    private final String groupId;
    private final Properties properties;

    public static KafkaConfig initialize() {
        return new KafkaConfig(
                getEnv(KAFKA_BOOTSTRAP_SERVERS),
                getEnv(KAFKA_INPUT_TOPIC_NAME),
                getEnv(KAFKA_INPUT_GROUP_ID),
                initializeProperties()
        );
    }

    private static Properties initializeProperties() {
        var properties = new Properties();
        var securityProtocol = getEnv(KAFKA_SECURITY_PROTOCOL);
        if (StringUtils.isNotBlank(securityProtocol)) {
            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
            properties.put(SaslConfigs.SASL_MECHANISM, getEnv(KAFKA_SASL_MECHANISM));
            properties.put(SaslConfigs.SASL_JAAS_CONFIG,
                    "org.apache.kafka.common.security.scram.ScramLoginModule required " +
                            "username=\"" + getEnv(KAFKA_USER) + "\" " +
                            "password=\"" + getEnv(KAFKA_PASS) + "\";");
        }
        return properties;
    }
}
