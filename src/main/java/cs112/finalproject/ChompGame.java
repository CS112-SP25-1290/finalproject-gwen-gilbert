
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

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ChompGame extends BoardGameBuilder {
    public ChompGame(int columns, int rows) {
        super(columns, rows);
        headerImage = new Image(SceneUtils.CHOMP_LOGO_PATH);
        //this.StartMenuRegion = new WrappedSceneBuilder().build();
        defaultTileValue = "[ ]";
    }

    public ChompGame() {
        this(5, 4);
    }
    
    @Override
    protected BoardTile playerSelectTile() {
        return null;
//        int colSelection = ConsoleUtils.getInt("What column do you want to select? >> ", 0, numColumns - 1);
//        int rowSelection = ConsoleUtils.getInt("What row do you want to select? >> ", 0, numRows - 1);
//        BoardTile retval = getBoardTile(colSelection, rowSelection);
//
//        if (retval != null && retval.getTileValue().equals(""))
//        {
//            System.out.println("That tile has already been eaten! Please select another!");
//            return null;
//        }
//        return retval;
    }

    @Override
    protected BoardTile computerSelectTile() {
        BoardTile retval = getBoardTile(Rand.nextInt(numColumns), Rand.nextInt(numRows));
        if (retval != null && retval.getTileValue().equals(""))
        {
            return null;
        }
        return retval;
    }

    /**
     * Initialises and fills an int array representing the play board
     */
    @Override
    protected void generateBoard()
    {
        super.generateBoard();
        board[0][0].setTileValue("[X]");
    }
}