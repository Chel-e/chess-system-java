package pieces;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Boardgame.Board;
import Boardgame.Position;
import Chess.Color;
public class Pawn extends ChessPiece{
    private ChessMatch chessMatch;
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        if (getColor() == Color.WHITE) {  
            //above 1 
            p.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ 
                mat[p.getRow()][p.getColumn()] = true;
            }
            //above 2 
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                if (getMoveCount() == 0 && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p)) {
                    mat[p.getRow()] [p.getColumn()] = true;
                }
            }
            // right
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()] [p.getColumn()] = true;
            }
            // left
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()] [p.getColumn()] = true;
            }
            // Especial move en passant white 
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    mat[position.getRow() - 1][position.getColumn() - 1] = true;
                }
                Position righ = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(righ) && isThereOpponentPiece(righ) && getBoard().piece(righ) == chessMatch.getEnPassantVulnerable()) {
                    mat[position.getRow() - 1][position.getColumn() + 1] = true;
                }
            }

        } else {
            //
            p.setValues(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ 
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) { 
                if (getBoard().positionExists(p) && getMoveCount() == 0 && !getBoard().thereIsAPiece(p)) {
                    mat[p.getRow()] [p.getColumn()] = true;
                }
            }
            // right
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()] [p.getColumn()] = true;
            }
            // left
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()] [p.getColumn()] = true;
            }
           // Especial move en passant black 
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    mat[position.getRow() + 1][position.getColumn() - 1] = true;
                }
                Position righ = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(righ) && isThereOpponentPiece(righ) && getBoard().piece(righ) == chessMatch.getEnPassantVulnerable()) {
                    mat[position.getRow() + 1][position.getColumn() + 1] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
