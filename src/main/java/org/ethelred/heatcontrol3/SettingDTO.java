package org.ethelred.heatcontrol3;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity("setting")
public record SettingDTO(@Id String room, int settingFahrenheit, String mode) {
}
