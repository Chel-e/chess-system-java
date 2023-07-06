import Boardgame.Position;
import Chess.ChessMetch;

public class App {
    public static void main(String[] args) throws Exception {
        ChessMetch chess = new ChessMetch();
        Ui.printBoard(chess.getPieces());

    }
}
