package move;

import game.Board;
import pieces.Alliance;
import pieces.King;
import pieces.Piece;
import pieces.Rook;

import java.util.ArrayList;

public class QueenCastle extends Move {

    private final Rook castleRook;
    public QueenCastle(Rook castleRook, King moveKing, int destination, Board board) {
        super(moveKing, destination, board);
        this.castleRook = castleRook;
    }

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
                newPiece.setPosition(2 + offset);
                newPiece = Move.correctPieceData(newPiece);
            }
            else if (newPiece.equals(this.castleRook)) {
                newPiece.setPosition(3 + offset);
                newPiece = Move.correctPieceData(newPiece);
            }
            board.addPiece(newPiece);
        }

        return board;
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.QUEEN_CASTLE_MOVE;
    }

    @Override
    public String toString() {
        return "O-O-O";
    }
}
