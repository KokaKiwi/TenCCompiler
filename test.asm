SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit
SUB PC, 1
:func_main
SET X, 35
SET [SP], X
SET J, [SP]
SET B, 35
IFE J, B
SET PC, start_if_block_gen_label_1
SET PC, start_else_block_gen_label_1
:start_if_block_gen_label_1
SET X, 89
SET I, SP
ADD I, 1
SET [I], X
SET PC, end_if_block_gen_label_1
:start_else_block_gen_label_1
SET J, [SP]
SET B, 36
IFE J, B
SET PC, start_if_block_gen_label_2
SET PC, start_else_block_gen_label_2
:start_if_block_gen_label_2
SET X, 69
SET I, SP
ADD I, 1
SET [I], X
SET PC, end_if_block_gen_label_2
:start_else_block_gen_label_2
SET X, 83
SET I, SP
ADD I, 1
SET [I], X
:end_if_block_gen_label_2
:end_if_block_gen_label_1
