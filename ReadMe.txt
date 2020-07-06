
1.	Mapper

class name : Map

The Map function initially finds the name of two tables under consideration. These names are stored
under global variables. The columns on which the two tables need to be joined is extracted by 
splitting the row on "," and using the second element (join column) as the key emitted by our
Map function. The entire row corresponding to this element is stored as a value. These key-value
pairs are then further passed to the Reduce function.


2.	Reducer

class name : Reduce

The Reduce function takes in the key-value pairs emitted by the Map function. I have created two hashsets 
corresponding to two tables. I iterate through each of the values of the two hashsets and combine them to 
generate the final rows which would then be the output of the Reduce function. HashSet is built by taking 
into consideration the fact that rows shouldn't be repeated.


3. Driver

class name : Main

The commandline arguments are used to set the input and output paths. All the other required configurations 
are set in this class and then the job is put to run.
 