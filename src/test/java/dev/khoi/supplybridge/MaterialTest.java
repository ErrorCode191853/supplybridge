package dev.khoi.supplybridge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void materialsWithSameNumberAreEqualEvenWhenOtherFieldsDiffer() {
        Material a = new Material("MAT-001", "Steel bolt", "M8 hex bolt", "Fasteners", UnitOfMeasure.PIECE);
        Material b = new Material("MAT-001", "Hex bolt", null, "Hardware", UnitOfMeasure.PIECE);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void materialsWithDifferentNumbersAreNotEqual() {
        Material a = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);
        Material b = new Material("MAT-002", "Steel bolt", null, null, UnitOfMeasure.PIECE);

        assertNotEquals(a, b);
    }

    @Test
    void materialNumberIsTrimmedBeforeComparison() {
        Material a = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);
        Material b = new Material("  MAT-001 ", "Steel bolt", null, null, UnitOfMeasure.PIECE);

        assertEquals(a, b);
    }

    @Test
    void renamedMaterialIsStillFoundInHashSet() {
        Material material = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);
        Set<Material> catalog = new HashSet<>();
        catalog.add(material);

        material.rename("Steel hex bolt");

        assertTrue(catalog.contains(material));
        assertTrue(catalog.contains(new Material("MAT-001", "Anything", null, null, UnitOfMeasure.PIECE)));
    }

    @Test
    void renameTrimsNewName() {
        Material material = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);

        material.rename("  Steel hex bolt\t");

        assertEquals("Steel hex bolt", material.getName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void renameRejectsBlankNameAndKeepsOldOne(String newName) {
        Material material = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> material.rename(newName));

        assertEquals("name must not be null or blank", ex.getMessage());
        assertEquals("Steel bolt", material.getName());
    }

    @Test
    void changeDescriptionTrimsNewDescription() {
        Material material = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);

        material.changeDescription("  M8 hex bolt ");

        assertEquals("M8 hex bolt", material.getDescription());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void changeDescriptionStoresBlankAsNull(String newDescription) {
        Material material = new Material("MAT-001", "Steel bolt", "M8 hex bolt", null, UnitOfMeasure.PIECE);

        material.changeDescription(newDescription);

        assertNull(material.getDescription());
    }

    @Test
    void changeCategoryTrimsNewCategory() {
        Material material = new Material("MAT-001", "Steel bolt", null, null, UnitOfMeasure.PIECE);

        material.changeCategory(" Fasteners ");

        assertEquals("Fasteners", material.getCategory());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   \t  "})
    void changeCategoryStoresBlankAsNull(String newCategory) {
        Material material = new Material("MAT-001", "Steel bolt", null, "Fasteners", UnitOfMeasure.PIECE);

        material.changeCategory(newCategory);

        assertNull(material.getCategory());
    }
}
