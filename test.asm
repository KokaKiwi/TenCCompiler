SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit
SUB PC, 1
:func_main
SET X, 65
SET I, SP
ADD I, 2
SET [I], X
SET X, 0
SET [SP], X
:start_while_cond_gen_label_1
SET J, [SP]
SET B, 64
IFG J, B
SET PC, lessfalse_gen_label_1
SET PC, start_while_block_gen_label_1
:lessfalse_gen_label_1
SET PC, end_while_block_gen_label_1
:start_while_block_gen_label_1
SET X, 32768
ADD X, [SP]
SET I, SP
ADD I, 1
SET [I], X
SET I, SP
ADD I, 2
SET X, [I]
SET I, SP
ADD I, 1
SET J, [I]
SET [J], X
SET I, SP
ADD I, 2
ADD [I], 1
ADD [SP], 1
SET PC, start_while_cond_gen_label_1
:end_while_block_gen_label_1
