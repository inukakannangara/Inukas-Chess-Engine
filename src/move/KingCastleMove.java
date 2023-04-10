package move;

import game.Board;
import pieces.Alliance;
import pieces.Piece;
import pieces.Rook;

import java.util.ArrayList;

public class KingCastleMove extends Move {

    private final Rook castleRook;
    public KingCastleMove(Rook castleRook, Piece movePiece, int destination, Board board) {
        super(movePiece, destination, board);
        this.castleRook = castleRook;
    }

    @Override
    public Board execute() {
        Board board = new Board();
        ArrayList<Piece> pieces = this.getBoard().getPieces();

        int offset = 0;
        if (this.getMovePiece().getPieceAlliance() == Alliance.WHITE) {
            offset = 56;
        }

        Piece newPiece;
        for (Piece piece : pieces) {
            newPiece = piece.clone();
            if (newPiece.equals(this.getMovePiece())) {
                newPiece.setPosition(6 + offset);
                newPiece = Move.correctPieceData(newPiece);
            }
            else if (newPiece.equals(this.castleRook)) {
                newPiece.setPosition(5 + offset);
                newPiece = Move.correctPieceData(newPiece);
            }
            board.addPiece(newPiece);
        }

        return board;
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.KING_CASTLE_MOVE;
    }

    @Override
    public String toString() {
        return "O-O";
    }
}
