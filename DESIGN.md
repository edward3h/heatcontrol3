# Design Notes

## APIs called

### kumoserver

* Get list of rooms
* Get room data (current temp and settings)
* Update room settings

### openweather

* Get current weather, daily min/max

Cache this: API request limits.

### sensors

Local temperature sensors in the home. Raspberry Pi.

* Get sensor data. Temp, humidity, battery status.

## Additional Data

* Associate sensors with rooms.
* In a room with an associated sensor, the application setting is not the same as the unit setting.

## Cron

Run a scheduled task to check sensors and update the rooms with associated sensors.

## UI

Must fit on mobile phone screen.

Base on old version.

Use JTE templates and HTMX.