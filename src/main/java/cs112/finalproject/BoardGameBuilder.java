package cs112.finalproject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.util.Arrays;

public abstract class BoardGameBuilder extends MiniGameBuilder {
    public BoardTile[][] board;
    protected int numColumns;
    protected int numRows;

    protected Region ChangeBoardRegion;

    protected static final int DEFAULT_NUM_COLUMNS = 5;
    protected static final int DEFAULT_NUM_ROWS = 4;
    protected static final String DEFAULT_TILE_VALUE = "";

    public String defaultTileValue;

    public BoardGameBuilder(int columns, int rows) {
        super();
        ChangeBoardRegion = new ChangeBoardBuilder().build();
        numColumns = columns;
        numRows = rows;
        defaultTileValue = DEFAULT_TILE_VALUE;
    }
    public BoardGameBuilder() {
        this(DEFAULT_NUM_COLUMNS, DEFAULT_NUM_ROWS);
    }

    public boolean setNumColumns(int columns) {
        if (columns > -1) {
            numColumns = columns;
            return true;
        }
        return false;
    }
    public boolean setNumRows(int rows) {
        if (rows > -1) {
            numRows = rows;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the number of columns that constitute the game board.
     * @return How many columns are in the board.
     */
    public int getNumColumns() {
        return numColumns;
    }
    /**
     * Retrieves the number of rows that constitute the game board.
     * @return How many rows are in the board.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Retrieves a BoardTile in a given column and row position.
     * @param column Index for the tile's column.
     * @param row Index for the tile's row.
     * @return Board tile at the given position.
     * @throws BoardTileNotFoundException Error for when the given BoardTile is not found
     */
    private BoardTile findBoardTile(int column, int row) throws BoardTileNotFoundException {
        if (board.length == 0) {
            throw new BoardTileNotFoundException("Board does not exist or has not been generated, cannot retrieve board tile.");
        }
        
        if (board.length <= column) {
            throw new IndexOutOfBoundsException("Column index [" + column + "] is outside the bounds of the board with numColumns [" + numColumns + "]");
        }

        if (board[0].length <= row) {
            throw new IndexOutOfBoundsException("Row index [" + row + "] is outside the bounds of the board with numRows [" + numRows + "]");
        }

        if (board[column][row] == null) {
            throw new BoardTileNotFoundException("Board tile at column, row [" + column + "," + row + "] does not exist.");
        }

        return board[column][row];
    }

    /**
     * Retrieves a BoardTile in a given column and row position.
     * @param column Index for the tile's column.
     * @param row Index for the tile's row.
     * @return Board tile at the given position.
     */
    protected BoardTile getBoardTile(int column, int row) {
        try {
            return findBoardTile(column, row);
        }
        catch (BoardTileNotFoundException | IndexOutOfBoundsException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Creates a default board of with the given number of columns and rows.
     */
    protected void generateBoard() {
        board = new BoardTile[numColumns][numRows];
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                board[i][j] = new BoardTile(i, j);
            }
        }
    }
    
    protected void displayBoard() {
        for (int i = -1; i < numRows; i++)
        {
            String columnStr; // print column indeces on lefthoof side of the board
            if (i == -1) // empty space in the top-most corner
            {
                columnStr = "   |";
            }
            else
            {
                columnStr = String.format("%-3d|", i);
            }
            System.out.print(columnStr);

            for (int j = 0; j < numColumns; j++)
            {
                String rowStr;
                if (i == -1) // print row indeces in the first row
                {
                    rowStr = String.format("%2d ", j);
                }
                else // print board spaces
                {
                    rowStr = getBoardTile(j, i).toString();
                }
                System.out.print(rowStr + "|"); // print separators
            }
            System.out.println();
        }
        //ConsoleUtils.printLine('=', 3 + 6 * numColumns); // size in proportion to board length
    }

    protected abstract BoardTile playerSelectTile();
    protected abstract BoardTile computerSelectTile();

    public void switchToStartMenu() { this.returnToStartMenu(); }
    public void openTutorialPopup() {

    }
    public void openChangeBoardPopup() {

    }

    @Override
    public Region buildStartMenu() {
        Button playButton = SceneUtils.newButton("Start", ev -> startGame());
        Button tutorialButton = SceneUtils.newButton("How to Play", ev -> openTutorialPopup());
        Label currentFirstTurn = new Label("Current First Turn Player:");
        ChoiceBox<StartingPlayer> chooseFirstTurn = new ChoiceBox<StartingPlayer>(FXCollections.observableList(Arrays.stream(StartingPlayer.values()).toList()));
        chooseFirstTurn.setValue(firstTurnPlayer);
        chooseFirstTurn.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number value, Number newValue) {
                firstTurnPlayer = StartingPlayer.values()[newValue.intValue()];
            }
        });
        Button changeBoardButton = SceneUtils.newButton("Change Board Size", ev -> openChangeBoardPopup());
        Button exitButton = SceneUtils.newButton("Return To Main Menu", onExitEvent);

