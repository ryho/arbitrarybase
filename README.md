arbitrarybase
=============

An arbitrary base calculator. Any base 2-36.

CalculatorApp.java interacts with the command line, changes settings, prints answers, and calls Calculator if the user gave a calculation instead of chaning settings

Calculator.java receives the users command, parses it into numbers and operations, and returns a numerical answer.

Features:
 * four function calculator [*,/,+,-]
 * set input base to arbitrary base (2-36) [type "input base" then a number]
 * set output base to arbitrary base (2-36) [type "output base" then a number]
 * change input base and output base to different things to use as a base converter
 * numbers with a fractional component work properly in all bases
 * dynamic input in the most common bases, regardless of input/output base
   * base 2 [0b*]
   * base 8 [0*]
   * base 10 [0b*]
   * base 16 [0x*] or [0h*]
 * use as many nested parenthesis as you want
 * 5(4) returns same thing as 5*(4)
 * .5 is the same thing as 0.5
 * follows order of operations, PMDAS
 * numbers are stored in 64 bit double precision
 * automatically adjusts max number of digits to print based on bits of precision of a double and the output base
