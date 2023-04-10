package game;

import gui.BoardPanel;
import move.CaptureMove;
import move.Move;
import move.MoveType;
import pieces.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap<Integer, Piece> pieces;
    public Board() {
        this.pieces = new HashMap<>();
    }

    public ArrayList<Move> getValidMoves(Alliance alliance) {
        ArrayList<Move> validMoves = new ArrayList<>();

        ArrayList<Move> pieceValidMoves;
        for (Piece piece : this.pieces.values()) {
            if (piece.getPieceAlliance() == alliance) {
                pieceValidMoves = piece.getPieceValidMoves(this);
                for (Move move : pieceValidMoves) {
                    Board moveBoard = move.execute();
                    if (moveBoard.isInCheck(alliance)) {
                        continue;
                    }
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
    }

    public boolean isInCheck(Alliance alliance) {
        Piece king = null;
        ArrayList<Move> validOpponentMoves = new ArrayList<>();

        for (Piece piece : this.pieces.values()) {
            if (piece.getPieceAlliance() == alliance && piece.getPieceType() == PieceType.KING) {
                king = piece;
            }
            else if (piece.getPieceAlliance() == alliance.getOpponent()){
                ArrayList<Move> pieceMoves = piece.getPieceValidMoves(this);
                validOpponentMoves.addAll(pieceMoves);
            }
        }

        if (king == null) {
            return false;
        }

        for (Move move : validOpponentMoves) {
            if (move.getMoveType() == MoveType.CAPTURE_MOVE) {
                CaptureMove captureMove = (CaptureMove) move;
                if (captureMove.getCapturePiece().equals(king)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCheckMate(Alliance alliance) {
        ArrayList<Move> validMoves = this.getValidMoves(alliance);
        return validMoves.size() == 0 && this.isInCheck(alliance);
    }

    public boolean isStaleMate(Alliance alliance) {
        ArrayList<Move> validMoves = this.getValidMoves(alliance);
        return validMoves.size() == 0;
    }

    public int score() {
        if (this.isCheckMate(Alliance.WHITE)) {
            return Integer.MIN_VALUE;
        }
        else if (this.isCheckMate(Alliance.BLACK)) {
            return Integer.MAX_VALUE;
        }
        else if (this.isStaleMate(Alliance.WHITE) || this.isStaleMate(Alliance.BLACK)) {
            return 0;
        }

        int score = 0;

        score += (this.countPieces(PieceType.ROOK, Alliance.WHITE) * 50);
        score -= (this.countPieces(PieceType.ROOK, Alliance.BLACK) * 50);

        score += (this.countPieces(PieceType.KNIGHT, Alliance.WHITE) * 29);
        score -= (this.countPieces(PieceType.KNIGHT, Alliance.BLACK) * 29);

        score += (this.countPieces(PieceType.BISHOP, Alliance.WHITE) * 31);
        score -= (this.countPieces(PieceType.BISHOP, Alliance.BLACK) * 31);

        score += (this.countPieces(PieceType.QUEEN, Alliance.WHITE) * 90);
        score -= (this.countPieces(PieceType.QUEEN, Alliance.BLACK) * 90);

        score += (this.countPieces(PieceType.PAWN, Alliance.WHITE) * 10);
        score -= (this.countPieces(PieceType.PAWN, Alliance.BLACK) * 10);

        return score;
    }

    public void setDefaultLayout() {
        this.pieces = new HashMap<>();

        for (int i = 8; i <= 15; i++) {
            this.pieces.put(i, (new Pawn(i, Alliance.BLACK)));
            this.pieces.put(i + 40, (new Pawn(i + 40, Alliance.WHITE)));
        }

        PieceType type = null;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0 -> type = PieceType.ROOK;
                case 1 -> type = PieceType.KNIGHT;
                case 2 -> type = PieceType.BISHOP;
            }
            int[] positions = {i, 7 - i};
            for (int position : positions) {
                this.pieces.put(position, Piece.generatePiece(type, position, Alliance.BLACK));
                this.pieces.put(position + 56, Piece.generatePiece(type, position + 56, Alliance.WHITE));
            }
        }

        this.pieces.put(3, new Queen(3, Alliance.BLACK));
        this.pieces.put(59, new Queen(59, Alliance.WHITE));
        this.pieces.put(4, new King(4, Alliance.BLACK));
        this.pieces.put(60, new King(60, Alliance.WHITE));
    }

    public void addPiece(Piece piece) {
        this.pieces.put(piece.getPiecePosition(), piece);
    }

    public void displayBoard() {
        JFrame frame = new JFrame();
        BoardPanel panel = new BoardPanel(this);
        frame.add(panel);

        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public Board clone() {
        Board board = new Board();

        for (Piece piece : this.pieces.values()) {
            board.pieces.put(piece.getPiecePosition(), piece.clone());
        }

        return board;
    }

    public Piece getPieceAt(int position) {
        return this.pieces.get(position);
    }

    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.addAll(this.pieces.values());

        return pieces;
    }

    public int countPieces(PieceType pieceType, Alliance alliance) {
        int count = 0;
        for (Piece piece : this.pieces.values()) {
            if (piece.getPieceType() == pieceType && piece.getPieceAlliance() == alliance) {
                count++;
            }
        }
        return count;
    }

    public static String positionToString(int position) {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};

        int numRows = position / 8;
        int numColumns = position % 8;

        return letters[numColumns] + (8 - numRows);
    }

    public static boolean[] getColumn(int index) {
        boolean[] board = new boolean[64];

        for (int i = 0; i < 8; i++) {
            board[index + i * 8] = true;
        }

        return board;
    }
}
