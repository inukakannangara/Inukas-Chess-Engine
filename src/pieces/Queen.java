package pieces;

import game.Board;
import move.Move;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public ArrayList<Move> getPieceValidMoves(Board board) {
        ArrayList<Move> validMoves;

        Bishop bishop = new Bishop(this.getPiecePosition(), this.getPieceAlliance());
        Rook rook = new Rook(this.getPiecePosition(), this.getPieceAlliance());

        validMoves = bishop.getPieceValidMoves(board);
        ArrayList<Move> rookValidMoves = rook.getPieceValidMoves(board);
        validMoves.addAll(rookValidMoves);

        for (Move move : validMoves) {
            move.setMovePiece(this);
        }

        return validMoves;
    }

    @Override
    public Piece clone() {
        return new Queen(this.getPiecePosition(), this.getPieceAlliance());
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }
}
