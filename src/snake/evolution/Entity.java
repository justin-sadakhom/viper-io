/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Entity.java
*/

package snake.evolution;

import javafx.scene.shape.Rectangle;

/*
// Abstract class that represents any object that
// can occupy space on the game grid. Subclass of
// Java FX's Rectangle.
*/
public abstract class Entity extends Rectangle {

    /* CONSTANTS */
    
    // Length of one square, in pixels.
    final static int SIDE_LENGTH = 40;
    
    /* FIELDS */
    
    // Current position.
    private Coordinate pos;
    
    /* CONSTRUCTOR */
    
    /*
    // Default constructor.
    //
    // @param pos - Position of Entity on grid.
    */
    public Entity(Coordinate pos) {
        
        super();
        
        this.pos = pos;
        
        loadShape();
    }
    
    /*
    // Copies a template Entity.
    //
    // @param template - Entity that constructor copies fields from.
    */
    public Entity(Entity template) {
        
        super();
        
        this.pos = template.pos;
        
        loadShape();
    }
    
    /* METHODS */
    
    // Getter for x-coordinate.
    public int x() {
        
        return pos.x();
    }
    
    // Getter for y-coordinate.
    public int y() {
        
        return pos.y();
    }
    
    // Getter for position.
    public Coordinate getPos() {
        
        return pos;
    }
    
    // Setter for position.
    public void setPos(int xPos, int yPos) {
        
        // Invokes setter methods for "pos".
        pos.changeX(xPos);
        pos.changeY(yPos);
                
        // Sets position using new values of pos.x() and pos.y().
        setX(pos.x() * SIDE_LENGTH - 0.5);
        setY(pos.y() * SIDE_LENGTH + 0.5);
    }
    
    // Alternate setter for position.
    public void setPos(Coordinate pos) {
        
        // Sets object's pos to value of inputted pos.
        this.pos = new Coordinate(pos.x(), pos.y());
        
        // Sets position using new values of pos.x() and pos.y().
        setX(pos.x() * SIDE_LENGTH - 0.5);
        setY(pos.y() * SIDE_LENGTH + 0.5);
    }
    
    // Specifies the size and position.
    private void loadShape() {
        
        // Sets position in the program window.
        setX(pos.x() * SIDE_LENGTH - 0.5); // -0.5 ensures no blurriness
        setY(pos.y() * SIDE_LENGTH + 0.5); // +0.5 ensures no blurriness
        
        // Sets dimensions as SIDE_LENGTH.
        setWidth(SIDE_LENGTH);
        setHeight(SIDE_LENGTH);
    }
}