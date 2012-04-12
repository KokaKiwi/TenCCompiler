SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit
SUB PC, 1
:func_main
SUB SP, 2
SET X, 6
ADD X, 3
SET [SP], X
SET A, [SP]
SET B, 35
JSR func_test
SET X, A
SET I, SP
SET [I+1], X
ADD SP, 2
SET PC, exit
:func_test
SUB SP, 1
SET Y, A
MUL Y, B
SET X, Y
ADD X, 6
SET [SP], X
SET A, [SP]
ADD SP, 1
SET PC, POP
