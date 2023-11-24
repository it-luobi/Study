package com.luobi.study.skill.thread.task;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明
 * 做这个多线程异步任务，主要是因为我们有很多永动的异步任务，什么是永动呢？就是任务跑起来后，需要一直跑下去。
 * <p>
 * 比如消息 Push 任务，因为一直有消息过来，所以需要一直去消费 DB 中的未推送消息，就需要整一个 Push 的永动异步任务。
 * <p>
 * 简单总结一下需求：
 * <p>
 * 1.能同时执行多个永动的异步任务；
 * 2.每个异步任务，支持开多个线程去消费这个任务的数据；
 * 3.支持永动异步任务的优雅关闭，即关闭后，需要把所有的数据消费完毕后，再关闭。
 * <p>
 * 完成上面的需求，需要注意几个点：
 * <p>
 * 1.每个永动任务，可以开一个线程去执行；
 * 2.每个子任务，因为需要支持并发，需要用线程池控制；
 * 3.永动任务的关闭，需要通知子任务的并发线程，并支持永动任务和并发子任务的优雅关闭。
 */
public class LoopTask {

    private List<ChildTask> childTasks;

    /**
     * 每个任务都开一个单独的 Thread
     * 这里我初始化了 2 个永动任务，分别为 childTask1 和 childTask2，然后分别执行
     * 后面 Sleep 了 5 秒后，再关闭任务
     * 最后分析看看是否可以按照预期优雅退出。
     */
    public static void main(String[] args) throws Exception {
        LoopTask loopTask = new LoopTask();
        loopTask.initLoopTask();
        Thread.sleep(5000L);
        loopTask.shutdownLoopTask();
    }

    /**
     * 输出数据：
     * “Pool-childTask” 是线程池名称；
     * “childTask” 是任务名称；
     * “Cat(catName=波斯猫)” 是执行的结果；
     * “childTask shut down” 是关闭标记；
     * “childTask: Cycle-X-Begin” 和 “childTask: Cycle-X-End” 是每一轮循环的开始和结束标记。
     * <p>
     * 结果分析：
     * childTask1 和 childTask2 分别执行
     * 在第一轮循环中都正常输出了 5 条波斯猫数据；
     * 在第二轮执行过程中，启动了关闭指令，这次第二轮执行没有直接停止，而是先执行完任务中的数据，再执行退出，所以完全符合优雅退出结论。
     */

    public void initLoopTask() {
        childTasks = new ArrayList<>();
        childTasks.add(new ChildTask("childTask1"));
        childTasks.add(new ChildTask("childTask2"));
        for (final ChildTask childTask : childTasks) {
            new Thread(childTask::doExecute).start();
        }
    }

    public void shutdownLoopTask() {
        if (!CollectionUtils.isEmpty(childTasks)) {
            for (ChildTask childTask : childTasks) {
                childTask.terminal();
            }
        }
    }

}
