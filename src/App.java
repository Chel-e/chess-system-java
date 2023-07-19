import java.util.InputMismatchException;
import java.util.Scanner;

import Boardgame.Position;
import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ChessMatch chess = new ChessMatch();
        while (true) {
			try {
				Ui.clearScreen();
				Ui.printMatch(chess);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = Ui.readChessPosition(sc);
				boolean[][] possibleMoves = chess.possibleMoves(source);
				Ui.clearScreen();
				Ui.printBoard(chess.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = Ui.readChessPosition(sc);
				
				ChessPiece capturedPiece = chess.performChessMove(source, target);
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
    }
}
