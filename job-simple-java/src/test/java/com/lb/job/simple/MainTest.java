package com.lb.job.simple;

import com.lb.job.simple.config.JobConfig;
import com.lb.job.simple.flink.StreamExecutionEnvironmentFactory;
import com.lb.job.simple.task.Task;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MainTest {
    @Test
    void main_shouldCallRun() throws Exception {
        try (MockedStatic<Main> mainMock = mockStatic(Main.class, CALLS_REAL_METHODS)) {

            mainMock
                    .when(() -> Main.run(any(), any(), any()))
                    .thenAnswer(invocation -> null);

            Main.main(new String[0]);

            mainMock.verify(() ->
                    Main.run(
                            any(JobConfig.class),
                            any(StreamExecutionEnvironmentFactory.class),
                            any(Task.class)
                    )
            );
        }
    }

    @Test
    void run_shouldInitializeAndExecuteFlinkJob() throws Exception {
        JobConfig config = mock(JobConfig.class);
        StreamExecutionEnvironmentFactory envFactory = mock(StreamExecutionEnvironmentFactory.class);
        Task task = mock(Task.class);
        StreamExecutionEnvironment env = mock(StreamExecutionEnvironment.class);

        when(envFactory.getEnvironment(config)).thenReturn(env);

        Main.run(config, envFactory, task);

        verify(envFactory).getEnvironment(config);
        verify(task).initTask(config, env);
        verify(env).execute();
        verifyNoMoreInteractions(envFactory, task, env);
    }

}