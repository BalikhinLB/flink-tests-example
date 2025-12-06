package com.lb.job.simple.task;

import com.lb.job.simple.config.JobConfig;
import com.lb.job.simple.model.Temperature;
import com.lb.job.simple.sink.SinkIntoExternalSink;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.connector.source.Source;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;

public class Task {

    public void initTask(JobConfig config, StreamExecutionEnvironment env) {
        var source = env.fromSource(
                sourceJobEvent(config),
                WatermarkStrategy.noWatermarks(),
                "Kafka Source"
        );
        source.filter(temperature -> "Moscow".equals(temperature.cityName()))
                .map(String::valueOf)
                .sinkTo(new SinkIntoExternalSink(config));
    }


    private Source<Temperature, ?, ?> sourceJobEvent(JobConfig config) {
        return KafkaSource.<Temperature>builder()
                .setBootstrapServers(config.getKafkaConfig().getBootstrapServers())
                .setTopics(config.getKafkaConfig().getTopicName())
                .setGroupId(config.getKafkaConfig().getGroupId())
                .setProperties(config.getKafkaConfig().getProperties())
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(Temperature.class))
                .build();
    }
}
