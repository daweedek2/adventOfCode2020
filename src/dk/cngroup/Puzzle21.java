package dk.cngroup;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Puzzle21 {

    public static void main(String[] args) throws IOException {
        System.out.println("answer A: " + runA(Utils.getStringWithSingleLineSeparator("21")));
        System.out.println("answer B: " + runB(Utils.getStringWithSingleLineSeparator("21")));
    }

    public static long runA(String input) {
        Map<String, Allergen> allergenMap = getAllergenMap(input);
        List<String> ingredientWithAllergen = determineIngredientWithAllergen(allergenMap);
        // remove ingredients with allergens from below list.

        return Arrays.stream(input.split("( |\n|\\(contains.*)"))
                .filter(ingredient -> !ingredient.equals(""))
                .filter(ingredient -> !ingredientWithAllergen.stream().filter(allergen -> allergen.equals(ingredient)).findAny().isPresent())
                .count();
    }

    private static List<String> determineIngredientWithAllergen(Map<String, Allergen> allergenMap) {
        boolean allFound = false;
        do {
            List<String> allergenIngredients = allergenMap.values().stream()
                    .filter(allergen -> allergen.ingredient != null)
                    .map(allergen -> allergen.ingredient)
                    .collect(Collectors.toList());

            allergenMap.values().stream()
                    .forEach(allergen -> allergen.findIngredient(allergenIngredients));

            allFound = allergenMap.values().stream().allMatch(allergen -> allergen.hasIngredient());

            if(allFound) {
                return allergenMap.values().stream()
                        .filter(allergen -> allergen.ingredient != null)
                        .map(allergen -> allergen.ingredient)
                        .collect(Collectors.toList());
            }

        } while(true);
    }

    private static Map<String, Allergen> getAllergenMap(String input) {
        Map<String, Allergen> allergenMap = new HashMap<>();
        Arrays.stream(input.split("\n"))
                .forEach(s -> {
                    String[] split = s.split("(\\(contains |\\)|, )");
                    IntStream.range(1, split.length)
                            .forEach(i -> {
                                if(allergenMap.containsKey(split[i])) {
                                    allergenMap.get(split[i]).addRecipe(split[0]);
                                } else {
                                    allergenMap.put(split[i], new Allergen(split[i], split[0]));
                                }
                            });

                });
        return allergenMap;
    }

    public static class Allergen {
        String name;
        List<String> recipe = new ArrayList<>();
        String ingredient;
        List<String> possibleIngredients;

        public Allergen(String name, String recipe) {
            this.name = name;
            addRecipe(recipe);
        }

        public void addRecipe(String recipe) {
            this.recipe.add(recipe);
        }

        public boolean hasIngredient() {
            return ingredient != null;
        }

        public boolean findIngredient(List<String> excluding) {
            if(hasIngredient()) {
                return true;
            }
            if(possibleIngredients == null) {
                String base = recipe.get(0);
                // remove the ingredients that contain/are another allergen
                for (int i = 0; i < excluding.size(); i++) {
                    base = base.replace(excluding + " ", "");
                }
                String[] parts = base.split(" ");

                possibleIngredients = Arrays.stream(parts).filter(part ->
                        IntStream.range(1, recipe.size())
                                .allMatch(recipe2 ->
                                        Arrays.stream(recipe.get(recipe2).split(" ")).filter(ingredient -> ingredient.equals(part)).findFirst().isPresent())
                )
                        .collect(Collectors.toList());
            }

            possibleIngredients.removeAll(excluding);

            if(possibleIngredients.size() == 1) {
                ingredient = possibleIngredients.get(0);
                return true;
            }

            return false;

        }
    }

    public static String runB(String input) {
        Map<String, Allergen> allergenMap = getAllergenMap(input);
        determineIngredientWithAllergen(allergenMap);
        return allergenMap.keySet().stream().sorted().map(allergen -> allergenMap.get(allergen).ingredient).collect(Collectors.joining(","));
    }
}
