grammar Expr;

// Parser rule
program: ( assign ';' | expression ';' )* ;
expression: '(' expression ')'
          | expression ( '*' | '/' ) expression
          | expression ( '+' | '-' ) expression
          | ID
          | number ;
assign: ID '=' number ;
number: INT
      | REAL ;

// Lexer rule
INT: [0-9]+ ;
REAL: [0-9]+'.'[0-9]* ;
ID: [a-zA-Z]+ ;
WHITESPACE: [ \t\r\n]+ -> skip ;
