package org.ethelred.heatcontrol3;

import gg.jte.Content;
import gg.jte.output.WriterOutput;
import io.micronaut.cache.annotation.CacheInvalidate;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.io.Writable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.exceptions.NotFoundException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import org.ethelred.heatcontrol3.kumojs.KumoJsClient;
import org.ethelred.heatcontrol3.kumojs.NamedRoomStatus;
import org.ethelred.heatcontrol3.openweather.OpenWeatherClient;
import org.ethelred.heatcontrol3.sensors.SensorsClient;
import org.ethelred.heatcontrol3.template.Templates;
import gg.jte.models.runtime.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


@Controller(produces = "text/html; charset=utf-8")
//@ExecuteOn(TaskExecutors.BLOCKING)
public class UIController {
    private final Templates templates;
    private final KumoJsClient kumoJsClient;
    private final OpenWeatherClient openWeatherClient;
    private final SensorsClient sensorsClient;

    public UIController(Templates templates, KumoJsClient kumoJsClient, OpenWeatherClient openWeatherClient, SensorsClient sensorsClient) {
        this.templates = templates;
        this.kumoJsClient = kumoJsClient;
        this.openWeatherClient = openWeatherClient;
        this.sensorsClient = sensorsClient;
    }

    @Get
    public Writable index(@Header(name = "hx-request", defaultValue = "false") boolean htmx) {
        var rooms = kumoJsClient.getRoomStatuses();
        var weather = openWeatherClient.getWeather();
        var sensors = sensorsClient.getSensorResults();
        return withLayout(htmx, "", "index", templates.index(rooms.join(), weather.join(), sensors.join())
        );
    }

    @Get("/room/{room}")
    public Writable room(String room, @Header(name = "hx-request", defaultValue = "false") boolean htmx) {
        var roomList = kumoJsClient.getRoomList().join();
        if (roomList.contains(room)) {
            var status = kumoJsClient.getRoomStatus(room);
            return withLayout(htmx, room, "room",
                            templates.room(KumoJsClient.MODES, new NamedRoomStatus(room, status.join()))
            );

        }
        throw new NotFoundException();
    }

    @Post(uri = "/room/{room}", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse updateRoom(String room, String mode, @Nullable String setting) {

        var roomList = kumoJsClient.getRoomList().join();
        if (roomList.contains(room) && KumoJsClient.MODES.contains(mode)) {
            var current = kumoJsClient.getRoomStatus(room).join();
            var futures = new ArrayList<CompletableFuture<?>>();
            if (!mode.equalsIgnoreCase(current.mode())) {
                futures.add(kumoJsClient.setMode(room, mode));
            }
            if ("plus".equalsIgnoreCase(setting)) {
                futures.add(kumoJsClient.setTemperature(room, mode, current.sp() + 1));
            } else if ("minus".equalsIgnoreCase(setting)) {
                futures.add(kumoJsClient.setTemperature(room, mode, current.sp() - 1));
            }
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
            return HttpResponse.seeOther(URI.create("/room/" + room));
        }

        throw new NotFoundException();
    }

    Writable withLayout(boolean htmx, String title, String rootClass, JteModel content){
        if (htmx) {
            return writable(content);
        } else {
            return writable(templates.layout(title, rootClass, content));
        }
    }


    static Writable writable(JteModel model) {
        return writer -> model.render(new WriterOutput(writer));
    }

}
