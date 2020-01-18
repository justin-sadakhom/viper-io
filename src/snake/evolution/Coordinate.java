package snake.evolution;

/*
// Point on a Cartesian coordinate system,
// where values go from 0 to GRID_LENGTH;
*/
public class Coordinate {
    
    private int xPos;
    private int yPos;
    
    /*
    // Default constructor.
    //
    // @param xPos: x value of position on grid
    // @param yPos: y value of position on grid
    */
    public Coordinate(int xPos, int yPos) {
        
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    /*
    // Generates random Coordinates within bounds of
    // game grid. Coordinate will always be at least
    // one Tile away from the edge of the grid to
    // ensure adequate room for the Snake's body.
    */
    public Coordinate() {
        
        xPos = (int)Math.floor(Math.random() * (Game.GRID_LENGTH - 2)) + 1;
        yPos = (int)Math.floor(Math.random() * (Game.GRID_LENGTH - 2)) + 1;
    }

    /* METHODS */
    
    /*
    // Checks if two Coordinates have the same values.
    //
    // @param other: Coordinate being compared to
    //
    // @return true if this has same values as other,
    // false otherwise.
    */
    public boolean equals(Coordinate other) {
        
        return xPos == other.x() && yPos == other.y();
    }
    
    /*
    // Compares Coordinate to <other> to determine
    // which is closer to <reference>.
    //
    // @param other: Coordinate being compared to
    // @param reference: Coordinate used to check distance from
    //
    // @return true if this is closer to reference than other,
    // false otherwise.
    */
    public boolean closer(Coordinate other, Coordinate reference) {
        
        // If y-coordinates are equal, check the x's.
        if (this.y() == other.y())
            return Math.abs(reference.x() - this.x()) < Math.abs(reference.x() - other.x());
        
        // Else x-coordinate equal, so check the y's.
        else
            return Math.abs(reference.y() - this.y()) < Math.abs(reference.y() - other.y());
    }
    
    /*
    // Checks if Coordinate is outside the grid.
    //
    // @return true if either x-coordinate or y-coordinate isn't
    // between 0 to GRID_LENGTH - 1, false otherwise.
    */
    public boolean outOfBounds() {
        
        return x() < 0 || x() > Game.GRID_LENGTH - 1 || y() < 0 || y() > Game.GRID_LENGTH - 1;
    }
    
    /*
    // Converts Coordinate position to the index
    // of its corresponding element in grid[].
    //
    // @return index of Tile with this Coordinate.
    */
    public int toTile() {
        
        return xPos * Game.GRID_LENGTH + yPos;
    }
    
    // Getter for x-coordinate.
    public int x() {
        
        return xPos;
    }

    // Getter for y-coordinate.
    public int y() {
        
        return yPos;
    }
    
    // Setter for both values at same time.
    public void change(int xPos, int yPos) {
        
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    // Setter for just x-coordinate.
    public void changeX(int xPos) {
        
        this.xPos = xPos;
    }
    
    // Setter for just y-coordinate.
    public void changeY(int yPos) {
        
        this.yPos = yPos;
    }
}