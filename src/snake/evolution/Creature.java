package snake.evolution;

/*
// Abstract class that represents beings that
// move on the game grid.
*/
public abstract class Creature extends Entity {
    
    // Position before last update.
    private Coordinate lastPos;
    
    /*
    // Default constructor.
    //
    // @param pos: position of Creature on grid
    */
    public Creature(Coordinate pos) {
        
        super(pos);
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