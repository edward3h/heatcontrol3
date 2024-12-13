@import java.time.OffsetDateTime
@import java.util.List
@import org.ethelred.heatcontrol3.kumojs.NamedRoomStatus
@import org.ethelred.heatcontrol3.openweather.OpenWeatherResult
@import org.ethelred.heatcontrol3.sensors.SensorResult

@param List<NamedRoomStatus> rooms
@param OpenWeatherResult weather
@param List<SensorResult> sensors

!{var now = OffsetDateTime.now();}
<table>
    @for(var room: rooms)
        <tr><td><a href="/room/${room.name()}">${room.name()}</a></td><td>${room.roomTemp().display()}</td><td>${room.mode()}</td><td>${room.setting()}</td></tr>
    @endfor
</table>
<table class="weather"><tr>
    <td>${weather.current().temp().display()}</td>
    <td>$unsafe{weather.minMax()}</td>
    <td>${weather.summary()}</td>
    </tr>
</table>
<table>
    @for(var sensor: sensors)
        <tr><td>${sensor.channel()}</td><td>${sensor.temperature().display()}</td><td>$unsafe{sensor.batteryOk() ? "&#x1F50B;" : "&#x1FAAB;"}</td><td class="ago">${sensor.age(now)}</td></tr>
    @endfor
</table>