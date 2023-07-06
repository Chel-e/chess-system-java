package Chess;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;
import pieces.King;
import pieces.Rock;

public class ChessMetch {
    private static final Color WHITE = null;
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
        public void initialSetup() {
        board.placePiece(new Rock(board, WHITE), new Position(2,1));
        board.placePiece(new King(board, WHITE), new Position(0,4));
    }
}
