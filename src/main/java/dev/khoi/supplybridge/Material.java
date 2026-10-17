package dev.khoi.supplybridge;

import java.util.Objects;

public class Material {

    private final String materialNumber;

    private String name;
    private String description;
    private String category;

    private UnitOfMeasure unitOfMeasure;

    public Material(String materialNumber, String name, String description, String category, UnitOfMeasure unitOfMeasure) {
        this.name = requireNonBlank(name, "name");
        this.materialNumber = requireNonBlank(materialNumber, "materialNumber");
        this.description = normalizeOptional(description);
        this.category = normalizeOptional(category);
        this.unitOfMeasure = Objects.requireNonNull(unitOfMeasure, "unitOfMeasure must not be null");
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    @Override
    public String toString() {
        return "Material " +
                "materialNumber='" + materialNumber + '\'' +
                ", name='" + name + '\'' +
                ", description=" + (description == null ? "null" : "'" + description + "'") +
                ", category=" + (category == null ? "null" : "'" + category + "'") +
                ", unitOfMeasure=" + unitOfMeasure;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank");
        }
        return value.strip();
    }

    // Optional text fields: blank input is stored as null, so callers only need one "absent" check.
    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.strip();
    }
}
