package move;

import game.Board;
import pieces.*;

import java.util.ArrayList;

public class CaptureMove extends Move {
    private final Piece capturePiece;
    public CaptureMove(Piece capturePiece, Piece movePiece, int destination, Board board) {
        super(movePiece, destination, board);
        this.capturePiece = capturePiece.clone();
    }
    @Override
    public Board execute() {
        Board board = new Board();
        ArrayList<Piece> pieces = this.getBoard().getPieces();

        Piece newPiece;
        for (Piece piece : pieces) {
            newPiece = piece.clone();
            if (newPiece.equals(this.capturePiece)) {
                continue;
            }

            if (piece.equals(this.getMovePiece())) {
                newPiece.setPosition(this.getDestination());
                newPiece = Move.correctPieceData(newPiece);
            }
            board.addPiece(newPiece);
        }
        return board;
    }

    public Piece getCapturePiece() {
        return this.capturePiece;
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.CAPTURE_MOVE;
    }

    @Override
    public String toString() {
        if (this.getMovePiece().getPieceType() == PieceType.PAWN) {
            String position = Board.positionToString(this.getMovePiece().getPiecePosition());
            String column = position.substring(0, 1);

            return column + "x" + Board.positionToString(this.getDestination());
        }
        else {
            return this.getMovePiece().toString() + "x" + Board.positionToString(this.getDestination());
        }
    }
}
