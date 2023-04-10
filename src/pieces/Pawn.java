package pieces;

import game.Board;
import move.CaptureMove;
import move.Move;

import java.util.ArrayList;

public class Pawn extends Piece {

    private boolean firstMove;
    public Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
        this.firstMove = true;
    }

    public Pawn(int piecePosition, Alliance pieceAlliance, boolean firstMove) {
        super(piecePosition, pieceAlliance);
        this.firstMove = firstMove;
    }

    @Override
    public ArrayList<Move> getPieceValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();
        int direction = 1;
        if (this.getPieceAlliance() == Alliance.WHITE) {
            direction = -1;
        }

        int destination = this.getPiecePosition() + 8 * direction;
        Piece destinationPiece = board.getPieceAt(destination);
        if (destinationPiece == null) {
            validMoves.add(new Move(this, destination, board));
        }

        if (this.isFirstMove()) {
            destination = this.getPiecePosition() + 16 * direction;
            destinationPiece = board.getPieceAt(destination);

            if (destinationPiece == null) {
                validMoves.add(new Move(this, destination, board));
            }
        }


        destination = this.getPiecePosition() + 9 * direction;
        destinationPiece = board.getPieceAt(destination);
        if (destinationPiece != null) {
            if (destinationPiece.getPieceAlliance() == this.getPieceAlliance().getOpponent()) {
                validMoves.add(new CaptureMove(destinationPiece, this, destination, board));
            }
        }

        destination = this.getPiecePosition() + 7 * direction;
        destinationPiece = board.getPieceAt(destination);
        if (destinationPiece != null) {
            if (destinationPiece.getPieceAlliance() == this.getPieceAlliance().getOpponent()) {
                validMoves.add(new CaptureMove(destinationPiece, this, destination, board));
            }
        }

        return validMoves;
    }

    @Override
    public Piece clone() {
        return new Pawn(this.getPiecePosition(), this.getPieceAlliance(), this.firstMove);
    }

    @Override
    public boolean equals(Piece piece) {
        if (piece.getPieceType() == PieceType.PAWN) {
            Pawn pawn = (Pawn) piece;
            return pawn.getPiecePosition() == this.getPiecePosition() && pawn.getPieceAlliance() == this.getPieceAlliance() && pawn.isFirstMove() == this.isFirstMove();
        }
        return false;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }
    public void setFirstMove(boolean isFirstMove) {
        this.firstMove = isFirstMove;
    }
}
