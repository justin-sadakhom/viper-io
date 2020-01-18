/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Apple.java
*/

package snake.evolution;

import javafx.scene.paint.Color;

/*
// Representation of the food that a snake eats.
// Is a subclass of Creature.
*/
public class Apple extends Creature {
    
    /* CONSTRUCTOR */
    
    /*
    // Default constructor.
    //
    // @param pos - Position of Apple on grid.
    */
    public Apple(Coordinate pos) {
        
        super(pos);
        
        // Sets fill colour to deep red.
        this.setFill(Color.rgb(170, 51, 51));
    }
    
    /* METHODS */
    
    // Getter for x-coordinate.
    @Override
    public int x() {
        
        return super.x();
    }
    
    // Getter for y-coordinate.
    @Override
    public int y() {
        
        return super.y();
    }
    
    // Getter for position.
    @Override
    public Coordinate getPos() {
        
        return super.getPos();
    }
    
    // Setter for position.
    @Override
    public void setPos(int xPos, int yPos) {
        
        super.setPos(xPos, yPos);
    }
    
    // Getter for last x-coordinate.
    @Override
    public int lastX() {
        
        return super.lastX();
    }
    
    // Getter for last y-coordinate.
    @Override
    public int lastY() {
        
        return super.lastY();
    }
    
    // Getter for last position.
    @Override
    public Coordinate lastPos() {
        
        return super.lastPos();
    }
    
    // Setter for last position.
    @Override
    public void setLastPos(Coordinate pos) {
        
        super.setLastPos(pos);
    }
}
