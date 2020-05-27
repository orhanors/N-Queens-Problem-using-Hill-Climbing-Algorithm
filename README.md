# N-Queens Problem using Hill Climbing Algorithm

# Problem
This problem is to find an arrangement of N queens on a chess board, such that no queen can attack any other queens on the board.

The chess queens can attack in any direction as horizontal, vertical, horizontal and diagonal way.

A binary matrix is used to display the positions of N Queens, where no queens can attack other queens.

1 0 0 0 0 0 0 0
0 0 0 0 0 0 1 0
0 0 0 0 1 0 0 0
0 0 0 0 0 0 0 1
0 1 0 0 0 0 0 0
0 0 0 1 0 0 0 0
0 0 0 0 0 1 0 0
0 0 1 0 0 0 0 0

# Hill Climbing

Hill Climbing with random restart: This is a local search algorithm. It is an iterative algorithm that starts with an arbitrary solution to a problem, then attempts to find a better solution by incrementally changing a single element of the solution. If the change produces a better solution, an incremental change is made to the new solution, repeating until no further improvements can be found. 

The hill-climbing algorithms often fail to find a goal when one exists because they can get stuck on local maxima. Random-restart can be used to solve the problem of local maxima, as it conducts a series of hill-climbing searches from randomly generated initial states, until a goal is found.
