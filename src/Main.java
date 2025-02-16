import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter FEN: ");
        String fen = scanner.nextLine();

        char[][] board = convertFENToBoard(fen);

        System.out.print("Enter piece to find: ");
        char piece = scanner.nextLine().charAt(0);

        int[] piecePosition = findPiecePosition(board, piece);
        if (piecePosition != null) {
            int pieceRow = piecePosition[0];
            int pieceCol = piecePosition[1];
            //findValidRookMoves(board, pieceRow, pieceCol);
            //findValidBishopMoves(board, pieceRow, pieceCol);
            //findValidKnightMoves(board, pieceRow, pieceCol);
            //findValidKingMoves(board, pieceRow, pieceCol);
            //findValidPawnMoves(board, pieceRow, pieceCol);
            findValidQueenMoves(board, pieceRow, pieceCol);
            drawBoard(board);
        } else {
            System.out.println("Piece not found on the board:");
        }
    }

    public static char[][] convertFENToBoard(String fen) {
        char[][] board = new char[8][8];
        String[] rows = fen.split("/");

        for (int i = 0; i < 8; i++) {
            String row = rows[i];
            int col = 0;

            for (int j = 0; j < row.length(); j++) {
                char c = row.charAt(j);
                if (Character.isDigit(c)) {
                    for (int k = 0; k < Character.getNumericValue(c); k++) {
                        board[i][col++] = '.';
                    }
                } else {
                    board[i][col++] = c;
                }
            }
        }
        return board;
    }

    public static int[] findPiecePosition(char[][] board, char piece) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == piece) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static void drawBoard(char[][] board) {
        for (char[] row : board) {
            for (char piece : row) {
                System.out.print(getEmojiForPiece(piece) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String getEmojiForPiece(char piece) {
        return switch (piece) {
            case 'K' -> "♔";
            case 'Q' -> "♕";
            case 'R' -> "♖";
            case 'B' -> "♗";
            case 'N' -> "♘";
            case 'P' -> "♙";
            case 'k' -> "♚";
            case 'q' -> "♛";
            case 'r' -> "♜";
            case 'b' -> "♝";
            case 'n' -> "♞";
            case 'p' -> "♟";
            case '.' -> ".";
            case '0' -> "0";
            default -> ".";
        };
    }

    // VALIDNI DVIZENJA NA TOPOT
    // 8/8/8/8/3R4/8/8/8
    // 8/8/8/8/8/8/7r/8
    // 8/2r5/8/8/8/8/8/8
    // 8/8/4R3/8/8/8/8/8
    // 8/8/8/8/8/8/8/7R
    public static void findValidRookMoves(char[][] board, int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == row && j == col) {
                    continue;
                }

                if (i == row) {
                    board[i][j] = '0';
                } else if (j == col) {
                    board[i][j] = '0';
                }
            }
        }
    }

    // VALIDNI DVIZENJA NA LANFEROT
    // 8/1B6/8/8/8/8/8/8
    // B7/8/8/8/8/8/8/8
    // 8/8/8/8/8/8/7b/8
    // 8/8/8/8/2b5/8/8/8
    // 8/8/8/8/8/8/3B4/8
    public static void findValidBishopMoves(char[][] board, int row, int col) {
        for (int i = 0; i < 8; i++) {
            if (row + i < 8 && col + i < 8 && board[row + i][col + i] == '.')
                board[row + i][col + i] = '0';

            if (row - i >= 0 && col - i >= 0 && board[row - i][col - i] == '.')
                board[row - i][col - i] = '0';
        }

        for (int i = 0; i < 8; i++) {
            if (row + i < 8 && col - i >= 0 && board[row + i][col - i] == '.')
                board[row + i][col - i] = '0';

            if (row - i >= 0 && col + i < 8 && board[row - i][col + i] == '.')
                board[row - i][col + i] = '0';
        }
    }

    // VALIDNI DVIZENJA NA KONJOT
    // 8/8/8/8/8/8/5N2/8
    // 8/1n6/8/8/8/8/8/8
    // 8/8/8/8/8/8/N7/8
    // 8/8/8/3n4/8/8/8/8
    // 8/8/8/8/8/8/8/7N
    public static void findValidKnightMoves(char[][] board, int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (row - 2 == i && (col - 1 == j || col + 1 == j)) {
                    board[i][j] = '0';
                }
                if (row + 2 == i && (col - 1 == j || col + 1 == j)) {
                    board[i][j] = '0';
                }
                if (col - 2 == j && (row - 1 == i || row + 1 == i)) {
                    board[i][j] = '0';
                }
                if (col + 2 == j && (row - 1 == i || row + 1 == i)) {
                    board[i][j] = '0';
                }
            }
        }
    }

    // VALIDNI DVIZENJA NA KRALOT
    // 8/8/8/8/8/8/5k2/8
    // 8/1K6/8/8/8/8/8/8
    // 8/8/8/8/8/8/K7/8
    // 8/8/8/3k4/8/8/8/8
    // 8/8/8/8/8/8/8/7k
    public static void findValidKingMoves(char[][] board, int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (row - 1 == i && (col - 1 == j || col + 1 == j || col == j)) {
                    board[i][j] = '0';
                }
                if (row == i && (col - 1 == j || col + 1 == j)) {
                    board[i][j] = '0';
                }
                if (row + 1 == i && (col - 1 == j || col + 1 == j || col == j)) {
                    board[i][j] = '0';
                }
            }
        }
    }

    // VALIDNI DVIZENJA NA PIONOT
    // 8/8/8/8/8/8/5p2/8
    // 8/1P6/8/8/8/8/8/8
    // 8/8/8/8/8/8/P7/8
    // 8/8/8/3p4/8/8/8/8
    // 8/8/8/8/8/8/8/7P
    public static void findValidPawnMoves(char[][] board, int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (row - 1 == i && (col - 1 == j || col + 1 == j || col == j)) {
                    board[i][j] = '0';
                }
                if (row - 2 == i && (col == j)) {
                    board[i][j] = '0';
                }
            }
        }
    }

    // VALIDNI DVIZENJA NA KRALICATA
    // 8/8/8/8/8/8/5Q2/8
    // 8/3q4/8/8/8/8/8/8
    // 1q6/8/8/8/8/8/8/8
    // 8/8/8/8/8/4Q3/8/8
    // 8/8/8/8/8/8/8/2Q5
    public static void findValidQueenMoves(char[][] board, int row, int col) {
        findValidBishopMoves(board, row, col);
        findValidRookMoves(board, row, col);
    }
}