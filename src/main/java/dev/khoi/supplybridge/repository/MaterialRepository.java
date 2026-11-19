package dev.khoi.supplybridge.repository;

import dev.khoi.supplybridge.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository {

    void add(Material material);

    Optional<Material> findByNumber(String materialNumber);

    List<Material> findAll();

    boolean existByNumber(String materialNumber);

    boolean removeByNumber(String materialNumber);

}
