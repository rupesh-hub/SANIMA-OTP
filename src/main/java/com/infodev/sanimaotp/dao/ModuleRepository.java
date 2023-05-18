package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query(nativeQuery = true, value = "select * from module where lower(module_name) = lower(?1)")
    Optional<Module> getByName(String moduleName);

    @Query(nativeQuery = true, value = "select * from module where lower(module_abbr) = lower(?1)")
    Optional<Module> getByNameAbbr(String nameAbbr);

    @Query(nativeQuery = true, value = "select * from modules m where status = 1")
    List<Module> fetchActiveModules();
}
