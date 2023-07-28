package pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;

public class King extends ChessPiece{
    private ChessMatch chessmatch;
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessmatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean[][] canMove(Position p, boolean[][] mat){

        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            return mat;
        } else if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            return mat;
        } else {
            return mat;
        }
    }
    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        // nw
        p.setValues(this.position.getRow() - 1, this.position.getColumn() -1);
        mat = canMove(p, mat);
        // above 
        p.setValues(this.position.getRow() - 1, this.position.getColumn());
        mat = canMove(p, mat);
        // ne
        p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        mat = canMove(p, mat);
        // left
        p.setValues(this.position.getRow(), this.position.getColumn() - 1);
        mat = canMove(p, mat);
        // right 
        p.setValues(this.position.getRow(), this.position.getColumn() + 1);
        mat = canMove(p, mat);
        //sw 
        p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        mat = canMove(p, mat);
        // below
        p.setValues(this.position.getRow() + 1, this.position.getColumn());
        mat = canMove(p, mat);
        //se
        p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        mat = canMove(p, mat);
        //#especia Move Castling 
        if (getMoveCount() == 0 && !chessmatch.getCheck()) {
            // #especialmove Castling kingside rook
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new  Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new  Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;  
                }
            }
            // #especialmove Castling Queenside rook
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new  Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new  Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new  Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;  
                }
            }

        }
        return mat;
    }

    
}
