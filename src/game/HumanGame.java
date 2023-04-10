package game;

import gui.BoardPanel;
import gui.TilePanel;
import move.Move;
import pieces.Alliance;
import pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HumanGame implements MouseListener {

    protected Board gameBoard;
    protected final BoardPanel boardPanel;
    protected Alliance currentPlayer = Alliance.WHITE;

    protected ArrayList<Move> whiteMoves;
    protected ArrayList<Move> blackMoves;

    private double winsWhite = 0;
    private double winsBlack = 0;

    protected int selectedTile = -1;
    private JFrame gameFrame;

    public HumanGame() {
        this.gameBoard = new Board();
        this.gameBoard.setDefaultLayout();

        this.boardPanel = new BoardPanel(gameBoard);
        this.boardPanel.addTileListener(this);

        this.whiteMoves = new ArrayList<>();
        this.blackMoves = new ArrayList<>();
    }

    public void start() {
        this.gameFrame = new JFrame("Deserted Chess | " + this.winsWhite + " - " + this.winsBlack);
        try {
            BufferedImage icon = ImageIO.read(new File("src/piece_images/wB.png"));
            this.gameFrame.setIconImage(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameFrame.add(this.boardPanel);

        this.gameFrame.setSize(600, 600);
        this.gameFrame.setResizable(false);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);
    }

    public void end(Alliance winner) {
        if (winner == Alliance.WHITE) {
            this.winsWhite++;
            this.gameFrame.setTitle("Deserted Chess | " + this.winsWhite + " - " + this.winsBlack);
        }
        if (winner == Alliance.BLACK) {
            this.winsBlack++;
            this.gameFrame.setTitle("Deserted Chess | " + this.winsWhite + " - " + this.winsBlack);
        }
        if (winner == null) {
            this.winsWhite += 0.5;
            this.winsBlack += 0.5;
            this.gameFrame.setTitle("Deserted Chess | " + this.winsWhite + " - " + this.winsBlack);
        }

        Board board = new Board();
        board.setDefaultLayout();

        this.currentPlayer = Alliance.WHITE;
        this.gameBoard = board;

        this.whiteMoves = new ArrayList<>();
        this.blackMoves = new ArrayList<>();
        this.boardPanel.updatePieces(board);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        TilePanel panel = (TilePanel) e.getSource();
        if (selectedTile == -1) {
            for (Piece piece : this.gameBoard.getPieces()) {
                if (piece.getPiecePosition() == panel.getPosition() && piece.getPieceAlliance() == this.currentPlayer) {
                    this.selectedTile = panel.getPosition();
                    return;
                }
            }
        }
        else {
            Piece selectedPiece = null;
            for (Piece piece : this.gameBoard.getPieces()) {
                if (piece.getPiecePosition() == this.selectedTile) {
                    selectedPiece = piece;
                }
            }

            ArrayList<Move> validMoves = this.gameBoard.getValidMoves(selectedPiece.getPieceAlliance());
            for (Move move : validMoves) {
                if (move.getMovePiece().equals(selectedPiece) && move.getDestination() == panel.getPosition()) {
                    this.gameBoard = move.execute();

                    if (this.currentPlayer == Alliance.WHITE) {
                        this.whiteMoves.add(move);
                    }
                    else {
                        this.blackMoves.add(move);
                    }

                    this.boardPanel.updatePieces(this.gameBoard);
                    this.selectedTile = -1;
                    this.currentPlayer = this.currentPlayer.getOpponent();

                    if (this.gameBoard.isCheckMate(this.currentPlayer)) {
                        this.end(this.currentPlayer.getOpponent());
                    }

                    if (this.gameBoard.isStaleMate(this.currentPlayer)) {
                        this.end(null);
                    }

                    this.boardPanel.flipBoard(this.currentPlayer);

                    return;
                }
            }

            for (Piece piece : this.gameBoard.getPieces()) {
                if (piece.getPiecePosition() == panel.getPosition()) {
                    this.selectedTile = panel.getPosition();
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
