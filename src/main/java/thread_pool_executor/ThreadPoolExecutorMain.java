package thread_pool_executor;

import lombok.AllArgsConstructor;

import java.util.concurrent.*;

@AllArgsConstructor
public class ThreadPoolExecutorMain {
    private int tasks;

    public void start() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(tasks);

        for (int i = 0; i < tasks; ++i) {
            MainThread newThread = new MainThread();
            threadPoolExecutor.execute(newThread);
        }

        threadPoolExecutor.shutdown();
    }
}
