# Mazes world project

In this project, I developed a maze game with different maze initiation algorithms: 
>1. `"Random selection"` algorithm - `simpleMaze` 
>2. `Prim's` algorithm - `myMaze`

The project follows the `MVC` (Model-View-Controller) architecture, where the CLI interface serves as the view. Communication between the layers is implemented using the `observer pattern`.

There is a Demo class that generates a `Maze2d` (`simpleMaze` or `myMaze`) and allows solving it using `BFS`, `DFS`, and `Astar` algorithms (all implemented locally).
The solution can be displayed, saved in a compressed format using `Huffman Coding`, and decompressed back to a `Maze2d` object.
The `Maze2d` instance is adapted to an `ISearchable` instance using the `adapter pattern`.

The main menu of the project provides several commands available for execution:

>**dir** &emsp; &emsp;  &emsp;  Prints the path of a maze or all files in the specified directory.  
>**genmaze** &emsp; Generates a maze using either the simpleMaze or myMaze algorithm.  
>**display** &emsp;  &emsp; Displays the generated or loaded mazes.  
>**savemaze** &emsp; Saves the generated or loaded mazes in a compressed format.  
>**loadmaze** &emsp; Loads a maze from a compressed file.  
>**mazesize** &emsp; Returns the size of the maze (uncompressed).  
>**filesize** &emsp; &ensp; Returns the file size of the compressed maze for comparison.  
>**exit** &emsp;  &emsp; Closes the application.  

The project was developed using IntelliJ IDEA 2021.1.3 as the IDE.
