package gui;

import pieces.Alliance;
import pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TilePanel extends JPanel{

    private final int position;
    private final Alliance alliance;

    private Piece piece;
    private boolean hasPiece;
    public TilePanel(int position, Alliance alliance) {
        this.position = position;
        this.alliance = alliance;

        this.setBackground(alliance.getColor());
    }

    public void reset() {
        this.setBackground(this.alliance.getColor());
    }

    public void highlightRed() {
        Color color = this.alliance.getColor();
        Color highlightColor = new Color(color.getRed() + 50, color.getGreen() + 30, color.getBlue() + 30);
        this.setBackground(highlightColor);
    }

    public void highlightBlue() {
        Color color = this.alliance.getColor();
        Color highlightColor = new Color(color.getRed() + 30, color.getGreen() + 30, color.getBlue() + 50);
        this.setBackground(highlightColor);
    }

    public void setPiece(Piece piece) {
        this.piece = piece.clone();
        this.hasPiece = true;

        String path = "src/piece_images/" + piece.getPieceAlliance() + piece + ".png";
        BufferedImage pieceImage;
        try {
            pieceImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel label  = new JLabel(new ImageIcon(pieceImage));
        this.add(label);
    }

    public void removePiece() {
        this.hasPiece = false;
        this.removeAll();
    }

    public int getPosition() {
        return position;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public Piece getPiece() {
        return this.piece;
    }
    public boolean hasPiece() {
        return this.hasPiece;
    }
}
