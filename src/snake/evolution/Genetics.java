package snake.evolution;

import java.util.ArrayList;

/*
// Contains variables and methods that
// handle collections of Snakes and their
// DNA (genetics).
*/
public class Genetics {

    // No. of inputs for neural net to process.
    public static final int INPUT_SIZE = 6;
    
    // No. of Snakes in a generation.
    public static final int GEN_SIZE = 45; // make multiple of 3, min 12
    
    // Alive snakes.
    public static ArrayList<Snake> generation;
    
    // Dead snakes.
    public static ArrayList<Snake> dead = new ArrayList<>();

    /*
    // Insertion sort algorithm.
    //
    // @param gen: unsorted ArrayList of Snakes
    //
    // @return Sorted ArrayList of Snakes.
    */
    public static ArrayList<Snake> sort(ArrayList<Snake> gen) {
        
        for (int i = 1; i < gen.size(); i++) {
            
            Snake base = gen.get(i);
            int j = i - 1;
            
            while (j >= 0 && gen.get(j).fitness() > base.fitness()) {
                
                gen.set(j + 1, gen.get(j));
                j -= 1;
            }
            
            gen.set(j + 1, base);
        }
        
        return gen;
    }
    
    /*
    // Find the index of largest element in an array.
    //
    // @param input[]: array that is being searched
    //
    // @return Index of largest element. -1 if error.
    */
    public static int largest(float input[]) {
        
        float largest = 0;
        
        // Iterate through each element.
        // If element is larger than "largest",
        // Set largest to that element.
        for (int i = 0; i < input.length; i++)
            if (Float.compare(input[i], largest) > 0)
                largest = input[i];
        
        // Iterate through each element to
        // find index of element that matches
        // largest.
        for (int i = 0; i < input.length; i++)
            if (Float.compare(input[i], largest) == 0)
                return i;
        
        return -1;
    }
    
    /*
    // Use recursion to empty a list of elements.
    //
    // @param list: ArrayList being emptied
    //
    // @return Cleaned ArrayList.
    */
    public static ArrayList<Snake> clean(ArrayList<Snake> list) {
        
        // If list still has elements...
        if (!list.isEmpty()) {
            
            // Remove element at first index.
            list.remove(0);
            
            // Recursively iterate through ArrayList until empty.
            Genetics.clean(list);
        }
        
        return list;
    }
}