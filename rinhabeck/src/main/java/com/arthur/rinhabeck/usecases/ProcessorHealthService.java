package com.arthur.rinhabeck.usecases;

import com.arthur.rinhabeck.dtos.HealthStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
public class ProcessorHealthService {

    public static final String DEFAULT_HEALTH_KEY = "health:processor:default";
    public static final String FALLBACK_HEALTH_KEY = "health:processor:fallback";

    private final RestClient restClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String defaultProcessorUrl;
    private final String fallbackProcessorUrl;

    public ProcessorHealthService(RedisTemplate<String, Object> redisTemplate,
                                  @Value("${payment.processor.default.url}") String defaultProcessorUrl,
                                  @Value("${payment.processor.fallback.url}") String fallbackProcessorUrl) {
        this.redisTemplate = redisTemplate;
        this.restClient = RestClient.create();
        this.defaultProcessorUrl = defaultProcessorUrl;
        this.fallbackProcessorUrl = fallbackProcessorUrl;
    }

    @Scheduled(fixedRate = 5000)
    public void checkAndCacheHealth() {

        try {
            HealthStatus defaultStatus = fetchHealth(defaultProcessorUrl);
            redisTemplate.opsForValue().set(DEFAULT_HEALTH_KEY, defaultStatus);
        } catch (Exception e) {
            redisTemplate.opsForValue().set(DEFAULT_HEALTH_KEY, new HealthStatus(true, 99999));
        }

        try {
            HealthStatus fallbackStatus = fetchHealth(fallbackProcessorUrl);
            redisTemplate.opsForValue().set(FALLBACK_HEALTH_KEY, fallbackStatus);
        } catch (Exception e) {
            redisTemplate.opsForValue().set(FALLBACK_HEALTH_KEY, new HealthStatus(true, 99999));
        }
    }

    private HealthStatus fetchHealth(String baseUrl) {
        return restClient.get()
                .uri(baseUrl + "/payments/service-health")
                .retrieve()
                .body(HealthStatus.class);
    }
}
