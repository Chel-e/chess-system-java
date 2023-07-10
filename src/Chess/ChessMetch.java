package Chess;

import Boardgame.Board;
import pieces.King;
import pieces.Rock;

public class ChessMetch {
    private Board board;

    public ChessMetch() {
        board = new Board(8, 8);
        initialSetup();
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
    private void PlaceNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
    public void initialSetup() {
        PlaceNewPiece('b', 6, new Rock(board, Color.WHITE));
        PlaceNewPiece('e', 8, new King(board, Color.WHITE));
    }
}
