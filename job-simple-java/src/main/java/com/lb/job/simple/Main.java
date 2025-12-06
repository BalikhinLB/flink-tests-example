package com.lb.job.simple;


import com.lb.job.simple.config.JobConfig;
import com.lb.job.simple.flink.StreamExecutionEnvironmentFactory;
import com.lb.job.simple.task.Task;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) throws Exception {
        var config = JobConfig.initialize();
        StreamExecutionEnvironment env = new StreamExecutionEnvironmentFactory().getEnvironment(config);
        new Task().initTask(config, env);
        env.execute();
    }
}