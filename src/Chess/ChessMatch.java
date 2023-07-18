package Chess;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;
import pieces.King;
import pieces.Rock;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }
    
    public  ChessPiece[][] getPieces() {
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
        PlaceNewPiece('c', 1, new Rock(board, Color.WHITE));
        PlaceNewPiece('c', 2, new Rock(board, Color.WHITE));
        PlaceNewPiece('d', 2, new Rock(board, Color.WHITE));
        PlaceNewPiece('e', 2, new Rock(board, Color.WHITE));
        PlaceNewPiece('e', 1, new Rock(board, Color.WHITE));
        PlaceNewPiece('d', 1, new King(board, Color.WHITE));
        PlaceNewPiece('c', 7, new Rock(board, Color.BLACK));
        PlaceNewPiece('c', 8, new Rock(board, Color.BLACK));
        PlaceNewPiece('d', 7, new Rock(board, Color.BLACK));
        PlaceNewPiece('e', 7, new Rock(board, Color.BLACK));
        PlaceNewPiece('e', 8, new Rock(board, Color.BLACK));
        PlaceNewPiece('d', 8, new King(board, Color.BLACK));
    }
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
        ValidateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece;
	}
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
    private void ValidateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can`t move to target position");
        }
    
    }
    private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
            Position position = sourcePosition.toPosition();
            validateSourcePosition(position);
            return board.piece(position).possibleMoves();
    }
}
