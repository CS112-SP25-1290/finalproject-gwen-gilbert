
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
package edu.miracosta.cs112.finalproject.finalproject;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ChompGame extends BoardGame {
    public ChompGame(int columns, int rows) {
        super(columns, rows);
        //this.StartMenuRegion = new WrappedSceneBuilder().build();
        defaultTileValue = "[ ]";
    }

    public ChompGame() {
        this(5, 4);
    }

    @Override
    protected boolean handleGameMenu() {
        switch (ConsoleUtils.getInt(">> ", 0, 3)) {
            case 0:
                play();
                break;
            case 1:
                numColumns = ConsoleUtils.getInt("How long should the board be (1, 10)? Default: 5 >> ", 1, 10);
                numRows = ConsoleUtils.getInt("How tall should the board be (1, 10)? Default: 4 >> ", 1, 10);
                break;
            case 2:
                System.out.println("Who should go first?");
                System.out.println("0: User | 1: Computer | 2: Random");
                switch (ConsoleUtils.getInt(">> ", 0, 2))
                {
                    case 0:
                        firstTurnPlayer = StartingPlayer.USER;
                        break;
                    case 1:
                        firstTurnPlayer = StartingPlayer.COMPUTER;
                        break;
                    case 2:
                        firstTurnPlayer = StartingPlayer.RANDOM;
                        break;
                }
                break;
            case 3:
                System.out.println("Bye-bye!");
                return true;
        }
        return false;
    }

    @Override
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
                ConsoleUtils.getInput("Press enter to continue");
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
        ConsoleUtils.getInput("Press enter to continue");
    }
    
    @Override
    protected BoardTile playerSelectTile() {
        int colSelection = ConsoleUtils.getInt("What column do you want to select? >> ", 0, numColumns - 1);
        int rowSelection = ConsoleUtils.getInt("What row do you want to select? >> ", 0, numRows - 1);
        BoardTile retval = getBoardTile(colSelection, rowSelection);

        if (retval != null && retval.getTileValue().equals(""))
        {
            System.out.println("That tile has already been eaten! Please select another!");
            return null;
        }
        return retval;
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

    /**
     * Prints game information to the console
     */
    @Override
    protected void displayGameMenu()
    {
        ConsoleUtils.printCentredBox("-*-*-*-*-*-*CHOMP*-*-*-*-*-*-");
        ConsoleUtils.printLine('=');
        ConsoleUtils.printCentredBox("Board Size: " + numColumns + " x " + numRows);
        ConsoleUtils.printCentredBox("Who Goes First: " + firstTurnPlayer);
        ConsoleUtils.printCentredBox("User Wins: " + userWins);
        ConsoleUtils.printCentredBox("Computer Wins: " + computerWins);
        ConsoleUtils.printLine('=');
        ConsoleUtils.printCentredBox("0: Play              | 1: Change Board");
        ConsoleUtils.printCentredBox("2: Change First Turn | 3: Exit        ");
        ConsoleUtils.printLine('=');
    }

    @Override
    public Region build() {
        return null;
    }

    @Override
    protected Region buildStartMenu() {
        Label label = new Label("Chomp!");
        standardise(label);

        Button button1 = new Button("Play");
        standardise(button1);
        button1.setOnAction(event -> );

        Button button2 = new Button("Change Starting Player");
        standardise(button2);
        button2.setOnAction(onExitEvent);

        Button button3 = new Button("Change Board Size");
        standardise(button3);
        button3.setOnAction(onExitEvent);

        Button button4 = new Button("Exit");
        standardise(button4);
        button4.setOnAction(onExitEvent);

        HBox row1 = new HBox(button1, button2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(button3, button4);
        row2.setAlignment(Pos.CENTER);

        VBox main = new VBox(label, row1, row2);
        main.setAlignment(Pos.CENTER);
        main.setSpacing(10);
        return main;
    }
}