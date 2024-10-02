%{
    #include <string.h>
    #include <stdio.h>
    #include <stdlib.h>
    #include <stdbool.h>
    #include <ctype.h>

    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    extern int currentLine;
    void yyerror();

    FILE* outputFile;
    char* filename;

    #define MAX 1000
    char declarations[MAX][MAX], sourceCode[MAX][MAX], imports[MAX][MAX];
    int lenDeclarations = 0, lenSourceCode = 0, lenImports = 0;
    char variablesRead[MAX][MAX];
    int n = 0, nr = 0;
    char expressions[MAX][MAX];
    int lenExpressions = 0;

    bool found(char col[][MAX], int n, char* var);
    void parseExpression(char* element);
    void printDeclarationSegment();
    void printCodeSegment();
    void printImports();
%}

%union {
	char * value;
}

%token MAIN
%token DATA_TYPE
%token CIN
%token COUT
%token LCB
%token RCB
%token LPR
%token RPR
%token SEMICOLON
%token COMMA
%token ASSIGN
%token DG;
%token DL;
%token PLUS
%token MINUS
%token MUL
%token DIV
%token CONST
%token ID

%%

program: DATA_TYPE MAIN LPR RPR LCB instruction_list RCB
	;

instruction_list : instruction 
		|  instruction instruction_list
		;
    
instruction : declarations
	| assign_instruction
    | in_instruction
	| out_instruction
	;

declarations: DATA_TYPE variable_list SEMICOLON
	;

variable_list: ID {
			char tmp[100];
			strcpy(tmp, " ");
			strcat(tmp, $<value>1);
			if (!found(declarations, lenDeclarations, tmp)) {
				strcpy(declarations[lenDeclarations], strcat(tmp, " times 4 db 0"));
				lenDeclarations++;
			}
		  }
	| ID COMMA variable_list {
					char tmp[100];
					strcpy(tmp, " ");
					strcat(tmp, $<value>1);
					if (!found(declarations, lenDeclarations, tmp)) {
						strcpy(declarations[lenDeclarations], strcat(tmp, " times 4 db 0"));
						lenDeclarations++;
					}
		 		}
	;

assign_instruction : ID ASSIGN expression SEMICOLON
			{
				char tmp[MAX];
				strcpy(tmp, $<value>3);
				char* token = strtok(tmp, " ");
				while (token != NULL) {
					strcpy(expressions[lenExpressions], token);
					lenExpressions++;
					token = strtok(NULL, " ");
				}
				parseExpression($<value>1);
			}
		;

expression : ID
	| CONST
	| ID operator expression
		{
			char tmp[MAX];
			strcpy(tmp, $<value>1);
			strcat(tmp, " ");
			strcat(tmp, $<value>2);
			strcat(tmp, " ");
			strcat(tmp, $<value>3);
			$<value>$ = strdup(tmp);
		}
	| CONST operator expression
		{
			char tmp[MAX];
			strcpy(tmp, $<value>1);
			strcat(tmp, " ");
			strcat(tmp, $<value>2);
			strcat(tmp, " ");
			strcat(tmp, $<value>3);
			$<value>$ = strdup(tmp);
		}
	;

operator : PLUS | MINUS | MUL | DIV
    ;

in_instruction : CIN DG ID SEMICOLON
			{
                n = 0;
                strcpy(variablesRead[n], "push dword ");
                strcat(variablesRead[n], $<value>3);
                strcat(variablesRead[n], "\n\t\tpush dword format");
                strcat(variablesRead[n], "\n\t\tcall [scanf]");
                strcat(variablesRead[n], "\n\t\tadd ESP, 4 * 2\n");
                n++;

                if (!found(imports, lenImports, "scanf")) {
                    strcpy(imports[lenImports], "scanf");
                    lenImports++;
                }
                if (!found(declarations, lenDeclarations, "format")) {
                    strcpy(declarations[lenDeclarations], " format db \"%d\", 0");
                    lenDeclarations++;
                }

                // Adauga codul generat pentru citire in sourceCode
                for (int i = 0; i < n; i++) {
                    strcpy(sourceCode[lenSourceCode], variablesRead[i]);
                    lenSourceCode++;
                }
            }
		;
		
