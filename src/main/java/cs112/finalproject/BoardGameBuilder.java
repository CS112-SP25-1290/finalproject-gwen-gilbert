package cs112.finalproject;

import cs112.finalproject.controllers.SceneController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BoardGameBuilder extends MiniGameBuilder {
    public BoardTile[][] board;
    protected int numColumns;
    protected int numRows;
    //protected Region ChangeBoardRegion;
    protected HBox boardWrapper;
    private TextField columnField;
    private TextField rowField;
    protected Image DEFAULT_TILE_IMAGE;

    public BoardGameBuilder(int columns, int rows) {
        super();
        if (!setNumColumns(columns) || !setNumRows(rows)) {
            numColumns = numRows = 3;
        }
        columnField.setText(String.valueOf(getNumColumns()));
        rowField.setText(String.valueOf(getNumRows()));
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

    /** Initialises and generates the board space for this board game. */
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

    @Override
    public void startGame() {
        generateBoard();
        super.startGame();
    }

    @Override
    public void onPlayerTurn() {
        disableBoardTiles(false);
    }

    @Override
    public void onComputerTurn() {
        disableBoardTiles(true);
        onTileSelected(onComputerSelectTile());
    }

    private void disableBoardTiles(boolean disable) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTileValue() != -1) {
                    board[i][j].getTile().setDisable(disable);
                }
            }
        }
    }

    protected void onTileSelected(BoardTile tile) {
        System.out.println(playerTurn + " " + tile.toString());
        if (playerTurn) {
            onPlayerSelectTile(tile);
        }

        // game has ended
        if (gameHasEnded()) {
            endGame();
        }
        else {
            changePlayerTurn(!playerTurn);
        }
    }
    protected BoardTile onComputerSelectTile() {
        ArrayList<BoardTile> selectableTiles = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                BoardTile tile = getBoardTile(i, j);
                if (tile != null && tile.getTileValue() == 0) {
                    selectableTiles.add(tile);
                }
            }
        }
        if (selectableTiles.size() > 1) {
            selectableTiles.remove(0);
        }

        return selectableTiles.get(this.Rand.nextInt(0, selectableTiles.size()));
    }
    protected abstract void onPlayerSelectTile(BoardTile tile);

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
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Region buildScene() {
        VBox retval = SceneUtils.newVBox();
        retval.setSpacing(50);
        initialiseEndButton(retval);
        boardWrapper = new HBox();
        boardWrapper.setAlignment(Pos.CENTER);
        retval.getChildren().addAll(boardWrapper, endGameButton);
        return retval;
    }

    @Override
    public Region buildStartMenu() {
        VBox retval = SceneUtils.newVBox();
        Label stats = SceneUtils.newLabel("");

        Button playButton = SceneUtils.newButton("Start", ev -> initialiseGame());
        SceneUtils.bindSize(playButton, retval);
        Button tutorialButton = SceneUtils.newButton("How to Play", ev -> switchToTutorial());
        SceneUtils.bindSize(tutorialButton, retval);
        Button exitButton = SceneUtils.newButton("Back To Main Menu", onExitEvent);
        SceneUtils.bindSize(exitButton, retval, 0 ,12);

        retval.getChildren().addAll(stats, playButton, tutorialButton, ChangePlayerRegion);
        Region region = buildBoardSizeRegion(retval);
        if (region != null) {
            retval.getChildren().add(region);
        }
        retval.getChildren().add(exitButton);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }

    /**
     * Constructs the container that holds the scene elements that control the board size.
     * @param parent The parent container that this one will be bound to.
     * @return A Region representing the container.
     */
    private Region buildBoardSizeRegion(Region parent) {
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
        private int column;
        private int row;
        private int tileValue;
        private Button tile;

        public BoardTile(int column, int row, int tileValue) {
            this.column = column;
            this.row = row;
            this.tileValue = tileValue;
            this.tile = SceneUtils.newButton(null, ev -> onTileSelected(this));
            this.tile.setGraphic(SceneUtils.newImageView(DEFAULT_TILE_IMAGE));
            this.tile.setPadding(new Insets(-1, -1, -1, -1));
        }
        public BoardTile(int column, int row) {
            this(column, row, 0);
        }

        /**
         * Set this object's tileValue field to a given char.
         */
        public void setTileValue(int tileValue) {
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
        public int getTileValue() {
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
                return tile.getColumn() == this.column && tile.getRow() == this.row && tile.getTileValue() == this.tileValue;
            }
            return false;
        }
    }
}
