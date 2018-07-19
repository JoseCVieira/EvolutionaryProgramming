# Evolutionary

Evolutionary programming modeled, implemented with objects to find the best path between two points in a Grid for Object-Oriented Programming course.

### Description:

Consider a grid n × m. We want to find the best path, that is, the path with lowest cost between
an initial point, with coordinates (xi, yi), and a final point, with coordinates (xf , yf ). Each path
has an associated cost that is determined by the number of edges traversed. In general, the
cost of each edge is 1, but there may be special areas where the cost is higher. There are nobst
obstacles at some points on the grid through which one can not proceed.
For instance, consider the following figure which represents a grid 5 × 4:

<p align="center">
  <img src="https://i.imgur.com/lVRwRA1.png">
</p>

In this figure, the initial point, marked with 	⊙, has coordinates (1, 1), the end point, marked with
⊕, has coordinates (5, 4), the obstacles are marked with ⚫, and the edges of the special cost areas,
indicated by dashed, have cost 4. In this case, the best path is (1, 1),(1, 2),(2, 2),(3, 2),(3, 1),(4, 1),
(5, 1),(5, 2),(5, 3),(5, 4) with cost 12. There are shorter paths but whose cost is higher. For
example, the path (1, 1),(1, 2),(2, 2),(3, 2),(3, 3),(4, 3),(5, 3),(5, 4) is shorter but its cost is 13.

### Approach:

The goal of this project is to program in Java a solution to the problem presented above using
evolutive programming modeled and implemented with objects. The idea is to generate at instant zero a population of ν individuals, all placed at the initial point, and make it evolve until the final instant τ . Each individual z is associated with a comfort. Each individual z evolve according to its comfort, by the following random mechanisms:
- Death
- Reproduction
- Move

### Input file format:

The file that describes the input parameters of the simulation is an XML file. The simulation
is an element simulation whose attributes indicate the final instant (finalinst), the initial
population (initpop) and maximum population (maxpop) of the simulation, as well as the
sensitivity of the comfort to variations (comfortsens). The element simulation contains six
elements:
- The empty element grid whose attributes describe the grid dimension (colsnb and
rowsnb).
-  The empty element initialpoint, whose attributes describe the initial point coordinates
(xinitial and yinitial).
- The empty element finalpoint, whose attributes describe the final point coordinates
(xfinal and yfinal).
- The empty element specialcostzones which contains a list of elements zone whose
attributes indicate its position in the grid (xinitial, yinitial, xfinal, yfinal) and
whose content indicates the cost.
- The element obstacles, whose the only attribute describe the number of obstacles (num),
which contains a list of empty elements obstacle whose attributes indicate its position
in the grid (xpos, ypos).
- The element events, which contains three new empty elements (death, reproduction and
move) whose attributes (param) describe the parameters related to the death, reproduction
and move events.

### Results:

The program should print to the terminal at the end of the simulation the path of the best fit
individual over all the simulation. By the best fit individual we mean:
- if there is an individual which reached the final point, the individual z with lower cost,
independently from the individual z being or not alive at the end of the simulation;
- if none of the individuals reached the final point, the path of the individual z with greater
comfort, independently from the individual z being or not alive at the end of the
simulation.

### Contributors:

	-Name: 		Carlos Henrique Silva
	-e-mail:	henrique_silva04@hotmail.com
	-Degree:	MEEC
	
	-Name: 		José Carlos Vieira
	-e-mail:	josecarlosvieira@tecnico.ulisboa.pt
	-Degree: 	MEEC

	-Name:		Pedro Esperança do Carmo
	-e-mail:	pedro.carmo@tecnico.ulisboa.pt
	-Degree:	MEEC

### Institution:

	-Instituto Superior Técnico, Universidade de Lisboa (2017/2018)
