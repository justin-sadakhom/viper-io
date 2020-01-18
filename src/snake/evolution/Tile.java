package snake.evolution;

import javafx.scene.paint.Color;

/*
// Square which makes up the game grid.
*/
public class Tile extends Entity {
    
    // Whether the Tile has a Creature on it.
    public boolean occupied;
    
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
    
    // Setter method for occupied.
    public void setOccupied(Boolean state) {
        
        occupied = state;
    }
}