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
number: INT
      | REAL ;

// Lexer rule
INT: [0-9]+ ;
REAL: [0-9]+'.'[0-9]* ;
ID: [a-zA-Z]+ ;
WHITESPACE: [ \t\r\n]+ -> skip ;
