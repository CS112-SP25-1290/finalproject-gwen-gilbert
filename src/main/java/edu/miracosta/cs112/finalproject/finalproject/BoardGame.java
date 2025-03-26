package edu.miracosta.cs112.finalproject.finalproject;

public abstract class BoardGame extends MiniGame {
    public BoardTile[][] board;
    protected int numColumns;
    protected int numRows;

    public static final int DEFAULT_NUM_COLUMNS = 5;
    public static final int DEFAULT_NUM_ROWS = 4;
    protected static final String DEFAULT_TILE_VALUE = "";

    public String defaultTileValue;

    public BoardGame(int columns, int rows) {
        numColumns = columns;
        numRows = rows;
        defaultTileValue = DEFAULT_TILE_VALUE;
    }
    public BoardGame() {
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
     * @throws BoardTileNotFoundException
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
     * @throws BoardTileNotFoundException
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
        ConsoleUtils.printLine('=', 3 + 6 * numColumns); // size in proportion to board length
    }

    protected abstract BoardTile playerSelectTile();
    protected abstract BoardTile computerSelectTile();
    
    /** 
     * Inner class representing a tile on a game board
     */
    public class BoardTile
    {
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
            return ConsoleUtils.centreString(String.valueOf(this.tileValue), 3);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj != null && obj instanceof BoardTile tile)
            {
                if (tile.getColumn() == this.column && tile.getRow() == this.row && tile.getTileValue().equals(this.tileValue)) {
                    return true;
                }
            }
            return false;
        }
    }
}
