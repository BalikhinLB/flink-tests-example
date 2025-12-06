package com.lb.job.simple.flink;

import static com.lb.job.simple.utils.Environments.LocalConfig.CHECKPOINT_DIR;
import static com.lb.job.simple.utils.Environments.LocalConfig.RESTORE_CHECKPOINT_PATH;
import static com.lb.job.simple.utils.Environments.LocalConfig.REST_PORT;
import static com.lb.job.simple.utils.Environments.getEnv;
import static com.lb.job.simple.utils.Environments.getEnvAsInt;

import com.lb.job.simple.config.JobConfig;
import java.nio.file.Paths;
import org.apache.flink.configuration.CheckpointingOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamExecutionEnvironmentFactory {
    public StreamExecutionEnvironment getEnvironment(JobConfig config) {
        StreamExecutionEnvironment env;
        if (config.isLocal()) {
            var conf = new Configuration();
            conf.set(RestOptions.PORT, getEnvAsInt(REST_PORT, 8089));

            String restorePath = getEnv(RESTORE_CHECKPOINT_PATH);
            if (restorePath != null && !restorePath.isEmpty()) {
                SavepointRestoreSettings restore =
                        SavepointRestoreSettings.forPath(restorePath, true);
                SavepointRestoreSettings.toConfiguration(restore, conf);
            }
            conf.set(CheckpointingOptions.CHECKPOINT_STORAGE, "filesystem");
            String cpDir = getEnv(CHECKPOINT_DIR, "tmp/flink-checkpoints");
            var cpAbsPath = Paths.get(cpDir).toAbsolutePath();
            conf.set(CheckpointingOptions.CHECKPOINTS_DIRECTORY, cpAbsPath.toUri().toString());

            env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
            env.setParallelism(1);
            env.enableCheckpointing(60_000);
        } else {
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }
        return env;
    }
}
