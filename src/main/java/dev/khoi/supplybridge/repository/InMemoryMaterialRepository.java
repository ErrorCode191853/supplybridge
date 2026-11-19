package dev.khoi.supplybridge.repository;

import dev.khoi.supplybridge.Material;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMaterialRepository implements MaterialRepository {

    private final Map<String, Material> storage = new ConcurrentHashMap<>();

    @Override
    public void add(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }

        String materialNumber = material.getMaterialNumber();
        if (materialNumber == null || materialNumber.isBlank()) {
            throw new IllegalArgumentException("Material number cannot be null or empty");
        }

        Material existingMaterial = storage.get(materialNumber);
        if (existingMaterial != null) {
            throw new IllegalArgumentException("Material with number " + materialNumber + " already exists");
        }

        storage.put(materialNumber, material);

    }

    @Override
    public Optional<Material> findByNumber(String materialNumber) {

        if (materialNumber == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(materialNumber));

    }

    @Override
    public List<Material> findAll() {

        return List.copyOf(storage.values());

    }

    @Override
    public boolean existByNumber(String materialNumber) {

        if (materialNumber == null) {
            return false;
        }
        return storage.containsKey(materialNumber);

    }

    @Override
    public boolean removeByNumber(String materialNumber) {

        if (materialNumber == null) {
            return false;
        }
        return storage.remove(materialNumber) != null;

    }


}
