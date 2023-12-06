/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_LAB4_TAB_H_INCLUDED
# define YY_YY_LAB4_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    MAIN = 258,                    /* MAIN  */
    ID = 259,                      /* ID  */
    CONST = 260,                   /* CONST  */
    REAL = 261,                    /* REAL  */
    DATA_TYPE = 262,               /* DATA_TYPE  */
    IF = 263,                      /* IF  */
    WHILE = 264,                   /* WHILE  */
    CIN = 265,                     /* CIN  */
    COUT = 266,                    /* COUT  */
    LCB = 267,                     /* LCB  */
    RCB = 268,                     /* RCB  */
    ELSE = 269,                    /* ELSE  */
    LPR = 270,                     /* LPR  */
    RPR = 271,                     /* RPR  */
    SEMICOLON = 272,               /* SEMICOLON  */
    COMMA = 273,                   /* COMMA  */
    COLON = 274,                   /* COLON  */
    ASSIGN = 275,                  /* ASSIGN  */
    PLUS = 276,                    /* PLUS  */
    MINUS = 277,                   /* MINUS  */
    MUL = 278,                     /* MUL  */
    LT = 279,                      /* LT  */
    GT = 280,                      /* GT  */
    NE = 281,                      /* NE  */
    DG = 282,                      /* DG  */
    DL = 283,                      /* DL  */
    FOR = 284,                     /* FOR  */
    EQUALS = 285,                  /* EQUALS  */
    DO = 286,                      /* DO  */
    AND = 287,                     /* AND  */
    OR = 288,                      /* OR  */
    DIV = 289                      /* DIV  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);


#endif /* !YY_YY_LAB4_TAB_H_INCLUDED  */
