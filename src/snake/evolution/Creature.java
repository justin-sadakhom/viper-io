/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Creature.java
*/

package snake.evolution;

/*
// Abstract class that represents beings that
// move on the game grid. Subclass of Entity.
*/
public abstract class Creature extends Entity {

    /* FIELDS */
    
    // Position before last update.
    private Coordinate lastPos;
    
    /* CONSTRUCTOR */
    
    /*
    // Default constructor.
    // Takes Coordinate as parameter.
    */
    
    /*
    // Default constructor.
    //
    // @param pos - Position of Creature on grid.
    */
    public Creature(Coordinate pos) {
        
        super(pos);
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
    public int lastX() {
        
        return lastPos.x();
    }
    
    // Getter for last y-coordinate.
    public int lastY() {
        
        return lastPos.y();
    }
    
    // Getter for last position.
    public Coordinate lastPos() {
        
        return lastPos;
    }
    
    // Setter for last position.
    public void setLastPos (Coordinate pos) {
        
        lastPos = new Coordinate(pos.x(), pos.y());
    }
}