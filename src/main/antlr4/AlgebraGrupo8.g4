grammar AlgebraGrupo8;

// Parser rules
program: (statement ';'?)* EOF;

statement: assignment | expression;

assignment: ID '<-' expression;

expression
    : '(' expression ')'                     # parenExpression
    | '[' expression ']'                     # bracketExpression
    | expression '^' expression               # powerExpression
    | expression ('*' | '/') expression       # mulDivExpression
    | expression ('+' | '-') expression       # addSubExpression
    | NUMBER                                  # numberExpression
    | ID                                      # idExpression
    ;

// Lexer rules
ID: [a-zA-Z_@][a-zA-Z0-9_@]*;
NUMBER: [0-9]+ ('.' [0-9]+)?;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;