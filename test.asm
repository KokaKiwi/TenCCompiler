SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit SUB PC, 1
:func_main
SET I, SP
SET [I], X
SET Z, 0
SET Y, Z
SET X, Y
SET I, SP
SET [I], X
SET Z, 65
SET Y, Z
SET X, Y
SET Z, 256
SET Y, Z
SET J, Y
SET [J], X
:start_while_cond_gen_label_1
SET I, SP
SET Z, [I]
SET Y, Z
SET J, Y
SET Z, 30
SET Y, Z
SET B, Y
IFG J, B
SET PC, lessfalse_gen_label_1
SET PC, start_while_block_gen_label_1
:lessfalse_gen_label_1
SET PC, end_while_block_gen_label_1
:start_while_block_gen_label_1
SET Z, 256
SET Y, Z
SET J, Y
SET Z, [J]
SET Y, Z
SET X, Y
SET I, SP
SET Z, [I]
SET Y, Z
ADD X, Y
SET Z, 32768
SET Y, Z
SET J, Y
SET I, SP
SET Z, [I]
SET Y, Z
ADD J, Y
SET [J], X
SET I, SP
ADD [I], 1
SET PC, start_while_cond_gen_label_1
:end_while_block_gen_label_1
