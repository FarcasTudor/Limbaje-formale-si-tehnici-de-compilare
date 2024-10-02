win_flex lab6.lxi
win_bison -d lab6.y
gcc -c lex.yy.c lab6.tab.c
gcc -o tudor1 lex.yy.o lab6.tab.o
tudor1 program.txt