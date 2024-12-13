package org.ethelred.heatcontrol3;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface SettingRepository extends CrudRepository<SettingDTO, String> {
    Optional<SettingDTO> findByRoom(String room);
}
