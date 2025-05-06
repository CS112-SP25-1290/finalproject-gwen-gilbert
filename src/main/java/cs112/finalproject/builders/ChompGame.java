package cs112.finalproject.builders;

import cs112.finalproject.SceneUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ChompGame extends BoardGameBuilder {
    private final Image EMPTY_TILE;
    private final ImageView POISON_TILE;
    public ChompGame(int columns, int rows) {
        super(columns, rows);
        headerImage = new Image(SceneUtils.CHOMP_LOGO_PATH);
        DEFAULT_TILE_IMAGE = new Image(SceneUtils.CHOMP_TILE_FULL);
        EMPTY_TILE = new Image(SceneUtils.CHOMP_TILE_EMPTY);
        POISON_TILE = SceneUtils.newImageView(new Image(SceneUtils.CHOMP_TILE_POISON));
    }

    public ChompGame() {
        this(5, 4);
    }

    @Override
    protected boolean gameHasEnded() {
        if (board[0][0].getTileValue() == 1) {
            // the base assumes a player wins on their turn, so we invert playerTurn
            // since Chomp ends when someone loses on their turn
            playerTurn = !playerTurn;
            return true;
        }
        return false;
    }
    @Override
    protected void onTileSelected(BoardTile tile) {
        int col = tile.getColumn();
        int row = tile.getRow();

        // when a tile is selected, remove it from list of selectable tiles
        // and change graphic to show it's been eaten/is empty
        for (int i = col; i < getNumColumns(); i++) {
            for (int j = row; j < getNumRows(); j++) {
                board[i][j].getTile().setGraphic(SceneUtils.newImageView(EMPTY_TILE));
                board[i][j].getTile().setDisable(true);
                board[i][j].setTileValue(1);
            }
        }

        super.onTileSelected(tile);
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
    protected void generateBoard() {
        super.generateBoard();
        board[0][0].getTile().setGraphic(POISON_TILE); // top-left tile is poisoned
    }
}