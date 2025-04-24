
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

    //@Override
    protected void play()
    {
        boolean userTurn;
        if (firstTurnPlayer == StartingPlayer.RANDOM) // choose a random player to go first
        {
            userTurn = Rand.nextBoolean();
        }
        else // otherwise computer goes first if "User" isn't the selected option
        {
            userTurn = firstTurnPlayer == StartingPlayer.USER;
        }

        // generate and reset the board, then print it to the console
        generateBoard();
        displayBoard();

        while (true) // loop until someone has won
        {
            BoardTile selection = null;
            if (userTurn)
            {
                System.out.println("Your turn!");
            }
            else
            {
                System.out.println("Computer's turn!");
                //ConsoleUtils.getInput("Press enter to continue");
            }
            
            while (selection == null)
            {
                if (userTurn) // use user input to select a space
                {
                    selection = playerSelectTile();
                }
                else // computer chooses a space at random
                {
                    selection = computerSelectTile();
                }
            }

            for (int i = selection.getColumn(); i < numColumns; i++) // eat all spaces below and to the right of the selected space
            {
                for (int j = selection.getRow(); j < numRows; j++)
                {
                    board[i][j].setTileValue("");
                }
            }


            displayBoard(); // update display
            if (board[0][0].getTileValue().equals("")) // if the poisoned space has been eaten, update who has won then break out of the loop
            {
                break;
            }
            else {
                userTurn = !userTurn;
            }
        }

        if (!userTurn)
        {
            System.out.println("You win!");
            userWins++;
        }
        else
        {
            System.out.println("You lost, womp womp!");
            computerWins++;
        }
        //ConsoleUtils.getInput("Press enter to continue");
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

    @Override
    public Region buildScene() { // WIP
        VBox retval = new VBox();
        retval.setAlignment(Pos.CENTER);
        HBox rowNums = new HBox();
        retval.getChildren().add(rowNums);
        rowNums.setAlignment(Pos.CENTER);
        for (int i = 0; i < getNumRows(); i++) {
            rowNums.getChildren().add(new Label("" + i)); // replace w/ better int-to-String thing
        }

        for (int i = 1; i < getNumColumns() + 1; i++) {
            HBox column = new HBox(new Label("" + i));
            column.setAlignment(Pos.CENTER);
            for (int j = 0; j < getNumRows(); j++) {
                column.getChildren().add(new Button("test"));
            }
            retval.getChildren().add(column);
        }

        return retval;
    }
}