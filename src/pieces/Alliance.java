package pieces;

import java.awt.*;

public enum Alliance {
    WHITE {
        @Override
        public Alliance getOpponent() {
            return BLACK;
        }

        @Override
        public Color getColor() {
            return new Color(100, 100, 100);
        }

        @Override
        public String toString() {
            return "w";
        }
    },
    BLACK {
        @Override
        public Alliance getOpponent() {
            return WHITE;
        }

        @Override
        public Color getColor() {
            return new Color(50, 50, 50);
        }
        @Override
        public String toString() {
            return "b";
        }
    };

    public abstract Alliance getOpponent();
    public abstract Color getColor();
    @Override
    public abstract String toString();
}
