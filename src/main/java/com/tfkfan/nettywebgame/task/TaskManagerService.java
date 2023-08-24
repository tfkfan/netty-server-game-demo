package com.tfkfan.nettywebgame.task;

import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public interface TaskManagerService {
    void execute(Task task);

    @SuppressWarnings("rawtypes")
    ScheduledFuture schedule(Task task, long delay, TimeUnit unit);

    @SuppressWarnings("rawtypes")
    ScheduledFuture scheduleAtFixedRate(Task task, long initialDelay,
                                        long period, TimeUnit unit);
    @SuppressWarnings("rawtypes")
    ScheduledFuture scheduleWithFixedDelay(Task task,
                                           long initialDelay, long delay, TimeUnit unit);
}
