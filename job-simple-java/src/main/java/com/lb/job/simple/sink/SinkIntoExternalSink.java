package com.lb.job.simple.sink;

import com.lb.job.simple.config.JobConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.connector.sink2.SinkWriter;

@Slf4j
@RequiredArgsConstructor
public class SinkIntoExternalSink implements Sink<String> {
    private final JobConfig config;

    @SuppressWarnings("deprecation")
    @Override
    public SinkWriter<String> createWriter(InitContext context) {
        return new SinkIntoExternalSinkWriter(config);
    }

    @RequiredArgsConstructor
    static class SinkIntoExternalSinkWriter implements SinkWriter<String> {
        private final ExternalSink externalSink;
        private final JobConfig config;

        public SinkIntoExternalSinkWriter(JobConfig config) {
            this.externalSink = new ExternalSinkImpl();
            this.config = config;
        }

        @Override
        public void write(String element, Context context) {
            externalSink.send(element, config.getSinkConfig().getMessageLevel());
        }

        @Override
        public void flush(boolean endOfInput) { /* no-op */ }

        @Override
        public void close() { /* no-op */ }

    }
}
