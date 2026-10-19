package dev.khoi.supplybridge;


public class Main {
    public static void main(String[] args) {
        Material aluminiumSheet2mm = new Material("MAT-0001", "Aluminium Sheet 2mm", null, "Raw Material", UnitOfMeasure.PIECE);
        System.out.println(aluminiumSheet2mm);

        // Blank material number on purpose: shows the constructor rejecting invalid input.
        try {
            Material steelPowder = new Material(
                    "  ", "Steel Powder",
                    null, "Raw Material",
                    UnitOfMeasure.KILOGRAM);

            System.out.println(steelPowder);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }
}