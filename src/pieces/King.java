package pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class King extends ChessPiece{
    public King(Board board, Color color) {
        super(board, color);
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
        return mat;
      
    }

    
}
