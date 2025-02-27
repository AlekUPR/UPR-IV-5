import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter FEN: ");
        String fen = scanner.nextLine();
        char[][] board = convertFENToBoard(fen);


        System.out.print("Enter first piece (to calculate moves): ");
        char firstPiece = scanner.nextLine().charAt(0);


        System.out.print("Enter second piece (obstacle): ");
        char secondPiece = scanner.nextLine().charAt(0);

        int[] firstPiecePos = findPiecePosition(board, firstPiece);
        int[] secondPiecePos = findPiecePosition(board, secondPiece);

        if (firstPiecePos == null || secondPiecePos == null) {
            System.out.println("One or both pieces not found on the board.");
            return;
        }

        int firstRow = firstPiecePos[0];
        int firstCol = firstPiecePos[1];


        if (Character.toLowerCase(firstPiece) == 'r') {
            findValidRookMoves(board, firstRow, firstCol);
        } else if (Character.toLowerCase(firstPiece) == 'b') {
            findValidBishopMoves(board, firstRow, firstCol);
        } else if (Character.toLowerCase(firstPiece) == 'q') {
            findValidQueenMoves(board, firstRow, firstCol);
        }


        drawBoard(board);
    }

    public static char[][] convertFENToBoard(String fen) {
        char[][] board = new char[8][8];
        String[] rows = fen.split("/");

        for (int i = 0; i < 8; i++) {
            String row = rows[i];
            int col = 0;

            for (char c : row.toCharArray()) {
                if (Character.isDigit(c)) {
                    int emptySpaces = Character.getNumericValue(c);
                    for (int k = 0; k < emptySpaces; k++) {
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
            case '0' -> "0"; // Valid move marker
            default -> ".";
        };
    }

    public static void findValidRookMoves(char[][] board, int row, int col) {
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] != '.') break;
            board[i][col] = '0';
        }
        for (int i = row + 1; i < 8; i++) {
            if (board[i][col] != '.') break;
            board[i][col] = '0';
        }
        for (int j = col - 1; j >= 0; j--) {
            if (board[row][j] != '.') break;
            board[row][j] = '0';
        }
        for (int j = col + 1; j < 8; j++) {
            if (board[row][j] != '.') break;
            board[row][j] = '0';
        }
    }

    public static void findValidBishopMoves(char[][] board, int row, int col) {
        for (int i = 1; i < 8; i++) {
            if (row + i < 8 && col + i < 8 && board[row + i][col + i] == '.')
                board[row + i][col + i] = '0';
            else break;
        }
        for (int i = 1; i < 8; i++) {
            if (row - i >= 0 && col - i >= 0 && board[row - i][col - i] == '.')
                board[row - i][col - i] = '0';
            else break;
        }
        for (int i = 1; i < 8; i++) {
            if (row + i < 8 && col - i >= 0 && board[row + i][col - i] == '.')
                board[row + i][col - i] = '0';
            else break;
        }
        for (int i = 1; i < 8; i++) {
            if (row - i >= 0 && col + i < 8 && board[row - i][col + i] == '.')
                board[row - i][col + i] = '0';
            else break;
        }
    }

    public static void findValidQueenMoves(char[][] board, int row, int col) {
        findValidRookMoves(board, row, col);
        findValidBishopMoves(board, row, col);
    }
}