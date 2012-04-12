SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit
SUB PC, 1
:func_main
SET A, 72
JSR func_write
SET A, 101
JSR func_write
SET A, 108
JSR func_write
SET A, 108
JSR func_write
SET A, 111
JSR func_write
SET PC, exit
:func_write
SUB SP, 1
SET X, 32768
SET J, 256
ADD X, [J]
SET [SP], X
SET X, A
SET J, [SP]
SET [J], X
SET J, 256
ADD [J], 1
ADD SP, 1
SET PC, POP
