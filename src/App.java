import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<ChessPiece> captured = new ArrayList<>();
        while (!chess.getCheckMate()) {
			try {
				Ui.clearScreen();
				Ui.printMatch(chess, captured);
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
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				if (chess.getPromoted() != null) {
					System.out.print("Enter piece for promotion: (B/N/R/Q)");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("Q") && !type.equals("N") && !type.equals("R")) {
					System.out.print("Invalid value !Enter piece for promotion: (B/N/R/Q)");
					type = sc.nextLine().toUpperCase();
					}
					chess.replacePromotedPiece(type);
				}
			 } catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		Ui.clearScreen();
		Ui.printMatch(chess, captured);
    }
}
