# SLR Parser

This is a **Java** program created for a university Compiler course (CPCS-302). The program will take an arithmetic expression with tokens as an input and put the expression through a given **SLR Parse Table** and parse through the expression, outputting each step along the way, and then finally showing whether the expression is an accepted expression with the given **Context Free Grammer(CFG)** or if the expression is rejected.

## CFG and Parse Table

**E' &rarr; E**
1. E &rarr;  E + T
2. E &rarr;   T
3. T&rarr; T * F
4. T&rarr; F
5. F&rarr; ( E )
6. F&rarr; id
<br><br>

**SLR Parse Table**

|State|action| | | |  |         | goto |  |  |
|-----|--|--|--|--|---|---|----|---|---|
|     |id| +| \*| (|) | $ |E|T|F|
|0    |s5|  |  |s4|   |   |1|2|3|   
|1    |  |s6|  |  |   |acc| | | |
|2    |  |r2|s7|  |r2 |r2 | | | |  
|3    |  |r4|r4|  |r4 |r4 | | | |
|4    |s5|  |  |s4|   |   |8|2|3|
|5    |  |r6|r6|  |r6 |r6 | | | |
|6    |s5|  |  |s4|   |   | |9|3|
|7    |s5|  |  |s4|   |   | | |10|
|8    |  |s6|  |  |s11|   | | |  |
|9    |  |r1|s7|  |r1 |r1 | | | |
|10   |  |r3|r3|  |r3 |r3 | | | |
|11   |  |r5|r5|  |r5 |r5 | | | |


## Parsing Algorithm

_input_: An input string _w_ and an LR parsing table with functions _action_ and _goto_ for a grammar _G_.

_output_: If _w_ is in _L(G)_, a bottom-up parser for _w_; otherwise, an error indication.

_Method_: Initially, the parser has _s<sub>0</sub>_ on its stack, where _s<sub>0</sub>_  is the initial state, and w$ in the input buffer. The parser then executes the program until an accept or error action is encountered.

set _ip_ to point to the first symbol of _w$_;<br>
**repeat forever begin**<br>
&nbsp;&nbsp;&nbsp;let s be the state on top of the stack and<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *a* the symbol pointed to by *ip*<br>
&nbsp;&nbsp;&nbsp;**if** actions[s, a] = shift s' **then begin**<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; push *a* then s' on top of the stack;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;advance *ip* to the next input symbol<br>
&nbsp;&nbsp;&nbsp;**end**<br>
**else if** *action* [s, a] = reudce A &rarr; B **then begin**<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; pop 2*|B| symbols off the stack;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; let s' be the state now in tp of the stack;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; push A then *goto*[s', A] on top of the stack;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;output the production A &rarr; B<br>
&nbsp;&nbsp;&nbsp;**end**<br>
&nbsp;&nbsp;&nbsp;**else if** *action*[s, a] = accept **then**<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**return**<br>
&nbsp;&nbsp;&nbsp;**else** *error()*<br>
**end**<br>
