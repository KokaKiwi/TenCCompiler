SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit
SUB PC, 1
:func_main
SUB SP, 2
SET 6, X
ADD 3, X
SET X, [SP]
SET A, [SP]
SET 35, B
JSR func_test
SET X, A
SET I, SP
SET X, [I+1]
ADD SP, 2
SET PC, exit
:func_test
SUB SP, 1
SET Y, A
MUL Y, B
SET X, Y
ADD 6, X
SET X, [SP]
SET A, [SP]
ADD SP, 1
SET PC, POP
