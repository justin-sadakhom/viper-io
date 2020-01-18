package snake.evolution;

import javafx.scene.shape.Rectangle;

/*
// An object that can occupy space on the game grid.
*/
public abstract class Entity extends Rectangle {
    
    // Length of one square, in pixels.
    final static int SIDE_LENGTH = 40;
    
    // Current position.
    private Coordinate pos;
    
    /*
    // Default constructor.
    //
    // @param pos: position on grid
    */
    public Entity(Coordinate pos) {
        
        super();
        
        this.pos = pos;

        loadShape();
    }
    
    /*
    // Copies a template Entity.
    //
    // @param template: Entity that constructor copies fields from
    */
    public Entity(Entity template) {
        
        super();
        
        this.pos = template.pos;
        
        loadShape();
    }
    
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