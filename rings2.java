import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class rings2 {

    public static void main(String[] args) throws IOException {
        new rings2();
    }

    public rings2() throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
 
        String firstLine = br.readLine();
        String[] dimensions = firstLine.trim().split("\\s+");
        int rows = Integer.parseInt(dimensions[0]) + 2;
        int cols = Integer.parseInt(dimensions[1]) + 2;

        //  Keeping in mind that the default value for int arrays is zero
        int[][] distances = new int[rows][cols];

        //  Load the input into the grid, excluding the added rows, set first layer distances to 1
        for(int r = 1; r < rows-1; r++) {
            char[] chars = br.readLine().toCharArray();
            for (int ch = 0; ch < chars.length; ch++)
                if (chars[ch] == 'T') 
                    distances[r][ch + 1] = 1; 
        }
        int distance = 1;

        //  Continues to run until it can't find any new layers
        boolean nextLayerFound;
        do {
            nextLayerFound = false;
            for(int r = distance; r < rows-distance; r++) {
                for (int c = distance; c < cols-distance; c++) {
                    // Check all neighbors to see if tile is surrounded completely by distances equal or greater than itself. 
                    // If so, the current tile needs to increase its distance
                    if ((distances[r + 1][c] >= distance) && (distances[r - 1][c] >=
                            distance) && (distances[r][c + 1] >= distance) && (distances[r][c - 1] >= distance)) {
                        nextLayerFound = true;
                        distances[r][c] = distance+1;
                    }
                } 
            }
            if(nextLayerFound)
                distance++;
        } while(nextLayerFound);

         // Output
         StringBuilder sb = new StringBuilder();
         sb.append("\n");
         for(int r = 1; r < rows-1; r++) {
             for (int c = 1; c < cols-1; c++) {
                 int currentTileDistance = distances[r][c];
                 sb.append(".");
                 if(currentTileDistance >= 10) {
                     sb.append(currentTileDistance);
                     continue;
                 } 
                 if(distance >= 10) {
                     sb.append(".");
                 }
                 if(currentTileDistance == 0)
                     sb.append(".");
                 else
                     sb.append(currentTileDistance);
             }
             sb.append("\n");
         }
     System.out.println(sb.toString());
    }

}