out_instruction : COUT DL ID SEMICOLON
			{
				if (!found(imports, lenImports, "printf")) {
					strcpy(imports[lenImports], "printf");
					lenImports++;
				}
				if (!found(declarations, lenDeclarations, "format")) {
					strcpy(declarations[lenDeclarations], " format db \"%d\", 0");
					lenDeclarations++;
				}
				strcpy(sourceCode[lenSourceCode], "push dword [");
				strcat(sourceCode[lenSourceCode], $<value>3);
				strcat(sourceCode[lenSourceCode++], "]");
				strcpy(sourceCode[lenSourceCode++], "push dword format");
				strcpy(sourceCode[lenSourceCode++], "call [printf]");
				strcpy(sourceCode[lenSourceCode++], "add ESP, 4 * 2\n");
			}
		;
%%

int main(int argc, char* argv[]) {
	FILE* f = NULL;
    	if (argc > 1) { 
    		f = fopen(argv[1], "r");
        }
	
	if(!f) {
		perror("Could not open file! Switching to console...\n");
		yyin = stdin;
	}
    	else {
		yyin = f;
	}
    
    	strcpy(imports[lenImports++], "exit"); 
     	
	while(!feof(yyin)) {
		yyparse();
	}

	//printf("The file is sintactically correct!");
    
    	outputFile = fopen("asmCode.asm", "w+");
    
    	fprintf(outputFile, "bits 32\nglobal start\n\n");
    
    	printImports();
    
    	fprintf(outputFile, "segment data use32 class=data\n");
    	printDeclarationSegment();
    
    	fprintf(outputFile, "\nsegment code use32 class=code\n\tstart:\n");
    	strcpy(sourceCode[lenSourceCode++], "push dword 0");
    	strcpy(sourceCode[lenSourceCode++], "call [exit]");
    	printCodeSegment();
	return 0;
}

/* Print error to console. */
void yyerror() {
    	printf("Error at line: %d ! \n", currentLine);
    	exit(1);
}

/* Parse expression and add its elements to source code collection. */
void parseExpression(char*  element) {
	strcpy(sourceCode[lenSourceCode], "mov AL, [");
	strcat(sourceCode[lenSourceCode], expressions[0]);
	strcat(sourceCode[lenSourceCode++], "]");

	for (int i = 1; i < lenExpressions - 1; i+=2) {
		if (strcmp(expressions[i], "*") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode], "mov DL, ");
				strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
				strcpy(sourceCode[lenSourceCode++], "mul DL");
			} else {
				strcpy(sourceCode[lenSourceCode], "mul byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
		else if (strcmp(expressions[i], "+") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode], "add AL, ");
				strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
			} else {
				strcpy(sourceCode[lenSourceCode], "add AL, byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
		else if (strcmp(expressions[i], "-") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode], "sub AL, ");
				strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
			} else {
				strcpy(sourceCode[lenSourceCode], "sub AL, byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
		else if (strcmp(expressions[i], "/") == 0) {
            if (isdigit(expressions[i + 1][0])) {
                strcpy(sourceCode[lenSourceCode], "mov DL, ");
                strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
                strcpy(sourceCode[lenSourceCode++], "div DL");
            } else {
                // Division by a variable
                strcpy(sourceCode[lenSourceCode], "div byte [");
                strcat(sourceCode[lenSourceCode], expressions[i + 1]);
                strcat(sourceCode[lenSourceCode++], "]");
            }
        }
 	}

	strcpy(sourceCode[lenSourceCode], "mov [");
	strcat(sourceCode[lenSourceCode], element);
	strcat(sourceCode[lenSourceCode++], "], AL\n");
	lenExpressions = 0;
}

/* Determine whether a variable is found in the collection. */
bool found(char col[][MAX], int n, char* var) {
	char tmp[MAX];
	strcpy(tmp, var);
	strcat(tmp, " ");
	for (int i = 0; i < n; i++) {
		if (strstr(col[i], tmp) != NULL) {
			return true;
		}
	}
	return false;
}

/* Write to output file the ASM imports generated. */
void printImports() {
	for (int i = 0; i < lenImports; i++) {
		fprintf(outputFile, "extern %s\nimport %s msvcrt.dll\n\n", imports[i], imports[i]);
	}
}

/* Write to output file the ASM declaration segment generated. */
void printDeclarationSegment() {
	for (int i = 0; i < lenDeclarations; i++) {
		fprintf(outputFile, "\t%s\n", declarations[i]);
	}
}

/* Write to output file the ASM code segment generated. */
void printCodeSegment() {
	for (int i = 0; i < lenSourceCode; i++) {
		fprintf(outputFile, "\t\t%s\n", sourceCode[i]);
	}
}