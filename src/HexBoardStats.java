/****************************************************************************
 *  Command: HexBoardStats N0 N1 T
 *
 *  This program takes the board sizes N0,N1 and game count T as a command-line
 *  arguments. Then, the program runs T games for each board size N where
 *  N0 <= N <= N1 and where each play randomly chooses an unset tile to set in
 *  order to estimate the probability that player 1 will win.
 ****************************************************************************/

public class HexBoardStats {
    private final int N0, N1, T;
    private final double[] p1WinProbabilityEstimates;
    private Stopwatch stopwatch;

    public HexBoardStats(int N0, int N1, int T) {
        this.N0 = N0;
        this.N1 = N1;
        this.T = T;
        this.p1WinProbabilityEstimates = new double[N1 - N0 + 1];

        // Play T games for board sizes N0 - N1
        for(int n = N0; n <= N1; n++){
            int p1Wins = 0;
            stopwatch = new Stopwatch(); //starts time
            for(int t = 0; t < T; t++){
                if(simulateGame(n)){
                    p1Wins++;
                }
            }
            // print out the time for each board size played on
            double time = stopwatch.elapsedTime();
            System.out.println("Time for board size n: " + n + ", Games: " + T + ", time is: " + time);
            // save the probability for that board size
            p1WinProbabilityEstimates[n - N0] = Math.floor((double) p1Wins / T * 10) / 10;
        }


    }

    // Simulates board game and returns who won (true for player 1, false for player 2)
    private boolean simulateGame(int n){
        // Get random coordinates and play a game
        HexBoard board = new HexBoard(n);

        int currentPlayer = 1;

        while(!board.hasPlayer1Won() && !board.hasPlayer2Won()){
            int row, col;

            do{
                // make sure the coordinates are legal
                row = StdRandom.uniform(n);
                col = StdRandom.uniform(n);
            }while(board.isSet(row, col));

            board.setTile(row, col, currentPlayer);

            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }

        return board.hasPlayer1Won();
    }

    public int getN0() {
        return N0;
    }

    public int getN1() {
        return N1;
    }

    public int getT() {
        return T;
    }

    public double getP1WinProbabilityEstimate(int n) {
        if(n < N0 || n > N1) throw new IndexOutOfBoundsException("Board size out of bounds");
        return p1WinProbabilityEstimates[n - N0];
    }

    public double getP2WinProbabilityEstimate(int n) {
        if(n < N0 || n > N1) throw new IndexOutOfBoundsException("Board size out of bounds");
        return 1.0 - getP1WinProbabilityEstimate(n);
    }

    public static void main(String[] args) {
        int N0 = Integer.parseInt(args[0]);
        int N1 = Integer.parseInt(args[1]);
        int T = Integer.parseInt(args[2]);
        if(N0 <= 0 || N1 < N0 || T <= 0) throw new IllegalArgumentException("Incorrect arguments");


        HexBoardStats board = new HexBoardStats(N0, N1, T);
        for(int n = N0; n <= N1; n++){
            System.out.println("N = " + n + ", P1 = " + board.getP1WinProbabilityEstimate(n) + ", P2 = " + board.getP2WinProbabilityEstimate(n));
        }
    }
}