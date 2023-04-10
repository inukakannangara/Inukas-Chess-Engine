package pieces;

import game.Board;
import move.CaptureMove;
import move.Move;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public ArrayList<Move> getPieceValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();

        ArrayList<Integer> offsets = new ArrayList<>();

        if (Board.getColumn(0)[this.getPiecePosition()] || Board.getColumn(7)[this.getPiecePosition()]) {
            offsets.add(-15); offsets.add(-6); offsets.add(10); offsets.add(17);
            if (Board.getColumn(7)[this.getPiecePosition()]) {
                offsets.replaceAll(integer -> integer * -1);
            }
        }
        else if (Board.getColumn(1)[this.getPiecePosition()] || Board.getColumn(6)[this.getPiecePosition()]) {
            offsets.add(-17); offsets.add(-15); offsets.add(-6); offsets.add(10); offsets.add(17); offsets.add(15);
            if (Board.getColumn(6)[this.getPiecePosition()]) {
                offsets.replaceAll(integer -> integer * -1);
            }
        }
        else {
            offsets.add(-10); offsets.add(-17); offsets.add(-15); offsets.add(-6); offsets.add(10); offsets.add(17); offsets.add(15); offsets.add(6);
        }

        int destination;
        Piece destinationPiece;
        for (int offset : offsets) {
            destination = this.getPiecePosition() + offset;
            if (destination < 0 || destination > 63) {
                continue;
            }

            destinationPiece = board.getPieceAt(destination);

            if (destinationPiece == null) {
                validMoves.add(new Move(this, destination, board));
                continue;
            }
            if (destinationPiece.getPieceAlliance() == this.getPieceAlliance().getOpponent()) {
                validMoves.add(new CaptureMove(destinationPiece, this, destination, board));
            }
        }

        return validMoves;
    }

    @Override
    public Piece  clone() {
        return new Knight(this.getPiecePosition(), this.getPieceAlliance());
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }
}
