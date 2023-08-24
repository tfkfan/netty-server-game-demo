package com.tfkfan.nettywebgame.task;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleTaskManagerService extends NioEventLoopGroup implements TaskManagerService {
    private final AtomicLong taskNum;

    public SimpleTaskManagerService(int corePoolSize, Executor executor) {
        super(corePoolSize, executor);
        taskNum = new AtomicLong(0);
    }
    @Override
    public void execute(Task task) {
        taskNum.incrementAndGet();
        super.execute(task);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ScheduledFuture schedule(final Task task, long delay, TimeUnit unit) {
        taskNum.incrementAndGet();
        return super.schedule(task, delay, unit);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ScheduledFuture scheduleAtFixedRate(Task task, long initialDelay,
                                               long period, TimeUnit unit) {
        taskNum.incrementAndGet();
        return super.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ScheduledFuture scheduleWithFixedDelay(Task task,
                                                  long initialDelay, long delay, TimeUnit unit) {
        taskNum.incrementAndGet();
        return super.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }
}
