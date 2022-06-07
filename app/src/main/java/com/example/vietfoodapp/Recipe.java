package com.example.vietfoodapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Recipe {
    private int id;
    private String name;
    private String cuisineType;
    private String servings;
    private String description;
    private String imageURL;
    private ArrayList<List<String>> recipeIngredient;
    private ArrayList<List<String>> recipeInstruction;
    private String ingredient, instruction;

    // Overloaded constructors.
    public Recipe(){}

    // Constructor used to delete recipes.
    public Recipe(int id, String name, String cuisineType, String servings, String description, String imageURL) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.servings = servings;
        this.description = description;
        this.imageURL = imageURL;
        instruction = "";
        ingredient = "";
    }

    // Constructor used when users add the General Information (AddActivity class).
    public Recipe(String name, String cuisineType, String servings, String description, String imageURL) {
        this.name = name;
        this.cuisineType = cuisineType;
        this.servings = servings;
        this.description = description;
        this.imageURL = imageURL;
        instruction = "";
        ingredient = "";
    }

    // Constructor used when users add the instructions (AddActivity2).
    public Recipe(int id, String name, String cuisineType, String servings, String description, String imageURL,
                  String ingredient, ArrayList<List<String>> instructionList) {
        this.id = id;this.name = name;
        this.cuisineType = cuisineType; this.description = description;
        this.imageURL = imageURL; this.servings = servings;
        this.ingredient = ingredient; this.recipeInstruction=instructionList;
    }

    // Constructor used when users add the ingredients (AddActivity1).
    public Recipe(int id, String name, String cuisineType, String servings,String description, String imageURL,
                  ArrayList<List<String>> ingredientList, String instruction){
        this.id = id; this.name = name;
        this.cuisineType = cuisineType; this.description = description;
        this.imageURL = imageURL; this.servings = servings;
        this.recipeIngredient = ingredientList; this.instruction = instruction;
    }

    // General constructor with all recipe's attributes.
    public Recipe(int id, String name, String cuisineType, String servings, String description, String imageURL,
                  String ingredient, String instruction) {
        this.id = id; this.name = name;
        this.servings = servings; this.cuisineType = cuisineType;
        this.description = description; this.imageURL = imageURL;
        this.ingredient = ingredient; this.instruction = instruction;
    }



    // All the accessors and mutators.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<List<String>> getRecipeIngredient() { return recipeIngredient; }

    public void setRecipeIngredient(ArrayList<List<String>> ingredientList) { this.recipeIngredient = ingredientList; }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public ArrayList<List<String>> getRecipeInstruction() {
        return recipeInstruction;
    }

    public void setRecipeInstruction(ArrayList<List<String>> recipeInstruction) { this.recipeInstruction = recipeInstruction; }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /* The next two methods convert String to List and vice versa. The stringToList() is utilizes a switch-case statement
     * as the dimensions of the ArrayList<List<String>> for the Ingredients and Instructions are different.
     */
    public static ArrayList<List<String>> stringToList(String s, String ingreOrInstruc){
        /* The elements within the list (List<String>) are created by dividing the String passed through the arguments
         * for every ";". The String taken from the database. The tempList is the placeholder for the value to be returned from the method.
         */
        List<String> list = Arrays.asList(s.split("; "));
        ArrayList<List<String>> tempList = new ArrayList<List<String>>();
        switch(ingreOrInstruc){
            case "instruction":
                /* In the database, all instructions are stored in one cell. Each instructions include component of dish, summary of instruction, and detailed versions of instruction (which are
                 * separated by ","). Every instructions is also separated by "," meaning that the String from the database take the format of "Instruc1-Comp; Instruc1-Sum; Instruc1-Det; Instruc2-Comp;
                 * Instruc2-Sum; Instruc3-Det; ..." Therefore, for the loop, the inner list consists of three elements (leading to i+=3 being executed everytime loop occurs).
                 */
                for (int i = 0; i < list.size() - 2; i+=3) {
                    ArrayList<String> innerList = new ArrayList<String>(
                            Arrays.asList(list.get(i), list.get(i+1),list.get(i+2)));
                    tempList.add(innerList);
                }
                break;
            case "ingredient":
                 /* In the database, all ingredients are stored in one cell. Each ingredients include ingredients name, component, amount, and image URL (which are separated by ";"). Every ingredients
                  * is also separated by "," meaning that the String from the database database take the format of "Ingre1-Comp; Ingre1-Name; Ingre1-Amount; Ingre1-URL; Ingre2-Comp; Ingre2-Name;
                  * Ingre2-Amount; Ingre2-URL; ..." Therefore,  for the loop, the inner list consists of four elements (leading to i+=4 being executed everytime loop occurs).
                  */
                for (int i = 0; i < list.size() - 3; i+=4) {
                    ArrayList<String> innerList = new ArrayList<String>(
                            Arrays.asList(list.get(i), list.get(i+1),list.get(i+2),list.get(i+3)));
                    tempList.add(innerList);
                }
                break;
            default:
        }
        return tempList;
    }

    public String listToString(ArrayList<List<String>> list) {
        String allString = "";
        // The double for() loops ensure all elements in the ArrayList<List<String>> are iterated through.
       if(!list.isEmpty()) {
           for (int i = 0; i < list.size(); i++) {
               for (int j = 0; j < list.get(i).size(); j++) {
                   // Check if it is the last element, as "; " is not added.
                   if (i == list.size() - 1 && j == list.get(i).size() - 1)
                       allString += list.get(i).get(j);
                   else allString += list.get(i).get(j) + "; ";
               }
           }
       }
        return allString;
    }


    // Change the instructions and ingredients from ArrayList<List<String>> to String and vice versa.
    public ArrayList<List<String>> setInstructionStringtoArray(){
        recipeInstruction = stringToList(instruction, "instruction");
        return recipeInstruction;
    }

    public ArrayList<List<String>> setIngredientStringtoArray(){
        recipeIngredient = stringToList(ingredient, "ingredient");
        return recipeIngredient;
    }

    public void setInstructionArraytoString(){
        instruction=listToString(recipeInstruction);
    }

    public void setIngredientArraytoString(){
        ingredient=listToString(recipeIngredient);
    }

    // toString() method.
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ingredient=" + ingredient +
                '}';
    }

    /*
     * The following three methods sort the recipes by passing Comparator through the sort method
     * Collections.sort. Collections.sort utilizes a Merge Sort, also known as a Divide and Conquer
     * algorithm. It is a recursive algorithm, and is quicker for larger lists compared to insertion
     * and bubble sort as it does not go through the entire list several times, and has consistent run time.
     * https://getrevising.co.uk/grids/merge-sort-advantages-and-disadvantages
     */
    // Sort by recipe type
    public static Comparator<Recipe> recipeTypeComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r1.getCuisineType().compareTo(r2.getCuisineType());
        }
    };

    // Sort by ascending order
    public static Comparator<Recipe> recipeNameComparatorAZ = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r1.getName().compareTo(r2.getName());
        }
    };

    // Sort by descending order
    public static Comparator<Recipe> recipeNameComparatorZA = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r2.getName().compareTo(r1.getName());
        }
    };

}
