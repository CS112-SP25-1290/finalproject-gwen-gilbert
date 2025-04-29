package cs112.finalproject;

import cs112.finalproject.controllers.SceneController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.util.Arrays;

public abstract class BoardGameBuilder extends MiniGameBuilder {
    public BoardTile[][] board;
    protected int numColumns;
    protected int numRows;
    protected Region ChangeBoardRegion;
    protected HBox boardWrapper;
    TextField columnField;
    TextField rowField;
    protected static final String DEFAULT_TILE_VALUE = "";

    public String defaultTileValue;

    public BoardGameBuilder(int columns, int rows) {
        super();
        numColumns = columns;
        numRows = rows;
        columnField.setText(String.valueOf(getNumColumns()));
        rowField.setText(String.valueOf(getNumRows()));
        defaultTileValue = DEFAULT_TILE_VALUE;
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

    @Override
    public Region buildScene() {
        boardWrapper = new HBox();
        boardWrapper.setAlignment(Pos.CENTER);
        return boardWrapper; }

    @Override
    public void startGame() {
        super.startGame();
        generateBoard();
    }
    /**
     * Creates a default board of with the given number of columns and rows.
     */
    protected void generateBoard() {
        board = new BoardTile[numColumns][numRows];
        boardWrapper.getChildren().clear();

        for (int i = 0; i < numColumns; i++) {
            VBox column = new VBox();
            for (int j = 0; j < numRows; j++) {
                board[i][j] = new BoardTile(i, j);
                column.getChildren().add(board[i][j].getTile());
            }
            boardWrapper.getChildren().add(column);
        }
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

    protected abstract BoardTile playerSelectTile();
    protected abstract BoardTile computerSelectTile();
    public void openTutorialPopup() {

    }

    @Override
    public Region buildStartMenu() {
        VBox retval = SceneUtils.newVBox();
        Button playButton = SceneUtils.newButton("Start", ev -> startGame());
        SceneUtils.bindSize(playButton, retval);
        Button tutorialButton = SceneUtils.newButton("How to Play", ev -> switchToTutorial());
        SceneUtils.bindSize(tutorialButton, retval);
        Button exitButton = SceneUtils.newButton("Back To Main Menu", onExitEvent);
        SceneUtils.bindSize(exitButton, retval, 0 ,12);

        retval.getChildren().addAll(playButton, tutorialButton, ChangePlayerRegion);
        ChangeBoardRegion = buildBoardSizeRegion(retval);
        if (ChangeBoardRegion != null) {
            retval.getChildren().add(ChangeBoardRegion);
        }
        retval.getChildren().add(exitButton);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }
    protected Region buildBoardSizeRegion(Region parent) {
        HBox box = new HBox();
        box.prefWidthProperty().bind(parent.widthProperty().divide(4));
        Label label = SceneUtils.newLabel("Board Size: ");
        SceneUtils.bindSize(label, box, 0, 12);
        Label label2 = SceneUtils.newLabel(" X ");
        SceneUtils.bindSize(label2, box, 0, 12);

        // listener logic retrieved from:
        // https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        columnField = new TextField(String.valueOf(getNumColumns()));
        columnField.prefWidthProperty().bind(box.prefWidthProperty().divide(8));
        columnField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    columnField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                else {
                    int i = Integer.parseInt(newValue);
                    if (i > 0 && i < 10) {
                        setNumColumns(i);
                    }
                }
            }
        });
        rowField = new TextField();
        rowField.prefWidthProperty().bind(box.prefWidthProperty().divide(8));
        rowField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    rowField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                else {
                    int i = Integer.parseInt(newValue);
                    if (i > 0 && i < 10) {
                        setNumRows(i);
                    }
                }
            }
        });

        SceneUtils.bindFontSize(columnField);
        SceneUtils.bindSize(columnField, box, 0, 12);
        SceneUtils.bindFontSize(rowField);
        SceneUtils.bindSize(rowField, box, 0, 12);

        box.getChildren().addAll(label, columnField, label2, rowField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /** Inner class representing a tile on a game board */
    public class BoardTile {
        public int column;
        public int row;
        public String tileValue;

        public Button tile;

        public BoardTile(int column, int row) {
            this.column = column;
            this.row = row;
            this.tileValue = defaultTileValue;
            this.tile = new Button(tileValue);
        }

        public BoardTile() {
            this(0, 0);
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
        public Button getTile() { return this.tile; }

        @Override
        public String toString() {
            return String.format("BoardTile: row: %d, col: %d, value: %s", row, column, tileValue);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj instanceof BoardTile tile) {
                return tile.getColumn() == this.column && tile.getRow() == this.row && tile.getTileValue().equals(this.tileValue);
            }
            return false;
        }
    }
}
