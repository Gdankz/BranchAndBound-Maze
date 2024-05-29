package MethodUTS;
import java.util.PriorityQueue;

class Node implements Comparable<Node>{
    int x,y, distance;
    Node(int x, int y, int distance){
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other){
        return this.distance - other.distance;
    }
}
public class BranchAndBoundMaze{
    static int ROW = 5;
    static int COL = 5;
    static char[][] grid = {
            {'S', '1', '0', '1', '1'},
            {'0', '1', '1', '0', '1'},
            {'1', '1', '1', '0', '1'},
            {'1', '0', '0', '0', '1'},
            {'1', '1', 'E', '1', '1'}
    };

    static int[] rowMoves = {-1,0,0,1};
    static int[] colMoves = {0,-1,1,0};

    static boolean isValid(int row, int col){
        return (row >= 0) && (row<ROW) && (col>=0) && (col<COL);
    }

    static boolean isBlocked(int row, int col){
        return grid[row][col] == '0';
    }

    static int branchAndBound(){
        boolean[][] visited = new boolean[ROW][COL];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0,0,0));

        while (!pq.isEmpty()){
            Node current = pq.poll();
            int x = current.x;
            int y = current.y;
            int distance = current.distance;

            System.out.println("Exploring node: (" + x + ", " + y + "), distance: " + distance);

            if (grid[x][y] == 'E'){
                System.out.println("Reached the exit!");
                return distance;
            }

            if (!visited[x][y]){
                visited[x][y] = true;

                for (int i = 0; i < 4; i++) {
                    int newX = x + rowMoves[i];
                    int newY = y + colMoves[i];

                    System.out.println("Checking neighbor: (" + newX + ", " + newY + ")");

                    if (isValid(newX, newY) && !isBlocked(newX, newY) && !visited[newX][newY]){
                        pq.add(new Node(newX, newY, distance+1));
                        System.out.println("Added neighbor to priority queue: (" + newX + ", " + newY + "), distance: " + (distance+1));
                    } else {
                        System.out.println("Neighbor: (" + newX + ", " + newY + ") is not valid or already visited.");
                        System.out.println();
                    }
                }
            } else {
                System.out.println("Node: (" + x + ", " + y + ") is already visited.");
            }
        }
        System.out.println();
        System.out.println("No path exists to reach the exit.");
        return -1;
    }

    public static void main(String[] args) {
        int minDistance = branchAndBound();
        if(minDistance != -1){
            System.out.println("Minimum distance to reach the exit: " + minDistance);
        } else {
            System.out.println("No path exists to reach the exit.");
        }
    }
}

