
import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.util.Random;
public class HillClimbingRandomRestart {
    //Change n size 
    static int n = 8;
    private static int stepsClimbedAfterLastRestart = 0;
    private static int stepsClimbed =0;
    private static int heuristic = 0;
    private static int randomRestarts = 0;
    private static long e_time = 0;
    private static int running_size = 25;

    //Method to create a new random board
    public static NQueen[] generateBoard() {
        NQueen[] startBoard = new NQueen[n];
        Random rndm = new Random();
        for(int i=0; i<n; i++){
            startBoard[i] = new NQueen(rndm.nextInt(n), i);
        }
        return startBoard;
    }

    //Method to print the Current State
    private static void printState (NQueen[] state) {
        //Creating temporary board from the present board
        int[][] tempBoard = new int[n][n];
        for (int i=0; i<n; i++) {
            //Get the positions of Queen from the Present board and set those positions as 1 in temp board
            tempBoard[state[i].getRow()][state[i].getColumn()]=1;
        }
        System.out.println();
        for (int i=0; i<n; i++) {
            for (int j= 0; j < n; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to find Heuristics of a state
    public static int findHeuristic (NQueen[] state) {
        int heuristic = 0;
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].ifConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    // Method to get the next board with lower heuristic
    public static NQueen[] nextBoard (NQueen[] presentBoard) {
        NQueen[] nextBoard = new NQueen[n];
        NQueen[] tmpBoard = new NQueen[n];
        int presentHeuristic = findHeuristic(presentBoard);
        int bestHeuristic = presentHeuristic;
        int tempH;

        for (int i=0; i<n; i++) {
            //  Copy present board as best board and temp board
            nextBoard[i] = new NQueen(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tmpBoard[i] = nextBoard[i];
        }
        //  Iterate each column
        for (int i=0; i<n; i++) {
            if (i>0)
                tmpBoard[i-1] = new NQueen (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new NQueen (0, tmpBoard[i].getColumn());
            //  Iterate each row
            for (int j=0; j<n; j++) {
                //Get the heuristic
                tempH = findHeuristic(tmpBoard);
                //Check if temp board better than best board
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                    //  Copy the temp board as best board
                    for (int k=0; k<n; k++) {
                        nextBoard[k] = new NQueen(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                //Move the queen
                if (tmpBoard[i].getRow()!=n-1)
                    tmpBoard[i].move();
            }
        }
        //Check whether the present bord and the best board found have same heuristic
        //Then randomly generate new board and assign it to best board
        if (bestHeuristic == presentHeuristic) {
            randomRestarts++;
            stepsClimbedAfterLastRestart = 0;
            nextBoard = generateBoard();
            heuristic = findHeuristic(nextBoard);
        } else
            heuristic = bestHeuristic;
        stepsClimbed++;
        stepsClimbedAfterLastRestart++;
        return nextBoard;
    }

    public float[] getAverage(long arr[][]){
        float[] array = new float[3];

        int climb = 0;
        int restart= 0;
        int time = 0;

        for(int i = 0; i<3;i++){
            for(int j=0; j<25 ; j++){
                if(i==0){
                    climb+=arr[j][0];
                }else if(i==1){
                    restart+=arr[j][1];
                }else{
                    time+=arr[j][2];
                }
            }

        }
        array[0] = climb/running_size;
        array[1] = restart/running_size;
        array[2] = time/running_size;
        return array;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int presentHeuristic;
        long[][] info = new long[26][3];

        HillClimbingRandomRestart hill = new HillClimbingRandomRestart();

        System.out.println("\n-----------------------------------------------------------------------\n"+
                "| Program that executes N QUEENS problem 25 times using HILL-CLIMBING |"
                +"\n-----------------------------------------------------------------------");
        for (int i = 0; i<25 ; i++){


            //Creating the initial Board
            NQueen[] presentBoard = generateBoard();
            presentHeuristic = findHeuristic(presentBoard);
            // test if the present board is the solution board
            while (presentHeuristic != 0) {
                //  Get the next board
                // printState(presentBoard);\n
                presentBoard = nextBoard(presentBoard);
                presentHeuristic  = heuristic;
            }
            //Printing the solution
            System.out.println("\n" + (i+1) + ".step is running...    ");
            printState(presentBoard);
            long end = System.currentTimeMillis();
            e_time = (end - start);
            System.out.println("\nExecution time: " +
                    e_time + "ms");
            System.out.println("Number of Steps Climbed: " + stepsClimbed);
            System.out.println("Number of random restarts: " + randomRestarts);
            System.out.println("Steps Climbed after last restart: " + stepsClimbedAfterLastRestart);

            info[i][0] = stepsClimbed;
            info[i][1] = randomRestarts;
            info[i][2] = e_time;


        }
        System.out.println();
        System.out.println("-------------------------\n"+
                "| TABLE OF 25 EXECUTION |\n"+"-------------------------");
        System.out.println();
        System.out.println("      Climb|" + "Restart|" + "Time");

        for (int i = 0; i < 25; i++) {
            System.out.print("Step" + (i+1)+":  ");
            for (int j = 0; j < 3; j++) {

                System.out.print(info[i][j] + "    ");

            }

            System.out.println();

        }
        float[] average = hill.getAverage(info);
        System.out.println();
        System.out.println("Average:  "+average[0]+"   "+average[1]+ "  "+ average[2]);
    }
}
