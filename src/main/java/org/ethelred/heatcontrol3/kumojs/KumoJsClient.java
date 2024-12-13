package org.ethelred.heatcontrol3.kumojs;

import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Retryable
@Client("${kumojs.url}")
public abstract class KumoJsClient {
    public static final List<String> MODES = List.of("off", "heat", "cool");

    @Cacheable("roomlist")
    @Get("/rooms")
    public abstract CompletableFuture<List<String>> getRoomList();

    @Get("/room/{room}/status")
    public abstract CompletableFuture<RoomStatus> getRoomStatus(@PathVariable("room") String room);

    public CompletableFuture<List<NamedRoomStatus>> getRoomStatuses() {
        var roomListFuture = getRoomList();
        return roomListFuture.thenCompose(this::namedRoomStatuses);
    }

    private CompletableFuture<List<NamedRoomStatus>> namedRoomStatuses(List<String> rooms) {
        var statuses = rooms.stream()
                .map(this::namedRoomStatus)
                .toList();
        return CompletableFuture.allOf(statuses.toArray(CompletableFuture[]::new))
                .thenApply(_ -> statuses.stream().map(CompletableFuture::join).toList());

    }

    private CompletableFuture<NamedRoomStatus> namedRoomStatus(String room) {
        return getRoomStatus(room)
                .thenApply(roomStatus -> new NamedRoomStatus(room, roomStatus));
    }


    @Put("/room/{room}/mode/{mode}")
    public abstract CompletableFuture<HttpResponse<?>> setMode(String room, String mode);

    @Put("/room/{room}/{mode}/temp/{temperature}")
    public abstract CompletableFuture<HttpResponse<?>> setTemperature(String room, String mode, int temperature);
}
