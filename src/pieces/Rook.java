package pieces;

import game.Board;
import move.CaptureMove;
import move.Move;

import java.util.ArrayList;

public class Rook extends Piece {

    private boolean firstMove;
    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
        this.firstMove = true;
    }

    public Rook(int piecePosition, Alliance pieceAlliance, boolean firstMove) {
        super(piecePosition, pieceAlliance);
        this.firstMove = firstMove;
    }

    @Override
    public ArrayList<Move> getPieceValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();

        int[] verticalOffsets = {8, -8};
        int destination;
        Piece destinationPiece;
        for (int offset : verticalOffsets) {
            destination = this.getPiecePosition() - offset;
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
                destination -= offset;
            }
        }

        destination = this.getPiecePosition() - 1;
        while ((destination + 1) % 8 != 0) {
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

            destination -= 1;
        }

        destination = this.getPiecePosition() + 1;
        while (destination % 8 != 0) {
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

            destination += 1;
        }

        return validMoves;
    }

    @Override
    public Piece clone() {
        return new Rook(this.getPiecePosition(), this.getPieceAlliance(), this.firstMove);
    }

    @Override
    public boolean equals(Piece piece) {
        if (piece.getPieceType() == PieceType.ROOK) {
            Rook rook = (Rook) piece;
            return piece.getPiecePosition() == this.getPiecePosition() && piece.getPieceAlliance() == this.getPieceAlliance() && rook.firstMove == this.firstMove;
        }
        return false;
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
