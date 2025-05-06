package cs112.finalproject.builders;

import cs112.finalproject.SceneUtils;
import javafx.scene.image.Image;
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
        super.startGame();
    }

    @Override
    protected ArrayList<BoardTile> getValidSelectableTiles() {
        ArrayList<BoardTile> selectableTiles = super.getValidSelectableTiles();
        if (selectableTiles.size() > 1) {
            selectableTiles.remove(0); // computer will never select the top-left tile unless it's the only option
        }
        return selectableTiles;
    }

    @Override
    protected boolean gameHasEnded() { return lineCompleted; }
    @Override
    protected void onTileSelected(BoardTile tile) {
        int col = tile.getColumn();
        int row = tile.getRow();

        tile.getTile().setGraphic(SceneUtils.newImageView(playerTurn ? (playerIsX? TILE_X : TILE_O) : (playerIsX? TILE_O : TILE_X)));
        tile.getTile().setDisable(true);
        tile.setTileValue(playerTurn ? 1 : 2);
        checkIfGameEnded(tile);
        super.onTileSelected(tile);
    }

    @Override
    public Region buildBoardSizeRegion(Region parent) {
        return null; // cannot change the size of the board
    }
    private void checkIfGameEnded(BoardTile selectedTile) {
        if (lineCompleted) {
            return;
        }

        int column = selectedTile.getColumn();
        int row = selectedTile.getRow();
        // tic-tac-toe board is always 3x3, so we can hardcode the min and max col/row values
        boolean firstOrLastColumn = (column == 0 || column == 2);
        boolean firstOrLastRow = (column == 0 || row == 2);

        if (firstOrLastColumn && firstOrLastRow) {
            lineCompleted = verifyCornerTile(column, row);
        }
        else if (firstOrLastColumn || firstOrLastRow) {
            lineCompleted = verifyEdgeTile(column, row);
        }
        else {
            lineCompleted = verifyBodyTile(column, row);
        }
    }

    private boolean verifyCornerTile(int col, int row) {
        if (verifyEdgeTile(col, row)) {
            return true;
        }

        return verifyDiagonal(col == row);
    }
    private boolean verifyEdgeTile(int col, int row) {
        if (verifyStraight(true, col, row)) {
            return true;
        }
        return verifyStraight(false, col, row);
    }
    private boolean verifyBodyTile(int col, int row) {
        if (verifyEdgeTile(col, row)) {
            return true;
        }

        if (verifyDiagonal(true)) {
            return true;
        }

        return verifyDiagonal(false);
    }

    private boolean verifyStraight(boolean horizontal, int col, int row) {
        int valueToCheck = playerTurn ? 1 : 2;
        if (horizontal) {
            return getBoardTile(0, row).getTileValue() == valueToCheck
                    && getBoardTile(1, row).getTileValue() == valueToCheck
                    && getBoardTile(2, row).getTileValue() == valueToCheck;
        }
        return getBoardTile(col, 0).getTileValue() == valueToCheck
                && getBoardTile(col, 1).getTileValue() == valueToCheck
                && getBoardTile(col, 2).getTileValue() == valueToCheck;
    }

    /**
     * Checks if there is three-in-a-row on a diagonal.
     * @param left Whether to check the diagonal going from top-left to bottom-right. Otherwise, check from top-right to bottom-left.
     * @return True if the diagonal is filled by the current player's tile.
     */
    private boolean verifyDiagonal(boolean left) {
        int valueToCheck = playerTurn ? 1 : 2;
        if (left) {
            return getBoardTile(0, 0).getTileValue() == valueToCheck
                    && getBoardTile(1, 1).getTileValue() == valueToCheck
                    && getBoardTile(2, 2).getTileValue() == valueToCheck;
        }
        return getBoardTile(2, 0).getTileValue() == valueToCheck
                && getBoardTile(1, 1).getTileValue() == valueToCheck
                && getBoardTile(0, 2).getTileValue() == valueToCheck;
    }
}