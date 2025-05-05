
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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TicTacToeGame extends BoardGameBuilder {
    private boolean lineCompleted;
    private boolean playerIsX;
    public TicTacToeGame(int columns, int rows) {
        super(columns, rows);
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
    protected void onPlayerSelectTile(BoardTile tile) {

    }

    @Override
    protected boolean gameHasEnded() { return lineCompleted; }
    @Override
    protected void onTileSelected(BoardTile tile) {
        int col = tile.getColumn();
        int row = tile.getRow();

        for (int i = col; i < numColumns; i++) {
            for (int j = row; j < numRows; j++) {
                board[i][j].getTile().setText(playerTurn ? (playerIsX? "X" : "O") : (playerIsX? "O" : "X"));
                board[i][j].getTile().setDisable(true);
                board[i][j].setTileValue(playerTurn ? 1 : 2);
            }
        }

        super.onTileSelected(tile);
    }

    public void checkIfGameEnded() {

    }
}