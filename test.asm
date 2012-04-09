SET PUSH, exit
SET PUSH, exit
SET PC, func_main
:exit SUB PC, 1
:func_main
SET Z, 0
SET Y, Z
SET X, Y
SET I, SP
ADD I, 2
SET [I], X
SET Z, 1
SET Y, Z
SET X, Y
SET I, SP
ADD I, 1
SET [I], X
SET Z, 365
SET Y, Z
SET X, Y
SET I, SP
SET [I], X
SET I, SP
ADD I, 2
SET Z, [I]
SET Y, Z
SET Z, Y
IFN 1, Z
SET PC, a-true_gen_label_1
SET PC, and-chain-false_gen_label_1
:a-true_gen_label_1
SET I, SP
ADD I, 0
SET Z, [I]
SET Y, Z
SET J, Y
SET Z, 365
SET Y, Z
SET B, Y
IFE J, B
SET PC, b-true_gen_label_1
SET PC, and-chain-false_gen_label_1
:b-true_gen_label_1
SET PC, or-chain-true_gen_label_1
:and-chain-false_gen_label_1
SET I, SP
ADD I, 1
SET Z, [I]
SET Y, Z
SET Z, Y
IFE 1, Z
SET PC, or-chain-true_gen_label_1
:or-chain-true_gen_label_1
SET PC, start_if_block_gen_label_1
:or-chain-false_gen_label_1
SET PC, end_if_block_gen_label_1
:start_if_block_gen_label_1
;Setting RAM
;Calc value
SET Z, 89
SET Y, Z
SET X, Y
;Calc address
SET Z, 32768
SET Y, Z
SET J, Y
;Setting value
SET [J], X
:end_if_block_gen_label_1
