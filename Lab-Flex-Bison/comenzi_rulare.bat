win_flex lab4.lxi
win_bison -d lab4.y
gcc -c lex.yy.c lab4.tab.c
gcc -o tudor lex.yy.o lab4.tab.0
tudor program.txt