/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
// Vikram Saluja Maze Solver
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Should be from start to end cells
        // Create new Arraylist and Stack
        ArrayList<MazeCell> solutions = new ArrayList<>();
        Stack<MazeCell> values = new Stack<>();
        // Temp variable is set to end cell
        MazeCell temp = maze.getEndCell();
        // Run until the temp cell is equal to the start cell since this would mean it is complete
        while(temp != maze.getStartCell()){
            // Add the parent of temp to stack
            values.add(temp.getParent());
            temp = temp.getParent();
        }

        // Until stack is empty add all values to arraylist to get correct order (LIFO)
        while(!values.isEmpty()){
            solutions.add(values.pop());
        }
        // Return filled arraylist
        return solutions;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> dfs = new Stack<>();
        MazeCell start = maze.getStartCell();
        MazeCell end = maze.getEndCell();

        // Add start cell to the stack
        dfs.push(start);
        start.setExplored(true);

        // While the stack is not still contains elements
        while(!dfs.isEmpty()){
            // Each time it runs, "check" is set to top element
            MazeCell check = dfs.pop();
            // If check is last element, the search is complete (return)
            if(check == end){
                return getSolution();
            }

            ArrayList<MazeCell> neighbors;
            neighbors = getNeighbors(check);

            // Run for every neighbor the current cell has
            for(int i = 0; i < neighbors.size(); i++){
                MazeCell neighbor = neighbors.get(i);
                // Set the neighbor to explored and reset the parent
                neighbor.setExplored(true);
                neighbor.setParent(check);
                // Push the neighbor to the stack
                dfs.push(neighbor);
            }
        }
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Create new queue
        Queue<MazeCell> bfs = new LinkedList<>();
        // Set start and end cells
        MazeCell start = maze.getStartCell();
        MazeCell end = maze.getEndCell();

        // Add the first cell to the queue
        bfs.add(start);
        start.setExplored(true);

        // While loop runs while the queue is not empty
        while(!bfs.isEmpty()){
            // Remove first element from the queue
            MazeCell check = bfs.remove();

            // If the check element is the same as the end element, the search is complete
            if(check == end){
                return getSolution();
            }

            ArrayList<MazeCell> neighbors;
            neighbors = getNeighbors(check);

            // Runs for as many neighbors as the cell has
            for(int i = 0; i < neighbors.size(); i++){
                MazeCell neighbor = neighbors.get(i);
                neighbor.setExplored(true);
                neighbor.setParent(check);
                // Add the neighbor to the queue
                bfs.add(neighbor);
            }
        }
        return getSolution();
    }

    public ArrayList<MazeCell> getNeighbors(MazeCell cell){
        int row = cell.getRow();
        int col = cell.getCol();

        ArrayList<MazeCell> neighbors = new ArrayList<>();

        // North first
        if(maze.isValidCell(row - 1, col)){
            neighbors.add((maze.getCell(row-1,col)));
        }
        // East second
        if(maze.isValidCell(row,col + 1)){
            neighbors.add(maze.getCell(row,col+1));
        }
        // South third
        if(maze.isValidCell(row + 1, col)){
            neighbors.add(maze.getCell(row+1, col));
        }
        // West last
        if(maze.isValidCell(row,col-1)){
            neighbors.add(maze.getCell(row,col -1));
        }
        // Return arraylist containing all of the neighbors in the correct order
        return neighbors;
    }


    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
