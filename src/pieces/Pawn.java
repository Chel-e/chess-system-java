package pieces;

import Chess.ChessPiece;
import Boardgame.Board;
import Boardgame.Position;
import Chess.Color;
public class Pawn extends ChessPiece{
    
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        if (getColor() == Color.WHITE) {  
            //
            p.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ 
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
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
            if (getBoard().positionExists(p)     && isThereOpponentPiece(p)) {
                mat[p.getRow()] [p.getColumn()] = true;
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
