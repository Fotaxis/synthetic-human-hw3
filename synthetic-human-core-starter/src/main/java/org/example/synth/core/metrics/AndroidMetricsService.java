package org.example.synth.core.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class AndroidMetricsService {

    private final MeterRegistry meterRegistry;
    private final Map<String, Counter> counterMap = new HashMap<>();
    private final AtomicLong currentAndroidBusynessConnectedValue = new AtomicLong();

    @PostConstruct
    public void registerMetrics() {
        Gauge.builder("current_android_busyness", currentAndroidBusynessConnectedValue::get)
                .description("current android task queue size")
                .register(meterRegistry);
    }

    public void publishQueueSizeMetric(int queueSize) {
        currentAndroidBusynessConnectedValue.set(queueSize);
    }

    public void publishProceedTasksMetric(String author) {
        counterMap.computeIfAbsent(author, a ->
                        Counter.builder("current_android_proceed_tasks")
                                .description("current android proceed tasks by author")
                                .tag("author", a)
                                .register(meterRegistry))
                .increment();
    }
}
