package week4;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 10/23/2019
 * @author: Monali
 */

public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){

        List<Board> openList = new ArrayList<Board>();
        List<Board> closeList = new ArrayList<Board>();

        openList.add(initial);

        while(!openList.isEmpty()){
            Board curr = null; // Get board with min f value
            closeList.add(openList.remove(0)); // Get the minimum from the list
            if(curr.isGoal()){
                // Backtrack to get the path from start to this board
            }else{
                Iterable<Board> neighbors = curr.neighbors();
                for(Board child: neighbors){
                    if(closeList.contains(child)) continue;

                }
            }
        }
    }



    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return false;
    }

    // min number of moves to solve initial board
    public int moves(){
        return Integer.MIN_VALUE;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        return null;
    }

    // test client (see below)
    public static void main(String[] args){

    }
}
