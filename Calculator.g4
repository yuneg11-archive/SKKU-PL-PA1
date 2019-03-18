grammar Calculator;

// Parser rule
program: ( expression NEWLINE )* ;
expression: '(' expression ')'
          | expression ( '*' | '/' ) expression
          | expression ( '+' | '-' ) expression
          | INT ;

// Lexer rule
NEWLINE: [\r\n]+ ;
INT: [0-9]+ ;
WHITESPACE: [ \t\r\n]+ -> skip ;
