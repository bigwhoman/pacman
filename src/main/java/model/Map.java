package model;

public class Map {
    private static Map maze;
    private int rows;
    private int columns;


    private Map() {

    }

    public static Map getMap() {
        if (maze == null)
            maze = new Map();
        return maze;
    }

    public Cell[][] mapInit() {
        Cell[][] map;
        rows = 10;
        columns = 10;
        map = new Cell[2 * rows + 1][2 * columns + 1];
        for (int j = 0; j < 2 * rows + 1; j++)
            for (int k = 0; k < 2 * columns + 1; k++) {
                map[j][k] = new Cell("1");
            }
        map[1][1].setType("*");
        map[0][1].setType("1");
        map[2 * rows][2 * columns - 1].setType("1");
        DFS(1, 1, map);
        map[4][4].setType("0");
        fixMaze(map);
        return map;
    }

    private void fixMaze(Cell[][] maze) {
        for (int i = 1; i < 2 * columns - 1; i++) {
            for (int j = 1; j < 2 * columns - 1; j++) {
                if (maze[i + 1][j + 1].getType().equals("1"))
                    maze[i][j].setType("0");
                if (maze[i][j - 1].getType().equals("1") && maze[i][j + 1].getType().equals("1") && maze[i + 1][j].getType().equals("1"))
                    maze[i][j].setType("0");
                maze[1][j].setType("0");
                maze[i][1].setType("0");
                maze[19][j].setType("0");
                maze[i][19].setType("0");
            }
        }
    }

    public void DFS(int currentRow, int currentColumn, Cell[][] Maze) {
        int movement = checkMove(currentRow, currentColumn, Maze);
        if (movement == 0) return;
        move(currentRow, currentColumn, Maze, movement);
        for (int i = 0; i < 100; i++) {
            if (movement == 1) DFS(currentRow, currentColumn - 2, Maze);
            if (movement == 2) DFS(currentRow - 2, currentColumn, Maze);
            if (movement == 3) DFS(currentRow, currentColumn + 2, Maze);
            if (movement == 4) DFS(currentRow + 2, currentColumn, Maze);
        }
    }

    public void move(int currentRow, int currentColumn, Cell[][] Maze, int movement) {
        if (movement == 1) {
            Maze[currentRow][currentColumn - 2].setType("*");
            Maze[currentRow][currentColumn - 1].setType("0");
        }
        if (movement == 2) {
            Maze[currentRow - 2][currentColumn].setType("*");
            Maze[currentRow - 1][currentColumn].setType("0");
        }
        if (movement == 3) {
            Maze[currentRow][currentColumn + 2].setType("*");
            Maze[currentRow][currentColumn + 1].setType("0");
        }
        if (movement == 4) {
            Maze[currentRow + 2][currentColumn].setType("*");
            Maze[currentRow + 1][currentColumn].setType("0");
        }
    }


    public int checkMove(int currentRow, int currentColumn, Cell[][] Maze) {
        int left = 0, right = 0, up = 0, down = 0;
        if (0 < currentColumn - 2 && !Maze[currentRow][currentColumn - 2].getType().equals("*")) left++;
        if (currentColumn + 2 < 2 * columns && !Maze[currentRow][currentColumn + 2].getType().equals("*")) right++;
        if (0 < currentRow - 2 && !Maze[currentRow - 2][currentColumn].getType().equals("*")) up++;
        if (currentRow + 2 < 2 * rows && !Maze[currentRow + 2][currentColumn].getType().equals("*")) down++;

        if (left == 0 && right == 0 && down == 0 && up == 0) return 0;

        for (int i = 0; i < 200; i++) {
            int random = (int) (Math.random() * 4) + 1;
            if (left == 1 && random == 1) return 1;
            if (right == 1 && random == 3) return 3;
            if (up == 1 && random == 2) return 2;
            if (down == 1 && random == 4) return 4;
        }
        return 0;
    }
}
