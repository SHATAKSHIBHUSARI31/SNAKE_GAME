import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class SnakeGame 
{
    private static final int GRID_SIZE = 10;
    private static final char EMPTY_CELL = '.';
    private static final char SNAKE_BODY = 'o';
    private static final char SNAKE_HEAD = 'O';
    private static final char FOOD = '*';

    private char[][] grid;
    private LinkedList<Point> snake;
    private Point food;
    private Direction currentDirection;

    private enum Direction 
    {
        UP, DOWN, LEFT, RIGHT
    }

    private class Point 
    {
        int x, y;

        public Point(int x, int y) 
        {
            this.x = x;
            this.y = y;
        }
    }

    public SnakeGame() 
    {
        grid = new char[GRID_SIZE][GRID_SIZE];
        snake = new LinkedList<>();
        currentDirection = Direction.RIGHT;

        initializeGrid();
        initializeSnake();
        spawnFood();
    }

    private void initializeGrid() 
    {
        for (int i = 0; i < GRID_SIZE; i++) 
        {
            for (int j = 0; j < GRID_SIZE; j++) 
            {
                grid[i][j] = EMPTY_CELL;
            }
        }
    }

    private void initializeSnake() 
    {
        snake.add(new Point(0, 0)); // Initial position of the snake
    }

    private void spawnFood() 
    {
        int x = (int) (Math.random() * GRID_SIZE);
        int y = (int) (Math.random() * GRID_SIZE);

        food = new Point(x, y);
        grid[x][y] = FOOD;
    }

    private void move() 
    {
        Point head = snake.getFirst();
        Point newHead = new Point(head.x, head.y);

        switch (currentDirection) 
        {
            case UP:
                newHead.x--;
                break;
            case DOWN:
                newHead.x++;
                break;
            case LEFT:
                newHead.y--;
                break;
            case RIGHT:
                newHead.y++;
                break;
        }

        // Check if the new head hits the boundary or itself
        if (newHead.x < 0 || newHead.x >= GRID_SIZE || newHead.y < 0 || newHead.y >= GRID_SIZE|| grid[newHead.x][newHead.y] == SNAKE_BODY) 
        {
            System.out.println("Game Over!");
            System.exit(0);
        }

        // Check if the new head eats the food
        if (newHead.x == food.x && newHead.y == food.y) 
        {
            snake.addFirst(newHead);
            grid[newHead.x][newHead.y] = SNAKE_HEAD;
            spawnFood();
        } 
        else 
        {
            Point tail = snake.removeLast();
            grid[tail.x][tail.y] = EMPTY_CELL;

            snake.addFirst(newHead);
            grid[newHead.x][newHead.y] = SNAKE_HEAD;

            for (Point segment : snake.subList(1, snake.size())) 
            {
                grid[segment.x][segment.y] = SNAKE_BODY;
            }
        }
    }

    private void printGrid() 
    {
        for (int i = 0; i < GRID_SIZE; i++) 
        {
            for (int j = 0; j < GRID_SIZE; j++) 
            {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void play() 
    {
        Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            printGrid();
            System.out.println("Enter your move (W/A/S/D): ");
            char move = scanner.next().charAt(0);

            switch (move) 
            {
                case 'W':
                    currentDirection = Direction.UP;
                    break;
                case 'A':
                    currentDirection = Direction.LEFT;
                    break;
                case 'S':
                    currentDirection = Direction.DOWN;
                    break;
                case 'D':
                    currentDirection = Direction.RIGHT;
                    break;
                default:
                    System.out.println("Invalid move!");
            }

            move();
        }
    }

    public static void main(String[] args) 
    {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.play();
    }
}