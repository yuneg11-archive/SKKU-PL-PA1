grammar Expr;

// Parser rule
program: ( assign ';' | expression ';' )* ;
expression: parenthesis expression parenthesis
          | expression operator expression
          | id
          | number ;
assign: id '=' number ;
parenthesis: ( '(' | ')' ) ;
operator: ( '*' | '/' )
        | ( '+' | '-' ) ;
id: ID ;
number: ( '+' | '-' ) number
      | unsigned_number ;
unsigned_number: UNSIGNED_INT
               | UNSIGNED_REAL ;

// Lexer rule
UNSIGNED_INT: [0-9]+ ;
UNSIGNED_REAL: [0-9]+'.'[0-9]* ;
ID: [a-zA-Z]+ ;
WHITESPACE: [ \t\r\n]+ -> skip ;
