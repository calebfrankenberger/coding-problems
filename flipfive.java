import java.util.*;
import java.io.*;

public class flipfive {
    public static void main(String[] args) throws IOException {
        new flipfive();
    }

    flipfive() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int numProblems = Integer.parseInt(br.readLine());
        State[] targetStates = new State[numProblems];

        // Load a State object for each input, add to targetStates
        for(int i = 0; i < numProblems; i++) {
            boolean[][] tiles = new boolean[3][3];
            for(int r = 0; r < 3; r++) {
                String string = br.readLine();
                for(int c = 0; c < 3; c++) {
                    if(string.charAt(c) == '.') 
                        tiles[r][c] = true;
                }
            }
            targetStates[i] = (new State(tiles));
        }

        // Create a start state of an entirely white grid
        boolean[][] allWhiteSquares = new boolean[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                allWhiteSquares[i][j] = true;
            }
        }
        final State startState = new State(allWhiteSquares);

        // Holds the distance from every state to an all white start state
        HashMap<State,Integer> states = new HashMap<State,Integer>();
        Queue<State> queue = new LinkedList<State>();
        int distance = 0;
        queue.add(startState);

        // While the queue still has States in it
        while(!queue.isEmpty()) {
            // Queue size is the number of States in current layer
            int layerSize = queue.size();
            // Go through all States in current layer
            for(int i=0; i < layerSize; i++) {
                State state = queue.poll();

                // Don't revisit a state
                if(states.containsKey(state)) 
                    continue;
                    
                // Set distance for current state
                states.put(state, distance);

                // Check all possible moves for current state. Add to queue if move is unvisited
                for(int r = 0; r < 3; r++) {
                    for(int c = 0; c < 3; c++) {
                        State move = new State(state.flipTile(r, c));
                        if(!states.containsKey(move))
                            queue.add(move);
                    }
                }
            }
            // After going through the entire layer, increase the distance for the next iteration
            distance++;
        }
        

        for(State state : targetStates)
            output.append(states.get(state) + " ");
        System.out.println(output.toString());
    }

    public class State {
        boolean[][] tiles;

        public State(boolean[][] tiles) {
            this.tiles = tiles;
        }

        // Flip given tile and adjacent neighbors, as long as the neighbor is within bounds of the grid
        public boolean[][] flipTile(int row, int col) {
            boolean[][] output = new boolean[3][3];

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    output[i][j] = tiles[i][j];
                }
            }

            // Flip Left
            if((row-1) >= 0 && (row-1) < 3) {
                output[row-1][col] = !output[row-1][col];
            }
            // Flip Right
            if((row+1) >= 0 && (row+1) < 3) {
                output[row+1][col] = !output[row+1][col];
            }
            // Flip Top
            if((col+1) >= 0 && (col+1) < 3) {
                output[row][col+1] = !output[row][col+1];
            }
            // Flip Bottom
            if((col-1) >= 0 && (col-1) < 3) {
                output[row][col-1] = !output[row][col-1];
            }
            // Flip Middle
            output[row][col] = !output[row][col];
            return output;
        }

        @Override
        public boolean equals(Object object) {
            State state = (State) object;
            return java.util.Arrays.deepEquals(tiles, state.tiles);
        }

        @Override
        public int hashCode() {
            return java.util.Arrays.deepHashCode(tiles);
        }
    }
}