package cs112.finalproject.builders;

import cs112.finalproject.SceneUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class TicTacToeGame extends BoardGameBuilder {
    private boolean lineCompleted;
    private boolean playerIsX;
    private Image TILE_X;
    private Image TILE_O;
    public TicTacToeGame(int columns, int rows) {
        super(columns, rows);
        DEFAULT_TILE_IMAGE = new Image(SceneUtils.TIC_TAC_TOE_EMPTY);
        TILE_X = new Image(SceneUtils.TIC_TAC_TOE_X);
        TILE_O = new Image(SceneUtils.TIC_TAC_TOE_O);
        headerImage = new Image(SceneUtils.TIC_TAC_TOE_LOGO_PATH);
    }

    public TicTacToeGame() {
        this(3, 3);
    }

    @Override
    public void startGame() {
        playerIsX = playerTurn;
        lineCompleted = false;
        super.startGame();
    }

    @Override
    protected ArrayList<BoardTile> getValidSelectableTiles() {
        ArrayList<BoardTile> selectableTiles = super.getValidSelectableTiles();
        // random chance to play smart
//        if (Rand.nextBoolean()) {
//
//        }
        return selectableTiles;
    }

    @Override
    protected boolean gameHasEnded() { return lineCompleted; }
    @Override
    protected void onTileSelected(BoardTile tile) {
        tile.getTile().setGraphic(SceneUtils.newImageView(playerTurn ? (playerIsX? TILE_X : TILE_O) : (playerIsX? TILE_O : TILE_X)));
        tile.getTile().setDisable(true);
        tile.setTileValue(playerTurn ? 1 : 2);
        checkIfGameEnded(tile);
        super.onTileSelected(tile);
    }

    @Override
    public Region buildBoardSizeRegion(Region parent, boolean forceSquare) {
        return super.buildBoardSizeRegion(parent, true);
    }
    private void checkIfGameEnded(BoardTile selectedTile) {
        if (lineCompleted) {
            return;
        }

        int column = selectedTile.getColumn();
        int row = selectedTile.getRow();
        int valToCheck = playerTurn ? 1 : 2;

        boolean firstOrLastColumn = (column == 0 || column == getNumColumns() - 1);
        boolean firstOrLastRow = (row == 0 || row == getNumRows() - 1);

        if (firstOrLastColumn && firstOrLastRow) {
            System.out.println("Corner Tile");
            lineCompleted = verifyCornerTile(valToCheck, column, row);
        }
        else if (firstOrLastColumn || firstOrLastRow) {
            System.out.println("Edge Tile");
            lineCompleted = verifyEdgeTile(valToCheck, column, row);
        }
        else {
            System.out.println("Body Tile");
            lineCompleted = verifyBodyTile(valToCheck, column, row);
        }
    }

    /**
     * Checks the horizontal and vertical lines that intersect at the current BoardTile.
     * Performs a maximum of 2 checks.
     * @param val The tileValue all checked BoardTiles must have to be considered part of a valid line.
     * @param col The column of the current BoardTile.
     * @param row The row of the current BoardTile.
     * @return True if the current BoardTile is part of a valid vertical or horizontal line.
     */
    private boolean verifyEdgeTile(int val, int col, int row) {
        if (verifyStraight(true, val, row)) {
            return true;
        }
        return verifyStraight(false, val, col);
    }

    /**
     * Checks the horizontal, vertical and diagonal lines that intersect at the current BoardTile.
     * Performs a maximum of 3 checks.
     * @param val The tileValue all checked BoardTiles must have to be considered part of a valid line.
     * @param col The column of the current BoardTile.
     * @param row The row of the current BoardTile.
     * @return True if the current BoardTile is part of a valid line.
     */
    private boolean verifyCornerTile(int val, int col, int row) {
        if (verifyEdgeTile(val, col, row)) {
            return true;
        }

        BoardTile tile = getBoardTile(col, row);
        return verifyDiagonal(col == row, val, tile);
    }
    /**
     * Checks the horizontal, vertical and diagonal lines that intersect at the current BoardTile.
     * Performs a maximum of 4 checks.
     * @param val The tileValue all checked BoardTiles must have to be considered part of a valid line.
     * @param col The column of the current BoardTile.
     * @param row The row of the current BoardTile.
     * @return True if the current BoardTile is part of a valid line.
     */
    private boolean verifyBodyTile(int val, int col, int row) {
        if (verifyEdgeTile(val, col, row)) {
            return true;
        }

        BoardTile tile = getBoardTile(col, row);
        if (col == row && verifyDiagonal(true, val, tile)) {
            return true;
        }
        if (col + row == getNumColumns() - 1) {
            return verifyDiagonal(false, val, tile);
        }
        return false;
    }

    /**
     * Checks a straight line of tiles on the board to determine if they complete a valid line.
     * @param horizontal If true, checks all tiles within the given row. If false, checks all tiles in the given column.
     * @param compareVal The tileValue all checked BoardTiles must have to be considered part of a valid line.
     * @param colOrRow If checking a horizontal line, this represents a row. If checking a vertical, this represents a column.
     * @return True if a complete valid line is present.
     */
    private boolean verifyStraight(boolean horizontal, int compareVal, int colOrRow) {
        if (horizontal) {
            for (int i = 0; i < getNumColumns(); i++) {
                if (getBoardTile(i, colOrRow).getTileValue() != compareVal) {
                    return false;
                }
            }
        }
        else {
            for (int i = 0; i < getNumColumns(); i++) {
                if (getBoardTile(colOrRow, i).getTileValue() != compareVal) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks the left or right diagonals to determine whether a line has been completed.
     * @param fromLeft If true, checks the diagonal from top-left to bottom-right. If false, checks the diagonal from top-right to bottom-left.
     * @param compareVal The tileValue each BoardTile must have to be considered part of a valid line.
     * @param checkingTile The BoardTile that was selected and initiated the verification process.
     * @return True if a valid diagonal line is present.
     */
    private boolean verifyDiagonal(boolean fromLeft, int compareVal, BoardTile checkingTile) {
        if (fromLeft) {
            // start with the top-left tile
            return verifyDiagonalRecursive(true, compareVal, 0, 0, getNumColumns(), checkingTile, true);
        }
        // start with the top-right tile
        return verifyDiagonalRecursive(false, compareVal, getNumColumns() - 1, 0, getNumColumns(), checkingTile, false);
    }

    /**
     * A recursive method that checks individual BoardTiles to see if they make up a valid diagonal line.
     * @param fromLeft If true, checks the diagonal from top-left to bottom-right. If false, checks the diagonal from top-right to bottom-left.
     * @param compareVal The tileValue each BoardTile must have to be considered part of a valid line.
     * @param col The column for the BoardTile currently being checked.
     * @param row The row for the BoardTile currently being checked.
     * @param boardSize The size of the board and how many valid tiles are needed to constitute a valid diagonal line.
     * @param checkingTile The BoardTile that was selected and initiated the verification process.
     * @param containsCheckingTile If the checkingTile is part of the set of BoardTiles being checked.
     * @return The value of containsCheckingTile if all necessary BoardTiles have been verified, or false if there aren't enough tiles or one isn't valid.
     */
    private boolean verifyDiagonalRecursive(
            boolean fromLeft, int compareVal, int col, int row, int boardSize,
            BoardTile checkingTile, boolean containsCheckingTile) {

        // have checked all necessary tiles and can end the recursion
        if (boardSize == 0) { return containsCheckingTile; }

        if (col < 0 || row < 0 || col >= getNumColumns() || row >= getNumRows()) {
            return false;
        }

        BoardTile current = getBoardTile(col, row);
        if (current.getTileValue() != compareVal) { return false; }

        if (fromLeft) {
            return verifyDiagonalRecursive(true, compareVal, ++col, ++row, --boardSize, checkingTile, containsCheckingTile);
        }
        else {
            return verifyDiagonalRecursive(false, compareVal, --col, ++row, --boardSize, checkingTile, containsCheckingTile || current.equals(checkingTile));
        }
    }
}