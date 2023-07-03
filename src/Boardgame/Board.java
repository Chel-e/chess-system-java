package Boardgame;

public class Board {
    private int row;
    private int columns;
    private Piece[][] pieces;
    public Board(int row, int columns) {
        this.row = row;
        this.columns = columns;
        pieces = new Piece[row][columns];
    }
    
}
