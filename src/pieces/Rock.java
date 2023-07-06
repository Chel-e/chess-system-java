package pieces;

import Boardgame.Board;
import Chess.ChessPiece;
import Chess.Color;

public class Rock extends ChessPiece{
    public Rock(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
    
}
