# Equijoin_Using_Mapreduce
The required task is to write a map-reduce program that will perform equijoin.

Format of the Input File: - <br />Table1Name, Table1JoinColumn, Table1Other Column1, Table1OtherColumn2, …….. Table2Name, Table2JoinColumn, Table2Other Column1, Table2OtherColumn2, ……... 
 
Format of the Output File: -<br /> If Table1JoinColumn value is equal to Table2JoinColumn value, simply append both line side by side for Output. If Table1JoinColumn value does not match any value of Table2JoinColumn, simply remove them for the output file. You should not include two joins contains same row (No duplicate joins in output file). 
 
Note: - Table1JoinColumn and Table2JoinColumn would both be Integer or Real or Double or Float, basically Numeric. 
 
Example Input : -<br />
R, 2, Don, Larson, Newark, 555-3221 <br />
S, 1, 33000, 10000, part1 <br />
S, 2, 18000, 2000, part1 <br />
S, 2, 20000, 1800, part1 <br />
R, 3, Sal, Maglite, Nutley, 555-6905 <br />
S, 3, 24000, 5000, part1 <br />
S, 4, 22000, 7000, part1 <br />
R, 4, Bob, Turley, Passaic, 555-8908<br />

Example Output: - <br />
R, 2, Don, Larson, Newark, 555-3221, S, 2, 18000, 2000, part1 <br />
R, 2, Don, Larson, Newark, 555-3221, S, 2, 20000, 1800, part1 <br />
R, 3, Sal, Maglite, Nutley, 555-6905, S, 3, 24000, 5000, part1 <br />
S, 4, 22000, 7000, part1, R, 4, Bob, Turley, Passaic, 555-8908 <br />
 
 
1.	Mapper<br />
class name : Map<br />
The Map function initially finds the name of two tables under consideration. These names are stored
under global variables. The columns on which the two tables need to be joined is extracted by 
splitting the row on "," and using the second element (join column) as the key emitted by our
Map function. The entire row corresponding to this element is stored as a value. These key-value
pairs are then further passed to the Reduce function.


2.	Reducer<br />
class name : Reduce<br />
The Reduce function takes in the key-value pairs emitted by the Map function. I have created two hashsets 
corresponding to two tables. I iterate through each of the values of the two hashsets and combine them to 
generate the final rows which would then be the output of the Reduce function. HashSet is built by taking 
into consideration the fact that rows shouldn't be repeated.


3. Driver<br />
class name : Main<br />
The commandline arguments are used to set the input and output paths. All the other required configurations 
are set in this class and then the job is put to run.
