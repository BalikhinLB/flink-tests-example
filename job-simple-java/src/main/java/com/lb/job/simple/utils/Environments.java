package com.lb.job.simple.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Environments {
    public static final String PROFILE = "profile";
    public static final String PROFILE_LOCAL = "local";

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static class Kafka {
        /**
         * Bootstrap servers для kafka
         */
        public static final String KAFKA_BOOTSTRAP_SERVERS = "KAFKA_BOOTSTRAP_SERVERS";
        /**
         * Имя топика Kafka, из которого читаются сообщения
         */
        public static final String KAFKA_INPUT_TOPIC_NAME = "KAFKA_INPUT_TOPIC_NAME";
        /**
         * groupId для consumer Kafka
         */
        public static final String KAFKA_INPUT_GROUP_ID = "KAFKA_INPUT_GROUP_ID";
        /**
         * Протокол безопасности для подключения к Kafka (например, PLAINTEXT, SSL, SASL_SSL)
         */
        public static final String KAFKA_SECURITY_PROTOCOL = "KAFKA_SECURITY_PROTOCOL";
        /**
         * SASL mechanism для подключения к Kafka (например, PLAIN, SCRAM-SHA-256, SCRAM-SHA-512)
         */
        public static final String KAFKA_SASL_MECHANISM = "KAFKA_SASL_MECHANISM";
        /**
         * Имя пользователя для аутентификации в Kafka при использовании {@link #KAFKA_SECURITY_PROTOCOL}
         * отличном от PLAINTEXT
         */
        public static final String KAFKA_USER = "KAFKA_USER";
        /**
         * Пароль для аутентификации в Kafka при использовании {@link #KAFKA_SECURITY_PROTOCOL}
         */
        public static final String KAFKA_PASS = "KAFKA_PASS";
    }

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static class Sink {
        /**
         * Уровень сообщений для отправки во внешний сервис
         */
        public static final String SINK_MESSAGE_LEVEL = "SINK_MESSAGE_LEVEL";
    }


    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static class LocalConfig {
        /**
         * Порт для UI flink кластера
         */
        public static final String REST_PORT = "REST_PORT";
        /**
         * Путь к ранее сохраненному чекпоинту, с которого нужно осуществить запуск
         */
        public static final String RESTORE_CHECKPOINT_PATH = "RESTORE_CHECKPOINT_PATH";
        /**
         * Место сохранение чекпоинтов (на hdd)
         */
        public static final String CHECKPOINT_DIR = "CHECKPOINT_DIR";
    }


    public static String getEnv(String key) {
        return System.getenv().get(key);
    }

    public static String getEnv(String key, String defaultValue) {
        return System.getenv().getOrDefault(key, defaultValue);
    }

    public static int getEnvAsInt(String key, int defaultValue) {
        return Integer.parseInt(getEnv(key, String.valueOf(defaultValue)));
    }
}
