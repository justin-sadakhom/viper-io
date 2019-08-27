/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Segment.java
*/

package snake.evolution;

import javafx.scene.paint.Color;

/*
// Representation of a unit that makes up
// a Snake. Subclass of Creature.
*/
public class Segment extends Creature {
    
    /* CONSTRUCTORS */
    
    /*
    // Default constructor.
    //
    // @param pos - Position of Segment on grid.
    */
    public Segment(Coordinate pos) {
        
        super(pos);
        
        // Sets fill colour to black.
        setFill(Color.rgb(55, 55, 55));
    }
    
    /* METHODS */
    
    /*
    // Checks if Segment is outside the grid.
    //
    // @return true if either x-coordinate or y-coordinate isn't
    // between 0 to GRID_LENGTH - 1, false otherwise.
    */
    public boolean outOfBounds() {
        
        return getPos().outOfBounds();
    }
    
    public boolean onApple(Apple apple) {
        
        return getPos().equals(apple.getPos());
    }
    
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