package pieces;

import game.Board;
import move.Move;

import java.util.ArrayList;

public abstract class Piece {
    private int piecePosition;
    private final Alliance pieceAlliance;

    public Piece(int piecePosition, Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
    }

    public static Piece generatePiece(PieceType type, int piecePosition, Alliance alliance) {
        switch (type) {
            case PAWN -> {
                return new Pawn(piecePosition, alliance);
            }
            case ROOK -> {
                return new Rook(piecePosition, alliance);
            }
            case KNIGHT -> {
                return new Knight(piecePosition, alliance);
            }
            case BISHOP -> {
                return new Bishop(piecePosition, alliance);
            }
            case QUEEN -> {
                return new Queen(piecePosition, alliance);
            }
            case KING -> {
                return new King(piecePosition, alliance);
            }
            default -> {
                return null;
            }
        }
    }

    public abstract ArrayList<Move> getPieceValidMoves(Board board);

    public int getPiecePosition() {
        return piecePosition;
    }

    public void setPosition(int position) {
        this.piecePosition = position;
    }

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }

    public abstract Piece clone();

    public abstract String toString();

    public abstract PieceType getPieceType();
    public boolean equals(Piece piece) {
        return piece.getPieceType() == this.getPieceType() && piece.getPiecePosition() == this.getPiecePosition() && piece.getPieceAlliance() == this.getPieceAlliance();
    }
}
