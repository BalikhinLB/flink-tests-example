package com.lb.job.simple;


import com.lb.job.simple.config.JobConfig;
import com.lb.job.simple.flink.StreamExecutionEnvironmentFactory;
import com.lb.job.simple.task.Task;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) throws Exception {
        run(
                JobConfig.initialize(),
                new StreamExecutionEnvironmentFactory(),
                new Task()
        );
    }

    static void run(
            JobConfig config,
            StreamExecutionEnvironmentFactory envFactory,
            Task task
    ) throws Exception {
        StreamExecutionEnvironment env = envFactory.getEnvironment(config);
        task.initTask(config, env);
        env.execute();
    }
}