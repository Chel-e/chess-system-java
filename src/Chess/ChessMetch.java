package Chess;

import Boardgame.Board;

public class ChessMetch {
    private Board board;

    public ChessMetch() {
        board = new Board(8, 8);
    }
    
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumns()];
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    } 
}
