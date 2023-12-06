%{
    #include <string.h>
    #include <stdio.h>
    #include <stdlib.h>

    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    extern int currentLine;
    void yyerror();
%}

%token MAIN
%token ID
%token CONST
%token REAL
%token DATA_TYPE
%token IF
%token WHILE
%token CIN
%token COUT
%token LCB
%token RCB
%token ELSE
%token LPR
%token RPR
%token SEMICOLON
%token COMMA
%token COLON
%token ASSIGN
%token PLUS
%token MINUS
%token MUL
%token LT
%token GT
%token NE
%token DG
%token DL
%token FOR
%token EQUALS
%token DO
%token AND
%token OR
%token DIV
%%     

program: DATA_TYPE MAIN LPR RPR LCB instruction_list RCB
	;

declarations: DATA_TYPE variable_list SEMICOLON
	;

variable_list: ID 
	| ID COMMA variable_list
	;

instruction_list : instruction 
		|  instruction instruction_list
		;

instruction : declarations
	| assign_instruction
	| if_else_instruction
	| io_instruction
	| loop_instruction
	;

loop_instruction : while_loop
		| do_while_loop
		| for_loop
		;


assign_instruction : ID ASSIGN expression SEMICOLON
		;

expression : ID
	| CONST
	| REAL
	| ID operator expression
	| CONST operator expression
	| REAL operator expression
	;

operator : PLUS | MINUS | MUL | DIV

bool_operator : GT | LT | NE | EQUALS

if_else_instruction : if_instruction 
		| if_instruction else_instruction
		;

if_instruction : IF LPR lista_cond RPR LCB instruction_list RCB ;

else_instruction: ELSE LCB instruction_list RCB
		;

lista_cond : cond | cond op_logic lista_cond ;

cond : expression bool_operator expression | expression ;

op_logic : AND | OR ;

boolean_expression : ID bool_operator expression
		;

io_instruction : CIN DG ID SEMICOLON
		| COUT DL ID SEMICOLON
		;

while_loop : WHILE LPR boolean_expression RPR LCB instruction_list RCB
		;

do_while_loop : DO LCB instruction_list RCB WHILE LPR boolean_expression RPR 
		;

for_loop : FOR LPR assign_instruction SEMICOLON boolean_expression SEMICOLON assign_instruction RPR LCB instruction_list RCB
                    ;
%%

int main(int argc, char* argv[]) {
    ++argv, --argc;
    
    // sets the input for flex file
    if (argc > 0) 
        yyin = fopen(argv[0], "r"); 
    else 
        yyin = stdin; 
    
    //read each line from the input file and process it
    while (!feof(yyin)) {
        yyparse();
    }
    printf("The file is sintactically correct!\n");
    return 0;
}

void yyerror() {
    printf("Error at line: %d ! \n", currentLine);
    exit(1);
}