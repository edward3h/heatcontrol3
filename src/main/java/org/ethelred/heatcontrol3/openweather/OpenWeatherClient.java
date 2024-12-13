package org.ethelred.heatcontrol3.openweather;

import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.concurrent.CompletableFuture;

@Client("${openweather.url}")
public interface OpenWeatherClient {

    @Cacheable("openweather")
    @Get
    CompletableFuture<OpenWeatherResult> getWeather();
}
