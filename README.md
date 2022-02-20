# Mazes world project


In this project, I created a maze game where each maze could be initiated using one of the following algorithms:
>1. `"Random selection"` algorithm (simpleMaze) 
>2. `Prim's` algorithm(myMaze)

This project uses MVC architecture, where the view is a CLI interface. The communication between the layers implemented using observer pattern.

There is a Demo class that generating a `Maze2d` (`simpleMaze` or `myMaze`), it can be solved using `BFS`, `DFS` and `Astar` algorithms (All of the algorithms implemented locally).
Then, the solution can be printed (showing the steps to the solution), saved compressed (using `Huffman Coding` algorithm) 
and decompressed back to a `Maze2d`. Using adapter pattern, `Maze2d` instance adapted to `ISearchable` instance. 

On the main menu of this project - are the following available commands for execution:

>**dir** &emsp; &emsp;  &emsp;  printing the path of a maze/all files in path  
>**genmaze** &emsp; generating simpleMaze or myMaze  
>**display** &emsp;  &emsp; displays generated/loaded mazes  
>**savemaze** &emsp; saves generated/loaded mazes compressed  
>**loadmaze** &emsp; loads maze from compressed file  
>**mazesize** &emsp; returns the maze size (uncompressed)  
>**filesize** &emsp; &ensp; returns the file size (compressed maze size for comparison)  
>**exit** &emsp;  &emsp; closes the whole application 

IDE: IntelliJ IDEA 2021.1.3
