package org.ethelred.heatcontrol3.sensors;

import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Client("${sensors.url}")
public interface SensorsClient {
    @Cacheable("sensors")
    @Get
    CompletableFuture<List<SensorResult>> getSensorResults();
}
