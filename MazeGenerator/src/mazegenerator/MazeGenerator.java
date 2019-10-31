/*
 * n=12
 */
package mazegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author stusi
 */
public class MazeGenerator {
    static int n;
    static int lastr;
    static int lastc;
    static boolean[][] north;
    static boolean[][] south;
    static boolean[][] east;
    static boolean[][] west;
    static boolean[][] visited;
    static int numVisited;
    static Random rand;
    static ArrayList<int[]> path;

    private static void printMaze() {
        for (int i = 0; i < n; i++) {
            System.out.print(" _");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print('|');
            for (int j = 0; j < n; j++) {
                if (i == lastr && j == lastc) {
                    System.out.print("X");
                } else if (i == 0 && j == 0) {
                    System.out.print("X");
                } else if (south[i][j]) {
                    System.out.print("_");
                } else {
                    System.out.print(" ");
                }
                if (east[i][j]) {
                System.out.print('|');
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // B Stusiak - My 12x12 maze and path
        n = 12;
        makeMaze();
        printMaze();
        printPath();
    }

    private static void makeMaze() {
        numVisited = 0;
        rand = new Random();
        north = new boolean[n][n];
        south = new boolean[n][n];
        east = new boolean[n][n];
        west = new boolean[n][n];
        visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                north[i][j] = true;
                south[i][j] = true;
                east[i][j] = true;
                west[i][j] = true;
                visited[i][j] = false;
            }
        }
        int r = 0;
        int c = 0;
        lastr = -1;
        lastc = -1;
        //printMaze();
        path = new ArrayList<>();
        while (numVisited < n*n) { // go until all squares have been visited
            //System.out.println("r = " + Integer.toString(r) + ", c = " + Integer.toString(c));
            if (!visited[r][c]) {
                visited[r][c] = true;
                numVisited++;
            }
            ArrayList<int[]> neighs = new ArrayList<>();
            // find new neighbours that haven't been visited
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    // check boundaries
                    if (r + i >= 0 && r + i < n && c + j >= 0 && c + j < n) {
                        // check visited and don't include diagonals
                        if (!visited[r+i][c+j] && (i == 0 || j == 0)) {
                            neighs.add(new int[]{r+i,c+j});
                            //System.out.println("neighbor (" + Integer.toString(r+i)+","+ Integer.toString(y+j)+")");
                        }
                        //System.out.println(neighs.size());
                    }
                }
            }
            if (neighs.isEmpty()) {
                if (r != lastr || c != lastc) {
                r = lastr;
                c = lastc;
                } else {
                    r = path.get(path.size()-1)[0];
                    c = path.get(path.size()-1)[1];
                    path.remove(path.size()-1);
                    //printPath();                    
                }
            } else {
                int next = rand.nextInt(neighs.size());
                path.add(new int[]{lastr,lastc});
                //printPath();
                lastr = r;
                lastc = c;
                r = neighs.get(next)[0];
                c = neighs.get(next)[1];
                //System.out.println("last:" + Arrays.toString(new int[]{lastr,lastc}));
                //System.out.println("next:" + Arrays.toString(new int[]{r,c}));
                //System.out.println("diffs:" + Integer.toString(r-lastr)+","+Integer.toString(c-lastc));
                if (c - lastc == -1) {
                    west[lastr][lastc] = false;
                    east[r][c] = false;
                } else if (c - lastc == 1) {
                    east[lastr][lastc] = false;
                    west[r][c] = false;
                } else if (r - lastr == 1) {
                    south[lastr][lastc] = false;
                    north[r][c] = false;
                } else if (r - lastr == -1) {
                    north[lastr][lastc] = false;
                    south[r][c] = false;
                }
            }
        }
    
    }
    
    private static void printPath() {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(Arrays.toString(path.get(i)) + " ");
            if (i%15 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
    
}
