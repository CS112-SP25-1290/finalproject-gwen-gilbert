package cs112.finalproject;

/**
 * Thrown when there is an error with retrieving a BoardTile object.
 */
public class BoardTileNotFoundException extends Exception {
    public BoardTileNotFoundException() {
        super();
    }
    
    public BoardTileNotFoundException(String s) {
        super(s);
    }
}
