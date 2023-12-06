%{
#include <stdio.h>
#include <stdlib.h>
int counter;
extern int yylex();
extern int yyparse();
extern void yyerror(const char* s);
extern FILE* yyin;
extern int yywrap();
%}

%token LEFT_PR 
%token RIGHT_PR
%token PLUS
%token NUMBER

%%

input: expression { if(counter == 0) printf("Se inchid corect.\n");
                  else printf("Nu se inchid corect.\n"); } ;

expression: term
	| expression PLUS term
	| term PLUS
	| PLUS expression 
	;

term: NUMBER 
	| LEFT_PR expression RIGHT_PR 
	| RIGHT_PR expression LEFT_PR
	| RIGHT_PR expression RIGHT_PR
	| LEFT_PR expression LEFT_PR;

%%

void yyerror(const char* s) {
	printf("Nu se inchid corect");
}

int main(int argc, char* argv[]) {
    ++argv, --argc;
    
    // sets the input for flex file
    if (argc > 0) 
        yyin = fopen(argv[0], "r"); 
    else 
        yyin = stdin; 
    
    while (!feof(yyin)) {
        yyparse();
    }
    return 0;
}
