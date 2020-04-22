package com.acme.elvl.repository;

import com.acme.elvl.model.entity.EnergyLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyLevelRepository extends CrudRepository<EnergyLevel, String> {

}
