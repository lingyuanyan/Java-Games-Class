import java.awt.*;
import java.util.Scanner;
import java.util.StringTokenizer;
public class chess {
    static piece[][] board;
    static boolean[][] attackedByWhite;
    static boolean[][] attackedByBlack;
    public static class piece {
        int nOfMoves = 0;
        boolean isBlack;
        char type;
        int x;
        int y;
        public piece(boolean isBlack, char type, int x, int y) {
            this.isBlack = isBlack;
            this.type = type;
            this.x = x;
            this.y = y;
        }
        public boolean canMove(int x, int y) {
            boolean valid = false;
            if (x == this.x && y == this.y) return valid;
            if (type == 'P') valid = canMovePawn(x, y);
            else if (type == 'N') valid = canMoveKnight(x, y);
            else if (type == 'B') valid = canMoveBishop(x, y);
            else if (type == 'R') valid = canMoveRook(x, y);
            else if (type == 'Q') valid = canMoveQueen(x, y);
            else valid = canMoveKing(x, y);
            if (valid) nOfMoves++;
            return valid;
        }
        public boolean canMovePawn(int x, int y) {
            int dir = 1;
            if (!isBlack) dir = -1;
            if (((y == this.y + dir && board[x][y] == null) && x == this.x) || (y == this.y + dir && board[x][y] != null && Math.abs(x - this.x) == 1 && board[x][y].isBlack != this.isBlack)) return true;
            if (y == this.y + dir && board[x][y] != null && Math.abs(x - this.x) == 1 && board[x][this.y].isBlack != this.isBlack && board[x][this.y].nOfMoves == 1 && this.y == 4 && board[x][this.y].type == 'P'){
                board[x][this.y] = null;
                return true;
            }
            return false;
        }
        public boolean canMoveKnight(int x, int y) {
            int movedX = Math.abs(x - this.x);
            int movedY = Math.abs(y - this.y);
            return (movedX == 1 && movedY == 2) || (movedX == 2 && movedY == 1);
        }
        public boolean canMoveBishop(int x, int y) {
            if (Math.abs(x - this.x) != Math.abs(y - this.y)) return false;
            int moveX = 1;
            int moveY = 1;
            if (x < this.x) moveX = -1;
            if (y < this.y) moveY = -1;
            int i = this.x;
            int j = this.y;
            while (board[i][j] == null) {
                i += moveX;
                j += moveY;
                if (i == x && j == y) return true;
            }
            return false;

        }
        public boolean canMoveRook(int x, int y) {
            if (x == this.x || y == this.y) {
                int moveX = 0;
                int moveY = 0;
                if (x < this.x) moveX = -1;
                else if (x > this.x) moveX = 1;
                else if (y < this.y) moveY = -1;
                else if (y > this.y) moveY = 1;
                int i = this.x;
                int j = this.y;
                while (board[i][j] == null) {
                    i += moveX;
                    j += moveY;
                    if (i == x && j == y) return true;
                }
            }
            return false;
        }
        public boolean canMoveQueen(int x, int y) {
            return canMoveBishop(x, y) || canMoveRook(x, y);
        }
        public boolean canMoveKing(int x, int y) {
            return  (Math.abs(this.x - x) == 1 && Math.abs(this.y-y) == 1);
        }

    }


    public static void main(String[] args) {
        boolean blackTurn = false;
        board = new piece[8][8];
        Scanner input = new Scanner(System.in);
        boolean ended = false;
        System.out.println("Start:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) System.out.print("   ");
                else System.out.print(" " + board[i][j].type + " ");
            }
        }
        while (ended) {
            String line = input.nextLine();
            int sc = line.charAt(0)-97;
            int sr =  Character.getNumericValue(line.charAt(2));
            line = input.nextLine();
            int tc = line.charAt(0)-97;
            int tr =  Character.getNumericValue(line.charAt(2));
            if (board[sc][sr].isBlack != blackTurn || !board[sc][sr].canMove(tc, tr)) {
                System.out.println("Invalid Move PLease try again...");
                continue;
            }
            board[tc][tr] = board[sc][sr];
            board[sc][sr] = null;
        }
    }
}
