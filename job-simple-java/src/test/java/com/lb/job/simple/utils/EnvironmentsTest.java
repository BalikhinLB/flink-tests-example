package com.lb.job.simple.utils;

import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnvironmentsTest {

    @Test
    void getEnv_shouldReturnValue_whenEnvExists() throws Exception {
        withEnvironmentVariable("TEST_ENV", "value")
                .execute(() -> {
                    String result = Environments.getEnv("TEST_ENV");
                    assertThat(result).isEqualTo("value");
                });
    }

    @Test
    void getEnvWithDefault_shouldReturnDefault_whenEnvDoesNotExist() {
        String result = Environments.getEnv("UNKNOWN_ENV", "default");
        assertThat(result).isEqualTo("default");
    }

    @Test
    void getEnvWithDefault_shouldReturnEnvValue_whenExists() throws Exception {
        withEnvironmentVariable("TEST_ENV", "value")
                .execute(() -> {
                    String result = Environments.getEnv("TEST_ENV", "default");
                    assertThat(result).isEqualTo("value");
                });
    }

    @Test
    void getEnvAsInt_shouldReturnIntValue_whenEnvExists() throws Exception {
        withEnvironmentVariable("INT_ENV", "8081")
                .execute(() -> {
                    int result = Environments.getEnvAsInt("INT_ENV", 80);
                    assertThat(result).isEqualTo(8081);
                });
    }

    @Test
    void getEnvAsInt_shouldReturnDefault_whenEnvDoesNotExist() {
        int result = Environments.getEnvAsInt("UNKNOWN_INT_ENV", 80);
        assertThat(result).isEqualTo(80);
    }

    @Test
    void getEnvAsInt_shouldThrowException_whenEnvIsNotNumber() throws Exception {
        withEnvironmentVariable("INT_ENV", "NaN")
                .execute(() ->
                        assertThatThrownBy(() ->
                                Environments.getEnvAsInt("INT_ENV", 80)
                        ).isInstanceOf(NumberFormatException.class)
                );
    }
}