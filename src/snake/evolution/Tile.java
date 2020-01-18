/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Tile.java
*/

package snake.evolution;

import javafx.scene.paint.Color;

/*
// Represents a square which makes up the
// game grid. Is a subclass of Entity.
*/
public class Tile extends Entity {
    
    /* FIELDS */
    
    // Whether the Tile has a Creature on it.
    public boolean occupied;
    
    /* CONSTRUCTOR */
    
    // Default constructor.
    public Tile(Coordinate position) {
        
        super(position);
        
        // Sets occupied to default value of false.
        occupied = false;
        
        // Sets colour of Tile to default value of white.
        this.setFill(Color.WHITE);
        
        // TURN ON FOR DEBUG MODE.
        //this.setStroke(Color.BLACK);
    }
    
    public Tile(Tile template) {
        
        super(template);
        
        occupied = false;
        
        this.setFill(Color.WHITE);
        
        // TURN ON FOR DEBUG MODE.
        //this.setStroke(Color.BLACK);
    }
    
    /* METHODS */
    
    // Setter method for occupied.
    public void setOccupied(Boolean state) {
        
        occupied = state;
    }
}