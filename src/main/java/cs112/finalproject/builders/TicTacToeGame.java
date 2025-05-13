package cs112.finalproject.builders;

import cs112.finalproject.SceneUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToeGame extends BoardGameBuilder {
    private boolean playerIsX;
    private boolean lineCompleted;
    private int currentTurnValue;
    private int movesLeft;
    private final Image TILE_X;
    private final Image TILE_O;
    private final ArrayList<BoardTile> GET_TILES_CACHE = new ArrayList<>();
    private final ArrayList<BoardTile> GET_TILES_PLAYER_CACHE = new ArrayList<>();
    public TicTacToeGame(int columns, int rows) {
        super(columns, rows);
        DEFAULT_TILE_IMAGE = new Image(SceneUtils.TIC_TAC_TOE_EMPTY);
        TILE_X = new Image(SceneUtils.TIC_TAC_TOE_X);
        TILE_O = new Image(SceneUtils.TIC_TAC_TOE_O);
        headerImage = new Image(SceneUtils.TIC_TAC_TOE_LOGO_PATH);
        START_MENU_FOOTER = SceneUtils.TIC_TAC_TOE_FOOTER;
    }
    public TicTacToeGame() {
        this(3, 3);
    }

    @Override
    public void startGame() {
        playerIsX = playerTurn;
        currentTurnValue = playerTurn ? 1 : 2;
        lineCompleted = false;
        movesLeft = getNumColumns() * getNumColumns();
        super.startGame();
    }
    @Override
    protected boolean gameHasEnded() { return lineCompleted || gameHasTied; }
    @Override
    public void changePlayerTurn(boolean playerTurn) {
        currentTurnValue = playerTurn ? 1 : 2;
        super.changePlayerTurn(playerTurn);
    }
    @Override
    public Region buildBoardSizeRegion(Region parent, boolean forceSquare) {
        return super.buildBoardSizeRegion(parent, true);
    }
    @Override
    protected void onTileSelected(BoardTile tile) {
        tile.getTile().setGraphic(SceneUtils.newImageView(playerTurn ? (playerIsX? TILE_X : TILE_O) : (playerIsX? TILE_O : TILE_X)));
        tile.getTile().setDisable(true);
        tile.setTileValue(currentTurnValue);
        movesLeft--;
        if (!lineCompleted) {
            lineCompleted = (checkTile(tile, true) == 1);
        }
        if (movesLeft == 0 && !lineCompleted) {
            gameHasTied = true;
        }
        super.onTileSelected(tile);
    }
    @Override
    protected ArrayList<BoardTile> getValidSelectableTiles() {
        ArrayList<BoardTile> selectableTiles = super.getValidSelectableTiles();
        ArrayList<BoardTile> cacheSelectableTiles = new ArrayList<>();
        ArrayList<Integer> validationValues = new ArrayList<>();
        for (BoardTile tile : selectableTiles) {
            int i = checkTile(tile, false);
            if (i != 0) {
                validationValues.add(i);
            }
        }

        // if any selectable spaces can complete a line
        if (validationValues.contains(1) || validationValues.contains(2)) {
            boolean computerCanWin = validationValues.contains(2);
            for (int i = 0; i < validationValues.size(); i++) {
                int val = validationValues.get(i);
                if (val != 0) {
                    if (computerCanWin && val != currentTurnValue) {
                        continue;
                    }
                    cacheSelectableTiles.add(selectableTiles.get(i));
                }
            }
            return cacheSelectableTiles;
        }

        System.out.println("Default random: " + selectableTiles.size());
        return selectableTiles;
    }

    private int checkTile(BoardTile selectedTile, boolean checkForCompletedLine) {
        switch (selectedTile.getTilePosition()) {
            case CORNER -> {
                //System.out.println("CORNER");
                return verifyCornerTile(selectedTile.getColumn(), selectedTile.getRow(), checkForCompletedLine);
            }
            case EDGE -> {
                //System.out.println("EDGE");
                return verifyEdgeTile(selectedTile.getColumn(), selectedTile.getRow(), checkForCompletedLine);
            }
            default -> {
                //System.out.println("BODY");
                return verifyBodyTile(selectedTile.getColumn(), selectedTile.getRow(), checkForCompletedLine);
            }
        }
    }

    private void getTilesStraight(boolean horizontal, int colOrRow) {
        GET_TILES_CACHE.clear();
        for (int i = 0; i < getNumColumns(); i++) {
            if (horizontal) {
                GET_TILES_CACHE.add(getBoardTile(i, colOrRow));
            }
            else {
                GET_TILES_CACHE.add(getBoardTile(colOrRow, i));
            }
        }
    }
    private void getTilesDiagonal(boolean fromLeft) {
        GET_TILES_CACHE.clear();
        if (fromLeft) { // start with the top-left tile
            getTilesDiagonalRecursive(true, 0, 0);
        }
        else { // start with the top-right tile
            getTilesDiagonalRecursive(false, getNumColumns() - 1, 0);
        }
        //System.out.println("getDiag: " + GET_TILES_CACHE.size());
    }
    /**
     * A recursive method that checks individual BoardTiles to see if they make up a valid diagonal line.
     * @param fromLeft If true, checks the diagonal from top-left to bottom-right. If false, checks the diagonal from top-right to bottom-left.
     * @param col The column for the BoardTile currently being checked.
     * @param row The row for the BoardTile currently being checked.
     * @return The value of containsCheckingTile if all necessary BoardTiles have been verified, or false if there aren't enough tiles or one isn't valid.
     */
    private boolean getTilesDiagonalRecursive(boolean fromLeft, int col, int row) {
        if (GET_TILES_CACHE.size() == getNumColumns()) {
            return true;
        }

        GET_TILES_CACHE.add(getBoardTile(col, row));
        if (fromLeft) {
            return getTilesDiagonalRecursive(true, ++col, ++row);
        }
        else {
            return getTilesDiagonalRecursive(false, --col, ++row);
        }
    }
    /**
     * Checks a straight line of tiles on the board to determine if they complete a valid line.
     * @param horizontal If true, checks all tiles within the given row. If false, checks all tiles in the given column.
     * @param colOrRow If checking a horizontal line, this represents a row. If checking a vertical, this represents a column.
     * @param checkForCompletedLine If true, checks if there is a row consisting of valid tiles.
     * @return True if a complete valid line is present.
     */
    private int verifyStraight(boolean horizontal, int colOrRow, boolean checkForCompletedLine) {
        getTilesStraight(horizontal, colOrRow);
        return verifyLine(checkForCompletedLine);
    }

    /**
     * Checks the left or right diagonals to determine whether a line has been completed.
     * @param fromLeft If true, checks the diagonal from top-left to bottom-right. If false, checks the diagonal from top-right to bottom-left.
     * @param selectedTile The BoardTile that was selected and initiated the verification process.
     * @param checkForCompletedLine If true, checks if there is a row consisting of valid tiles.
     * @return True if a valid diagonal line is present.
     */
    private int verifyDiagonal(boolean fromLeft, BoardTile selectedTile, boolean checkForCompletedLine) {
        getTilesDiagonal(fromLeft);
        if (!GET_TILES_CACHE.contains(selectedTile)) {
            return 0;
        }
        return verifyLine(checkForCompletedLine);
    }

    /**
     * Checks the horizontal and vertical lines that intersect at the current BoardTile.
     * Performs a maximum of 2 checks.
     * @param col The column of the current BoardTile.
     * @param row The row of the current BoardTile.
     * @param checkForCompletedLine If true, checks if there is a row consisting of valid tiles.
     * @return True if the current BoardTile is part of a valid vertical or horizontal line.
     */
    private int verifyEdgeTile(int col, int row, boolean checkForCompletedLine) {
        int n = verifyStraight(true, row, checkForCompletedLine);
        if (n != 0) {
            return n;
        }
        return verifyStraight(false, col, checkForCompletedLine);
    }

    /**
     * Checks the horizontal, vertical and diagonal lines that intersect at the current BoardTile.
     * Performs a maximum of 3 checks.
     * @param col The column of the current BoardTile.
     * @param row The row of the current BoardTile.
     * @param checkForCompletedLine If true, checks if there is a row consisting of valid tiles.
     * @return True if the current BoardTile is part of a valid line.
     */
    private int verifyCornerTile(int col, int row, boolean checkForCompletedLine) {
        int n = verifyEdgeTile(col, row, checkForCompletedLine);
        if (n != 0) {
            return n;
        }

        BoardTile tile = getBoardTile(col, row);
        return verifyDiagonal(col == row, tile, checkForCompletedLine);
    }
    /**
     * Checks the horizontal, vertical and diagonal lines that intersect at the current BoardTile.
     * Performs a maximum of 4 checks.
     * @param col The column of the current BoardTile.
     * @param row The row of the current BoardTile.
     * @param checkForCompletedLine If true, checks if there is a row consisting of valid tiles.
     * @return True if the current BoardTile is part of a valid line.
     */
    private int verifyBodyTile(int col, int row, boolean checkForCompletedLine) {
        int n = verifyEdgeTile(col, row, checkForCompletedLine);
        if (n != 0) {
            return n;
        }

        BoardTile tile = getBoardTile(col, row);
        if (col == row) {
            n = verifyDiagonal(true, tile, checkForCompletedLine);
            if (n != 0) {
                return n;
            }
        }
        if (col + row == getNumColumns() - 1) {
            return verifyDiagonal(false, tile, checkForCompletedLine);
        }
        return 0;
    }

    /**
     * Checks if the current cache of tiles creates a valid line, based on the given criteria.
     * @param checkForCompletedLine If true, checks if there is a row consisting of valid tiles. Otherwise, checks if the given line is one tile away from being complete
     * @return True if the current cache of tiles is valid.
     */
    private int verifyLine(boolean checkForCompletedLine) {

        // checking if the game should end
        // the tiles cache must be full of tiles matching the current turn value
        if (checkForCompletedLine) {
            return !GET_TILES_CACHE.removeIf(x -> x.getTileValue() != currentTurnValue) ? 1 : 0;
        }

        // if there is one empty space
        if (GET_TILES_CACHE.removeIf(x -> x.getTileValue() == 0)
                && GET_TILES_CACHE.size() == getNumColumns() - 1) {

            // return if all the occupied tiles belong to the same player
            if (GET_TILES_CACHE.stream().allMatch(x -> x.getTileValue() == 1)) {
                return currentTurnValue == 1 ? 2 : 1;
            }
            if (GET_TILES_CACHE.stream().allMatch(x -> x.getTileValue() == 1)) {
                return currentTurnValue == 2 ? 2 : 1;
            }
        }
        return 0;
    }
}