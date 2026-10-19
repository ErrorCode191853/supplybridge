package dev.khoi.supplybridge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaterialTest {

    @Test
    void createsMaterialWithAllFields() {
        Material material = new Material("MAT-001", "Steel bolt", "M8 hex bolt", "Fasteners", UnitOfMeasure.PIECE);

        assertEquals("MAT-001", material.getMaterialNumber());
        assertEquals("Steel bolt", material.getName());
        assertEquals("M8 hex bolt", material.getDescription());
        assertEquals("Fasteners", material.getCategory());
        assertEquals(UnitOfMeasure.PIECE, material.getUnitOfMeasure());
    }

    @Test
    void trimsAllTextFields() {
        Material material = new Material("  MAT-001 ", "\tSteel bolt\n", "  M8 hex bolt  ", " Fasteners ", UnitOfMeasure.PIECE);

        assertEquals("MAT-001", material.getMaterialNumber());
        assertEquals("Steel bolt", material.getName());
        assertEquals("M8 hex bolt", material.getDescription());
        assertEquals("Fasteners", material.getCategory());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void rejectsBlankMaterialNumber(String materialNumber) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Material(materialNumber, "Steel bolt", null, null, UnitOfMeasure.PIECE));

        assertEquals("materialNumber must not be null or blank", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void rejectsBlankName(String name) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Material("MAT-001", name, null, null, UnitOfMeasure.PIECE));

        assertEquals("name must not be null or blank", ex.getMessage());
    }

    @Test
    void rejectsNullUnitOfMeasure() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> new Material("MAT-001", "Steel bolt", null, null, null));

        assertEquals("unitOfMeasure must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void storesBlankDescriptionAsNull(String description) {
        Material material = new Material("MAT-001", "Steel bolt", description, "Fasteners", UnitOfMeasure.PIECE);

        assertNull(material.getDescription());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void storesBlankCategoryAsNull(String category) {
        Material material = new Material("MAT-001", "Steel bolt", "M8 hex bolt", category, UnitOfMeasure.PIECE);

        assertNull(material.getCategory());
    }
}
