package org.example.synth.core.command.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.synth.core.command.exceptions.QueueOverflowException;
import org.example.synth.core.command.model.AndroidCommand;
import org.example.synth.core.command.model.CommandPriority;
import org.example.synth.core.metrics.AndroidMetricsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandProcessorService {
    private static final int QUEUE_CAPACITY = 100;
    private static final int THREAD_POOL_SIZE = 1;
    private final AndroidMetricsService metricService;

    private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            THREAD_POOL_SIZE,
            THREAD_POOL_SIZE,
            0L,
            TimeUnit.MILLISECONDS,
            taskQueue
    );

    @PostConstruct
    public void init() {
        log.info("CommandProcessor started with thread pool size {}", THREAD_POOL_SIZE);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down CommandProcessor...");
        threadPoolExecutor.shutdownNow();
    }

    public void handleCommand(AndroidCommand command) throws QueueOverflowException {
        if (command.getPriority() == CommandPriority.CRITICAL) {
            log.info("Executing CRITICAL command immediately: {}", command);
            executeNow(command);
        } else {
            log.info("Queuing COMMON command: {}", command);
            try {
                threadPoolExecutor.execute(() -> executeNow(command));
            } catch (RejectedExecutionException e) {
                log.error("Queue is full! Rejected command: {}", command);
                throw new QueueOverflowException("Queue full, rejected command - %s".formatted(command), e);
            }
        }
    }

    private void executeNow(AndroidCommand command) {
        try {
            Thread.sleep(1000);
            log.info("""
                            Start executing command...
                            \tCommand from - {}
                            \tregistered at - {}
                            \tdescription - {}
                            \tpriority - {}
                            End executing command...""",
                    command.getAuthor(),
                    command.getTime(),
                    command.getDescription(),
                    command.getPriority());
            metricService.publishProceedTasksMetric(command.getAuthor());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Command execution interrupted: {}", command);
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void updateQueueSizeMetric() {
        int queueSize = threadPoolExecutor.getQueue().size();
        metricService.publishQueueSizeMetric(queueSize);
    }
}
