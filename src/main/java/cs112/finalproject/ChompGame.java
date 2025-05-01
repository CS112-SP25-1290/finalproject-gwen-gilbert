
/********************************************
*	DEVELOPER:	Gwen Gilbert
* COLLABORATORS: Eduardo Mariscal
*	COURSE:	CS226 Discrete Structures
*	PROJECT: 01
*	LAST MODIFIED:	Feb 7, 2025
********************************************/
/********************************************
*	Chomp!
*********************************************
*	PROGRAM DESCRIPTION:
*	Plays a game of Chomp against a rudimentary AI, with customisable board size
*********************************************
*   Chomp()
*   Chomp(int, int)
*   play() : void
*   startingMenu() : void
*   doGameLogic() : void
*   validateSelection(int, int) : boolean
*   getPrintBoardSpace(int, int) : String
*   printBoard() : void
*   generateBoard() : void
*********************************************/
package cs112.finalproject;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ChompGame extends BoardGameBuilder {
    private Image EMPTY_TILE;
    private ImageView POISON_TILE;
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
    protected void onPlayerSelectTile(BoardTile tile) {

    }

    @Override
    protected boolean gameHasEnded() {
        if (board[0][0].getTileValue() == -1) {
            // since the game ends when one player loses on their turn, we invert the boolean
            // since the base assumes a player wins on their turn
            playerTurn = !playerTurn;
            return true;
        }
        return false;
    }
    @Override
    protected void onTileSelected(BoardTile tile) {
        int col = tile.getColumn();
        int row = tile.getRow();

        for (int i = col; i < numColumns; i++) {
            for (int j = row; j < numRows; j++) {
                board[i][j].getTile().setGraphic(SceneUtils.newImageView(EMPTY_TILE));
                board[i][j].getTile().setDisable(true);
                board[i][j].setTileValue(-1);
            }
        }

        super.onTileSelected(tile);
    }

//    @Override
//    protected void onComputerSelectTile() {
//        super.onComputerSelectTile();
//    }

    /**
     * Initialises and fills an int array representing the play board
     */
    @Override
    protected void generateBoard() {
        super.generateBoard();
        board[0][0].setTileValue(2);
        board[0][0].getTile().setGraphic(POISON_TILE);
    }
}