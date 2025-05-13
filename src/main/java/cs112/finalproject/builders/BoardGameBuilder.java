package cs112.finalproject.builders;

import cs112.finalproject.BoardTileNotFoundException;
import cs112.finalproject.SceneUtils;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public abstract class BoardGameBuilder extends MiniGameBuilder {
    public BoardTile[][] board;
    private int numColumns;
    private int numRows;
    protected HBox boardWrapper;
    private TextField columnField;
    private TextField rowField;
    protected Image DEFAULT_TILE_IMAGE;
    private static final int MIN_NUM_COLUMNS_ROWS = 3;
    private static final int MAX_NUM_COLUMNS_ROWS = 7;
    public BoardGameBuilder(int columns, int rows) {
        super();
        if (!setNumColumns(columns) || !setNumRows(rows)) {
            numColumns = numRows = MIN_NUM_COLUMNS_ROWS;
        }
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
                //SceneUtils.bindSize(board[i][j].getTile(), SceneRegion, 2, 2);
                column.getChildren().add(board[i][j].getTile());
            }
            boardWrapper.getChildren().add(column);
        }
    }

    @Override
    public void startGame() {
        generateBoard(); // generate board before starting the game
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
        for (BoardTile[] boardTiles : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (boardTiles[j].getTileValue() == 0) {
                    boardTiles[j].getTile().setDisable(disable);
                }
            }
        }
    }

    private BoardTile onComputerSelectTile() {
        //return getBoardTile(0, 1);
        ArrayList<BoardTile> selectableTiles = getValidSelectableTiles();
        return selectableTiles.get(this.Rand.nextInt(0, selectableTiles.size()));
    }

    /**
     * Method called whenever a BoardTile is selected.
     * Controls turns and game ending.
     * @param tile The BoardTile that has been selected.
     */
    protected void onTileSelected(BoardTile tile) {
        System.out.println("Player:" + playerTurn + " | " + tile.toString());

        if (gameHasEnded()) {
            disableBoardTiles(true);
            endGame();
        }
        else {
            changePlayerTurn(!playerTurn);
        }
    }

    /**
     * Gets a list of BoardTile that can be selected by the computer.
     * Selectable BoardTiles are those with tileValue 0.
     * @return A list of BoardTile that can be selected by the computer.
     */
    protected ArrayList<BoardTile> getValidSelectableTiles() {
        ArrayList<BoardTile> selectableTiles = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                BoardTile tile = getBoardTile(i, j);
                if (tile != null && tile.getTileValue() == 0) {
                    selectableTiles.add(tile);
                }
            }
        }
        return selectableTiles;
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
            e.printStackTrace();
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
    public void switchToStartMenu() {
        if (columnField != null) {
            columnField.setText(Integer.toString(getNumColumns()));
        }
        if (rowField != null) {
            rowField.setText(Integer.toString(getNumRows()));
        }
        super.switchToStartMenu();
    }
    @Override
    public Region buildStartMenu() {
        VBox retval = SceneUtils.newVBox();
        Label stats = SceneUtils.newLabel("");
        stats.setTextAlignment(TextAlignment.CENTER);
        stats.textProperty().bind(Bindings.format("Player Wins: %d%nL@@KER Wins: %d%n", userWins, computerWins));
        SceneUtils.bindSize(stats, retval);

        Button playButton = SceneUtils.newButton("Start", ev -> initialiseGame());
        SceneUtils.bindSize(playButton, retval);
        Button exitButton = SceneUtils.newButton("Back To Main Menu", onExitEvent);
        SceneUtils.bindSize(exitButton, retval, 0 ,12);

        retval.getChildren().addAll(stats, ChangePlayerRegion);
        Region region = buildBoardSizeRegion(retval, false);
        if (region != null) {
            retval.getChildren().add(region);
        }
        retval.getChildren().addAll(playButton, exitButton);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }

    /**
     * Constructs the container that holds the scene elements that control the board size.
     * @param parent The parent container that this one will be bound to.
     * @return A Region representing the container.
     */
    protected Region buildBoardSizeRegion(Region parent, boolean forceSquareBoard) {
        HBox box = new HBox();
        box.prefWidthProperty().bind(parent.widthProperty().divide(4));
        Label label = SceneUtils.newLabel("Board Size: ");
        SceneUtils.bindSize(label, box, 0, 12);

        // listener logic retrieved from:
        // https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        columnField = new TextField();
        columnField.prefWidthProperty().bind(box.prefWidthProperty().divide(8));
        columnField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int i = Integer.parseInt(newValue);
                if (i < MIN_NUM_COLUMNS_ROWS) {
                    i = MIN_NUM_COLUMNS_ROWS;
                    columnField.setText(Integer.toString(MIN_NUM_COLUMNS_ROWS));
                }
                else if (i > MAX_NUM_COLUMNS_ROWS) {
                    i = MAX_NUM_COLUMNS_ROWS;
                    columnField.setText(Integer.toString(MAX_NUM_COLUMNS_ROWS));
                }

                setNumColumns(i);
                if (forceSquareBoard) {
                    setNumRows(i);
                }
            }
            catch (NumberFormatException e) {
                columnField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        SceneUtils.bindFontSize(columnField);
        SceneUtils.bindSize(columnField, box, 0, 12);
        box.getChildren().addAll(label, columnField);

        if (!forceSquareBoard) {
            Label label2 = SceneUtils.newLabel(" X ");
            SceneUtils.bindSize(label2, box, 0, 12);

            rowField = new TextField();
            rowField.prefWidthProperty().bind(box.prefWidthProperty().divide(8));
            rowField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    int i = Integer.parseInt(newValue);
                    if (i < MIN_NUM_COLUMNS_ROWS) {
                        i = MIN_NUM_COLUMNS_ROWS;
                        rowField.setText(Integer.toString(MIN_NUM_COLUMNS_ROWS));
                    }
                    else if (i > MAX_NUM_COLUMNS_ROWS) {
                        i = MAX_NUM_COLUMNS_ROWS;
                        rowField.setText(Integer.toString(MAX_NUM_COLUMNS_ROWS));
                    }
                    setNumRows(i);
                }
                catch (NumberFormatException e) {
                    rowField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            SceneUtils.bindFontSize(rowField);
            SceneUtils.bindSize(rowField, box, 0, 12);

            box.getChildren().addAll(label2, rowField);
        }

        box.setAlignment(Pos.CENTER);
        return box;
    }

    /** Inner class representing a tile on a game board */
    public class BoardTile {
        private final int column;
        private final int row;
        private final BoardTilePosition position;
        private int tileValue;
        private final Button tile;

        public BoardTile(int column, int row, int tileValue) {
            this.column = column;
            this.row = row;
            this.tileValue = tileValue;
            boolean firstOrLastColumn = (column == 0 || column == getNumColumns() - 1);
            boolean firstOrLastRow = (row == 0 || row == getNumRows() - 1);
            if (firstOrLastColumn && firstOrLastRow) {
                position = BoardTilePosition.CORNER;
            }
            else if (firstOrLastColumn || firstOrLastRow) {
                position = BoardTilePosition.EDGE;
            }
            else {
                position = BoardTilePosition.BODY;
            }
            this.tile = SceneUtils.newButton(null, ev -> onTileSelected(this));
            if (DEFAULT_TILE_IMAGE != null) {
                this.tile.setGraphic(SceneUtils.newImageView(DEFAULT_TILE_IMAGE));
            }
            else {
                this.tile.setPrefSize(50, 50);
                SceneUtils.bindSize(this.tile, boardWrapper);
            }
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
        public BoardTilePosition getTilePosition() { return this.position; }
        @Override
        public String toString() {
            return String.format("BoardTile: column: %d, row: %d, value: %s", column, row, tileValue);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj instanceof BoardTile boardTile) {
                return boardTile.getColumn() == this.column
                        && boardTile.getRow() == this.row
                        && boardTile.getTileValue() == this.tileValue;
            }
            return false;
        }
        public enum BoardTilePosition {
            CORNER,
            EDGE,
            BODY
        }
    }
}
