package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessMatch {
    private Board board;
    private int turn;
    private boolean check;
    private boolean checkMate;
    private Color currentPlayer;
    private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private List<ChessPiece> capturedPieces = new ArrayList<>();
    private ChessPiece enPassantVulnerable;
    public ChessMatch() {
        turn = 1;
        currentPlayer = Color.WHITE;
        board = new Board(8, 8);
        initialSetup();
    }
    public int getTurn() {
        return this.turn;
    }
    
    public boolean getCheckMate() {
        return checkMate;
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
    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }
    private void PlaceNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    public void initialSetup() {
        PlaceNewPiece('a', 1, new Rook(board, Color.WHITE));
        PlaceNewPiece('b', 1, new Knight(board, Color.WHITE));
        PlaceNewPiece('c', 1, new Bishop(board, Color.WHITE));
        PlaceNewPiece('d', 1, new Queen(board, Color.WHITE));
        PlaceNewPiece('e', 1, new King(board, Color.WHITE, this));
        PlaceNewPiece('f', 1, new Bishop(board, Color.WHITE));
        PlaceNewPiece('g', 1, new Knight(board, Color.WHITE));
        PlaceNewPiece('h', 1, new Rook(board, Color.WHITE));
        PlaceNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        PlaceNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        PlaceNewPiece('a', 8, new Rook(board, Color.BLACK));
        PlaceNewPiece('b', 8, new Knight(board, Color.BLACK));
        PlaceNewPiece('c', 8, new Bishop(board, Color.BLACK));
        PlaceNewPiece('d', 8, new Queen(board, Color.BLACK));
        PlaceNewPiece('e', 8, new King(board, Color.BLACK, this));
        PlaceNewPiece('f', 8, new Bishop(board, Color.BLACK));
        PlaceNewPiece('g', 8, new Knight(board, Color.BLACK));
        PlaceNewPiece('h', 8, new Rook(board, Color.BLACK));
        PlaceNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        PlaceNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
        ValidateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
        if (testCheck(getCurrentPlayer())) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("you can`t put yourself in check");
        }
        ChessPiece movedPiece = (ChessPiece) board.piece(target);
        check = (testCheck(Opponent(getCurrentPlayer()))) ? true : false;
		if (testCheckMate(Opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else{
            enPassantVulnerable = null;
        }
    
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
	    ChessPiece p = (ChessPiece)board.removePiece(source);
        p.increasMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add((ChessPiece) capturedPiece);
        }

        //Especial move castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2 ) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increasMoveCount();
        }
         //Especial move castling Queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2 ) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increasMoveCount();
        }
        //Especial move en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add((ChessPiece) capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }
		return capturedPiece;
	}
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece)board.removePiece(target);
        p.decreasMoveCount();
        board.placePiece(p, source);
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add((ChessPiece) capturedPiece);
        }
        //Especial move castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2 ) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreasMoveCount();
        }
         //Especial move castling Queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2 ) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreasMoveCount();
        }
        //Especial move en passant
         if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
               
            }
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
    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<ChessPiece> pieces = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
        for (ChessPiece p : pieces) {
            boolean[][] possibleMoves = p.possibleMoves();
            for (int i = 0; i < possibleMoves.length; i++) {
                for (int j = 0; j < possibleMoves.length; j++) {
                    if (possibleMoves[i][j] == true) {
                        Position target = new Position(i, j);
                        Position source = p.getChessPosition().toPosition();
                        Piece captured = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, captured);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
 
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }
}
