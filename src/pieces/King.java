package pieces;

import game.Board;
import move.CaptureMove;
import move.KingCastleMove;
import move.Move;
import move.QueenCastle;

import java.util.ArrayList;

public class King extends Piece {

    private boolean firstMove;
    public King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
        this.firstMove = true;
    }

    public King(int piecePosition, Alliance pieceAlliance, boolean firstMove) {
        super(piecePosition, pieceAlliance);
        this.firstMove = firstMove;
    }

    @Override
    public ArrayList<Move> getPieceValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();

        ArrayList<Integer> offsets = new ArrayList<>();
        offsets.add(8); offsets.add(-8); offsets.add(1); offsets.add(-7); offsets.add(9);

        if (Board.getColumn(7)[this.getPiecePosition()]) {
            offsets.replaceAll(integer -> integer * -1);
        }
        else if (!Board.getColumn(0)[this.getPiecePosition()]) {
            offsets.add(-1); offsets.add(7); offsets.add(-9);
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

        int offset = 0;
        if (this.getPieceAlliance() == Alliance.WHITE) {
            offset = 56;
        }

        if (this.getPiecePosition() == (4 + offset) && this.isFirstMove()) {
            Piece piece = board.getPieceAt(offset);
            if (piece == null) {
                return validMoves;
            }

            if (piece.getPieceType() == PieceType.ROOK && piece.getPieceAlliance() == this.getPieceAlliance()) {
                Rook castleRook = (Rook) piece;
                if (castleRook.isFirstMove()) {
                    if (board.getPieceAt(1 + offset) == null && board.getPieceAt(2 + offset) == null && board.getPieceAt(3 + offset) == null) {
                        validMoves.add(new QueenCastle(castleRook, this, 2 + offset, board));
                    }
                }
            }

            piece = board.getPieceAt(7 + offset);
            if (piece == null) {
                return validMoves;
            }

            if (piece.getPieceType() == PieceType.ROOK && piece.getPieceAlliance() == this.getPieceAlliance()) {
                Rook castleRook = (Rook) piece;
                if (castleRook.isFirstMove()) {
                    if (board.getPieceAt(5 + offset) == null && board.getPieceAt(6 + offset) == null) {
                        validMoves.add(new KingCastleMove(castleRook, this, 6 + offset, board));
                    }
                }
            }
        }

        return validMoves;
    }

    @Override
    public Piece clone() {
        return new King(this.getPiecePosition(), this.getPieceAlliance(), this.firstMove);
    }

    @Override
    public boolean equals(Piece piece) {
        if (piece.getPieceType() == PieceType.KING) {
            King king = (King) piece;
            return king.getPiecePosition() == this.getPiecePosition() && king.getPieceAlliance() == this.getPieceAlliance() && king.isFirstMove() == this.isFirstMove();
        }
        return false;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
