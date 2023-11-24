package com.luobi.study.skill.thread.task;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ChildTask {

    private static final int POOL_SIZE = 3;  // 线程池大小
    private static final int SPLIT_SIZE = 4;  // 数据拆分大小
    private final String taskName;

    // 接收jvm关闭信号，实现优雅停机
    protected volatile boolean terminal = false;

    public ChildTask(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 程序执行入口
     * 封装了每个任务执行的流程，当 terminal=true 时，先执行完任务数据，然后回收线程池，最后退出。
     * 永动执行：如果未收到停机命令，任务需要一直执行下去。
     */
    public void doExecute() {
        int i = 1;
        while (true) {
            System.out.println(taskName + ": Cycle-" + i + "-Begin");
            // 获取数据
            List<Cat> datas = queryData();
            // 处理数据
            taskExecute(datas);
            System.out.println(taskName + ":Cycle-" + i + "-End");
            if (terminal) {
                // 只有应用关闭，才会走到这里，用于实现优雅的下线
                break;
            }
            i++;
        }
        // 回收线程池资源
        TaskProcessUtil.releaseExecutors(taskName);
    }

    /**
     * 永动任务优雅停机
     * 当外面通知任务需要停机，需要执行完剩余任务数据，并回收线程资源，再退出任务。
     * 仅用于接受停机命令，这里该变量定义为 volatile，所以多线程内存可见。
     */
    public void terminal() {
        // 关机
        terminal = true;
        System.out.println(taskName + " shut down");
    }

    /**
     * 处理数据
     * 实际应用中需要把 doProcessData 定义为抽象方法，然后由各个任务实现自己的方法。
     */
    private void doProcessData(List<Cat> datas, CountDownLatch latch) {
        try {
            for (Cat cat : datas) {
                System.out.println(taskName + ":" + cat.toString() + ", ThreadName:" + Thread.currentThread().getName());
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    /**
     * 处理单个任务数据，多线程执行任务
     * 需要把数据拆分成 4 份，获取该任务的线程池，并交给线程池并发执行，然后通过 latch.await() 阻塞。
     * 当这 4 份数据都执行成功后，阻塞结束，该方法才返回。
     */
    private void taskExecute(List<Cat> sourceDatas) {
        if (CollectionUtils.isEmpty(sourceDatas)) {
            return;
        }
        // 将数据拆成4份（Lists.partition()：list集合中数据量过大，可根据需要进行拆分，进而通过循环或者多线程来处理数据）
        List<List<Cat>> splitDatas = Lists.partition(sourceDatas, SPLIT_SIZE);
        final CountDownLatch latch = new CountDownLatch(splitDatas.size());

        // 并发处理拆分的数据，共用一个线程池
        for (final List<Cat> datas : splitDatas) {
            ExecutorService executorService = TaskProcessUtil.getOrInitExecutors(taskName, POOL_SIZE);
            executorService.submit(() -> doProcessData(datas, latch));
        }

        try {
            latch.await();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 获取永动任务数据
     * 一般都是扫描DB，此处用queryData()代替
     * 实际应用中需要把 queryData 定为抽象方法，然后由各个任务实现自己的方法。
     */
    private List<Cat> queryData() {
        List<Cat> datas = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            datas.add(new Cat().setCatName("波斯猫" + i));
        }
        return datas;
    }

}
