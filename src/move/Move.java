package move;

import game.Board;
import pieces.*;

import java.util.ArrayList;

public class Move {

    private Piece movePiece;
    private final int destination;
    private final Board board;

    public Move(Piece movePiece, int destination, Board board) {
        this.movePiece = movePiece;
        this.destination = destination;
        this.board = board;
    }

    public Board execute() {
        Board board = new Board();
        ArrayList<Piece> pieces = this.board.getPieces();

        Piece newPiece;
        for (Piece piece : pieces) {
            newPiece = piece.clone();
            if (piece.equals(this.movePiece)) {
                newPiece.setPosition(this.destination);
                newPiece = correctPieceData(newPiece);
            }
            board.addPiece(newPiece);
        }
        return board;
    }

    public static Piece correctPieceData(Piece newPiece) {
        if (newPiece.getPieceType() == PieceType.PAWN) {
            Pawn newPawn = (Pawn) newPiece;
            newPawn.setFirstMove(false);
            return newPawn;
        }
        else if (newPiece.getPieceType() == PieceType.KING) {
            King newKing = (King) newPiece;
            newKing.setFirstMove(false);
            return newKing;
        }
        else if (newPiece.getPieceType() == PieceType.ROOK) {
            Rook newRook = (Rook) newPiece;
            newRook.setFirstMove(false);
            return newRook;
        }
        else {
            return newPiece;
        }
    }

    public MoveType getMoveType() {
        return MoveType.MOVE;
    }
    public Piece getMovePiece() {
        return movePiece;
    }

    public void setMovePiece(Piece piece) {
        this.movePiece = piece.clone();
    }

    public int getDestination() {
        return destination;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        if (this.getMovePiece().getPieceType() == PieceType.PAWN) {
            return Board.positionToString(this.getDestination());
        }
        else {
            return this.getMovePiece().toString() + Board.positionToString(this.getDestination());
        }
    }
}
