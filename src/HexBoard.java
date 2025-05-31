/****************************************************************************
 *  This class manages an N-by-N hex game board .
 ****************************************************************************/

public class HexBoard {
    private int[][] grid;   //0 = false, 1 = player 1, 2 = player 2
    private int unsetTiles;
    private static int size;
    private static int virtualNode;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWinCheck;

    public HexBoard(int N) {
        if(N <= 0) throw new IllegalArgumentException("Board size cannot be 0 or less");
        // get the size and the first virtual node address (don't always have to do size*size)
        size = N;
        virtualNode = size*size;

        grid = new int[N][N];

        unsetTiles = N*N;
        uf = new WeightedQuickUnionUF(N*N);
        ufWinCheck = new WeightedQuickUnionUF(N * N + 4); // +4 for the extra virtual nodes

    }

    // function checks to see if the tiles next to each other are played by the same player
    private void connectNeighbors(int row, int col){
        // directions will check the 6 coordinates next to the tile that was set
        int[][] directions = {{0, -1}, {0, 1}, {1, 0}, {1, -1}, {-1, 0}, {-1, 1}};
        for(int[] dir : directions) {
            int row2 = row + dir[0];
            int col2 = col + dir[1];
            // check to make sure the new row and col are legal
            if ((row2 < size && col2 < size) && (row2 >= 0 && col2 >= 0)) {
                if (grid[row][col] == grid[row2][col2]) {
                    int value1 = cordToVal(row, col);
                    int value2 = cordToVal(row2, col2);
                    // if they are connected then union them together
                    uf.union(value1, value2);
                    ufWinCheck.union(value1, value2);
                }
            }
        }

    }

    // this function is translating the coordinate on the board to a value that union find stores
    private int cordToVal(int row, int col){
        return col + (row * size);
    }

    public int getPlayer(int row, int col) {
        return grid[row][col];
    }
    
    public boolean isSet(int row, int col) {
        return grid[row][col] != 0;
    }

    public boolean isOnWinningPath(int row, int col) {
        // use the union find without the virtual nodes
        if(row < 0 || row >= size || col < 0 || col >= size) throw new IndexOutOfBoundsException("Coordinates out of bounds");
        int pos = cordToVal(row, col);

        // check to see if the set tile is connected to both winning sides
        if(hasPlayer1Won() && grid[row][col] == 1) {
            boolean connectedUp = false;
            boolean connectedDown = false;
            for(int i =0; i < size; i++){
                if(uf.connected(pos, size * i)){
                    connectedUp = true;
                    break;
                }
            }
            for(int i = 1; i < size+1; i++){
                if(uf.connected(pos, (size*i) - 1)){
                    connectedDown = true;
                    break;
                }
            }
            return connectedUp && connectedDown;
        }
        // check to see if the set tile is connected to both winning sides
        else if (hasPlayer2Won() && grid[row][col] == 2){
            boolean connectedLeft = false;
            boolean connectedRight = false;
            for(int i=0; i<size; i++){
                if(uf.connected(pos, i)){
                    connectedLeft = true;
                    break;
                }
            }
            for(int i = (size*size)-size; i < size*size; i++){
                if(uf.connected(pos, i)){
                    connectedRight = true;
                    break;
                }
            }
            return connectedLeft && connectedRight;
        }

        else return false;
    }

    public void setTile(int row, int col, int player) {
        grid[row][col] = player;

        // if the player played on a winning square then union it to the virtual nodes
        if(col == 0 && player == 1) ufWinCheck.union(cordToVal(row, col), virtualNode);  // size is the top virtual node parent
        else if (col == size-1 && player == 1) ufWinCheck.union(cordToVal(row, col), virtualNode + 1);  // size is the top virtual node parent
        if(row == 0 && player == 2) ufWinCheck.union(cordToVal(row, col), virtualNode + 2);  // size is the top virtual node parent
        else if(row == size-1 && player == 2) ufWinCheck.union(cordToVal(row, col), virtualNode + 3);  // size is the top virtual node parent

        // check the tiles next to the one just placed to see if a union should be made
        connectNeighbors(row, col);


        unsetTiles--;
    }

    // using the union find with virtual nodes to see if game has a winner
    public boolean hasPlayer1Won() {
        return ufWinCheck.connected(virtualNode, virtualNode+1);
    }

    public boolean hasPlayer2Won() {
        return ufWinCheck.connected(virtualNode+2, virtualNode+3);
    }

    public int numberOfUnsetTiles() {
        return unsetTiles;
    }
}
