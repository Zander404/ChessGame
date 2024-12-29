package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);

                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturePiece = chessMatch.performChessMove(source, target);
                if (capturePiece != null) {
                    captured.add(capturePiece);
                }

                if (chessMatch.getPromoted() != null) {
                    String type = sc.nextLine().toUpperCase();
                    if (!type.equals("B") && !type.equals("N") && !type.equals("KN") && !type.equals("R") && !type.equals("Q")) {
                        System.out.println(" Invalid Option! Enter the new piece for Promotion (B/KN/R/Q)");
                        type = sc.nextLine().toUpperCase();

                    }

                    chessMatch.replacePromotedPiece(type);
                }

            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);

    }
}