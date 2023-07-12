import java.util.Scanner;

import Boardgame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ChessMatch chess = new ChessMatch();
        while (true) {
			Ui.printBoard(chess.getPieces());
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = Ui.readChessPosition(sc);
			
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = Ui.readChessPosition(sc);
			
			ChessPiece capturedPiece = chess.performChessMove(source, target);
		}

    }
}
