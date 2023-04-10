package gui;

import game.Board;
import move.Move;
import move.MoveType;
import pieces.Alliance;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BoardPanel extends JPanel implements MouseListener {
    private Board board;
    private final ArrayList<TilePanel> panels;
    public BoardPanel(Board board) {
        this.board = board;
        this.panels = new ArrayList<>();

        this.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int position = i * 8 + j;

                TilePanel tilePanel;
                if ((i + j) % 2 == 0) {
                    tilePanel = new TilePanel(position, Alliance.WHITE);
                }
                else {
                    tilePanel = new TilePanel(position, Alliance.BLACK);
                }
                tilePanel.addMouseListener(this);
                this.panels.add(tilePanel);
                this.add(tilePanel);
            }
        }

        this.updatePieces(board);
    }

    public void updatePieces(Board board) {
        this.board = board;
        ArrayList<Piece> pieces = board.getPieces();

        for (TilePanel panel : this.panels) {
            panel.removePiece();
        }

        for (Piece piece : pieces) {
            this.panels.get(piece.getPiecePosition()).setPiece(piece);
        }
        this.revalidate();
        this.repaint();
    }

    public void flipBoard(Alliance alliance) {
        if (alliance == Alliance.WHITE) {
            this.removeAll();
            for (TilePanel panel : this.panels) {
                this.add(panel);
            }
        }
        else {
            this.removeAll();
            for (int i = 0; i < this.panels.size(); i++) {
                this.add(panels.get(this.panels.size() - 1 - i));
            }
        }

        this.revalidate();
        this.repaint();
    }

    public void addTileListener(MouseListener tileListener) {
        for (TilePanel panel : this.panels) {
            panel.addMouseListener(tileListener);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        TilePanel tilePanel = (TilePanel) e.getSource();
        tilePanel.highlightRed();

        if (tilePanel.hasPiece()) {
            Piece tilePiece = tilePanel.getPiece();
            ArrayList<Move> validMoves = this.board.getValidMoves(tilePiece.getPieceAlliance());
            for (Move move : validMoves) {
                Piece movePiece = move.getMovePiece();
                if (movePiece.equals(tilePiece)) {
                    if (move.getMoveType() == MoveType.CAPTURE_MOVE || move.getMoveType() == MoveType.QUEEN_CASTLE_MOVE || move.getMoveType() == MoveType.KING_CASTLE_MOVE) {
                        this.panels.get(move.getDestination()).highlightRed();
                    }
                    else if (move.getMoveType() == MoveType.MOVE) {
                        this.panels.get(move.getDestination()).highlightBlue();
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (TilePanel panel : this.panels) {
            panel.reset();
        }
    }
}