        VBox retval = new VBox(playButton, tutorialButton, currentFirstTurn, chooseFirstTurn, exitButton);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }

    /**
     * Inner class that builds and controls the menu for changing the size of the board.
     */
    public class ChangeBoardBuilder extends SceneBuilder {
        private Label boardSizeLabel;
        private Label columnLabel;
        private Label rowsLabel;

        private static final String BOARD_SIZE_KEY = "%d x %d";
        private static final String COLUMNS_PREFIX = "Current Num Columns: ";
        private static final String ROWS_PREFIX = "Current Num Rows: ";
        @Override
        public Region buildScene() {
            Label header = new Label("Change Board Size");
            SceneUtils.standardise(header);

            boardSizeLabel = new Label();
            SceneUtils.standardise(boardSizeLabel);
            columnLabel = new Label();
            SceneUtils.standardise(columnLabel);
            rowsLabel = new Label();
            SceneUtils.standardise(rowsLabel);

            Button returnButton = new Button();
            returnButton.setText("Return To Menu");
            returnButton.setOnAction(ev -> returnToStartMenu());
            SceneUtils.standardise(returnButton);

            update();

            VBox retval = new VBox(header, columnLabel, rowsLabel, returnButton);
            retval.setAlignment(Pos.CENTER);
            retval.setSpacing(10);
            return retval;
        }

        public void update() {
            this.boardSizeLabel.setText(String.format(BOARD_SIZE_KEY, getNumColumns(), getNumRows()));
            this.columnLabel.setText(COLUMNS_PREFIX + getNumColumns());
            this.rowsLabel.setText(ROWS_PREFIX + getNumRows());
        }
    }

    /** 
     * Inner class representing a tile on a game board
     */
    public class BoardTile {
        public int column;
        public int row;
        public String tileValue;
        
        public BoardTile(int column, int row) {
            this.column = column;
            this.row = row;
            tileValue = defaultTileValue;
        }

        public BoardTile() {
            this(0, 0);
        }

        /**
         * Set this object's column field to a given nonnegative int.
         * @param column Nonnegative int representing this object's column.
         * @return True if this object's column field was modified or not.
         */
        public boolean setColumn(int column) {
            if (column > -1) {
                this.column = column;
                return true;
            }
            return false;
        }
        /**
         * Set this object's row field to a given nonnegative int.
         * @param row Nonnegative int representing this object's row.
         * @return True if this object's row field was modified or not.
         */
        public boolean setRow(int row) {
            if (row > -1) {
                this.row = row;
                return true;
            }
            return false;
        }
        /**
         * Set this object's tileValue field to a given char.
         */
        public void setTileValue(String tileValue) {
            this.tileValue = tileValue;
        }

        /**
         * Get this object's current column index.
         * @return This object's column.
         */
        public int getColumn() {
            return this.column;
        }
        /**
         * Gets this object's current row index.
         * @return This object's row.
         */
        public int getRow() {
            return this.row;
        }
        /**
         * Gets this object's current tile value.
         * @return This object's tile value.
         */
        public String getTileValue() {
            return this.tileValue;
        }

        @Override
        public String toString() {
            return null;
            //return ConsoleUtils.centreString(String.valueOf(this.tileValue), 3);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj instanceof BoardTile tile)
            {
                return tile.getColumn() == this.column && tile.getRow() == this.row && tile.getTileValue().equals(this.tileValue);
            }
            return false;
        }
    }
}
