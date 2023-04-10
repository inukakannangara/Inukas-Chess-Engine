package pieces;

import game.Board;
import move.CaptureMove;
import move.Move;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public ArrayList<Move> getPieceValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();

        ArrayList<Integer> offsets = new ArrayList<>();
        offsets.add(-9); offsets.add(-7); offsets.add(9); offsets.add(7);

        if (Board.getColumn(0)[this.getPiecePosition()]) {
            offsets = new ArrayList<>();
            offsets.add(-7); offsets.add(9);
        }
        else if (Board.getColumn(7)[this.getPiecePosition()]) {
            offsets = new ArrayList<>();
            offsets.add(7); offsets.add(-9);
        }

        Piece destinationPiece;
        for (int offset : offsets) {
            int destination = this.getPiecePosition() + offset;
            while (destination >= 0 && destination <= 63) {
                destinationPiece = board.getPieceAt(destination);

                if (destinationPiece == null) {
                    validMoves.add(new Move(this, destination, board));
                }
                else if (destinationPiece.getPieceAlliance() == this.getPieceAlliance().getOpponent()) {
                    validMoves.add(new CaptureMove(destinationPiece, this, destination, board));
                    break;
                }
                else if (destinationPiece.getPieceAlliance() == this.getPieceAlliance()) {
                    break;
                }

                if (Board.getColumn(0)[destination] || Board.getColumn(7)[destination]) {
                    break;
                }

                destination += offset;
            }
        }

        return validMoves;
    }

    @Override
    public Piece clone() {
        return new Bishop(this.getPiecePosition(), this.getPieceAlliance());
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }
}
