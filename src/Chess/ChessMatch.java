package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;
import pieces.King;
import pieces.Rock;

public class ChessMatch {
    private Board board;
    private int turn;
    private boolean check;
    private Color currentPlayer;
    private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private List<ChessPiece> capturedPieces = new ArrayList<>();
    
    public ChessMatch() {
        turn = 1;
        currentPlayer = Color.WHITE;
        board = new Board(8, 8);
        initialSetup();
    }
    public int getTurn() {
        return this.turn;
    }
    
    public Color getCurrentPlayer() {
        return currentPlayer;
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
    
    public boolean getCheck() {
        return check;
    }
    private void PlaceNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
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
        if (testCheck(getCurrentPlayer())) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("you can´t put yourself in check");
        }
        check = (testCheck(Opponent(getCurrentPlayer()))) ? true : false;
		nextTurn();
        return (ChessPiece)capturedPiece;
	}
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
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
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; 
    }
    private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add((ChessPiece) capturedPiece);
        }
		return capturedPiece;
	}
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        board.placePiece(p, source);
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add((ChessPiece) capturedPiece);
        }
    }
    private Color Opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<ChessPiece> list = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
        for (ChessPiece p : list) {
            if (p instanceof King) {
                return p;
            }
        }
        throw new IllegalStateException("There is no " + color+ "King on the board");
    }
    private boolean testCheck(Color color) {
        List<ChessPiece> opponentPieces = piecesOnTheBoard.stream().filter(x -> x.getColor() == Opponent(color)).collect(Collectors.toList());
        Position kingPosition = king(color).getChessPosition().toPosition();
        for (ChessPiece piece : opponentPieces) {
            boolean[][] possibleMoves = piece.possibleMoves();
            if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }
 
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
            Position position = sourcePosition.toPosition();
            validateSourcePosition(position);
            return board.piece(position).possibleMoves();
    }
}
