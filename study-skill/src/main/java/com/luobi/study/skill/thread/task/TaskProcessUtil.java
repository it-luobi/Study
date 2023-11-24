package com.luobi.study.skill.thread.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Map;
import java.util.concurrent.*;

// 线程池工具类
public class TaskProcessUtil {

    /**
     * 每个任务，都有自己单独的线程池
     * 对于子任务，需要支持并发，如果每个并发都开一个线程，用完就关闭，对资源消耗太大，所以引入线程池
     */
    private static Map<String, ExecutorService> executors = new ConcurrentHashMap<>();

    /**
     * 初始化一个线程池
     */
    private static ExecutorService init(String poolName, int poolSize) {
        return new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("Pool-" + poolName).setDaemon(false).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 获取线程池
     * 获取线程池可能会存在并发情况，所以需要加一个 synchronized 锁，锁住后，需要对 executorService 进行二次判空校验。
     */
    public static ExecutorService getOrInitExecutors(String poolName, int poolSize) {
        ExecutorService executorService = executors.get(poolName);
        if (null == executorService) {
            synchronized (TaskProcessUtil.class) {
                executorService = executors.get(poolName);
                if (null == executorService) {
                    executorService = init(poolName, poolSize);
                    executors.put(poolName, executorService);
                }
            }
        }
        return executorService;
    }

    /**
     * 回收线程资源
     */
    public static void releaseExecutors(String poolName) {
        ExecutorService executorService = executors.remove(poolName);
        if (executorService != null) {
            executorService.shutdown();
        }
    }

}
