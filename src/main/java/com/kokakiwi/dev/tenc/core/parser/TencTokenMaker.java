/* The following code was generated by JFlex 1.4.3 on 10/04/12 19:28 */

package com.kokakiwi.dev.tenc.core.parser;

import java.io.IOException;

/**
 * Scanner for the C programming language.
 *
 * @author Koka El Kiwi (and some codes by Robert Futrell)
 * @version 0.1
 *
 */

public class TencTokenMaker extends TokenParser {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int EOL_COMMENT = 4;
  public static final int YYINITIAL = 0;
  public static final int VAR_DECLARE = 6;
  public static final int MLC = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3, 3
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\10\1\7\1\0\1\10\1\45\22\0\1\10\1\21\1\26"+
    "\1\0\1\46\1\63\1\64\1\16\1\13\1\14\1\47\1\6\1\62"+
    "\1\22\1\44\1\15\1\31\3\24\4\4\2\2\1\45\1\67\1\17"+
    "\1\12\1\20\1\11\1\0\4\3\1\5\1\3\5\1\1\43\10\1"+
    "\1\42\2\1\1\25\2\1\1\60\1\23\1\61\1\45\1\1\1\0"+
    "\1\37\1\30\1\52\1\55\1\35\1\36\1\1\1\53\1\50\2\1"+
    "\1\40\1\1\1\51\1\54\2\1\1\33\1\41\1\32\1\34\1\27"+
    "\1\66\1\25\2\1\1\56\1\65\1\57\1\45\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\4\0\1\1\1\2\1\3\1\4\2\1\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\11"+
    "\2\2\1\3\4\2\1\1\1\16\3\2\1\17\1\20"+
    "\1\21\1\22\1\23\1\24\2\1\1\2\1\25\3\1"+
    "\1\26\1\1\2\3\1\1\1\27\1\30\1\31\1\32"+
    "\1\33\1\34\4\11\1\35\1\36\1\37\1\40\1\41"+
    "\2\11\1\42\2\2\1\1\4\2\1\43\1\44\3\2"+
    "\1\45\1\46\1\47\1\2\1\3\1\0\1\3\1\1"+
    "\1\11\1\50\7\11\2\2\1\51\4\2\1\52\3\2"+
    "\1\0\2\11\1\2\2\51\1\53\1\2\1\54\1\55"+
    "\2\2\1\51\1\2\1\56\1\2\1\57";

  private static int [] zzUnpackAction() {
    int [] result = new int[125];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\70\0\160\0\250\0\340\0\u0118\0\u0150\0\u0188"+
    "\0\250\0\u01c0\0\u01f8\0\250\0\250\0\u0230\0\u0268\0\u02a0"+
    "\0\u02d8\0\u0310\0\u0348\0\u0380\0\u03b8\0\u03f0\0\u0428\0\u0460"+
    "\0\u0498\0\u04d0\0\u0508\0\u0540\0\u0578\0\u05b0\0\u05e8\0\u0620"+
    "\0\250\0\250\0\250\0\250\0\250\0\u0658\0\u0690\0\u06c8"+
    "\0\u0700\0\250\0\u0738\0\u0770\0\u07a8\0\250\0\u07e0\0\u0818"+
    "\0\u0850\0\u0888\0\250\0\250\0\250\0\250\0\250\0\250"+
    "\0\u08c0\0\u08f8\0\250\0\u0930\0\250\0\250\0\250\0\250"+
    "\0\250\0\u0968\0\u09a0\0\250\0\u09d8\0\u0a10\0\u0a48\0\u0a80"+
    "\0\u0ab8\0\u0af0\0\u0b28\0\250\0\u0118\0\u0b60\0\u0b98\0\u0bd0"+
    "\0\250\0\250\0\250\0\u0c08\0\u0c40\0\u0c78\0\340\0\u0cb0"+
    "\0\u0ce8\0\250\0\u0d20\0\u0d58\0\u0d90\0\u0dc8\0\u0e00\0\u0e38"+
    "\0\u0e70\0\u0ea8\0\u0ee0\0\u0f18\0\u0f50\0\u0f88\0\u0fc0\0\u0ff8"+
    "\0\u0118\0\u1030\0\u1068\0\u10a0\0\u10d8\0\u1110\0\u1148\0\u1180"+
    "\0\u11b8\0\u11f0\0\u0118\0\u1228\0\u0118\0\u0118\0\u1260\0\u1298"+
    "\0\340\0\u12d0\0\u0118\0\u1308\0\u0118";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[125];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\5\1\6\1\7\1\6\1\7\1\6\1\10\1\11"+
    "\1\12\1\11\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\5\1\7\1\6\1\24\1\25"+
    "\1\26\1\27\1\30\1\31\1\6\1\32\1\33\5\6"+
    "\1\34\1\11\1\5\1\35\1\36\1\37\1\40\3\6"+
    "\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50"+
    "\1\51\1\52\7\53\1\11\26\53\1\11\10\53\1\54"+
    "\3\53\1\11\12\53\1\11\1\53\7\55\1\56\26\55"+
    "\1\11\14\55\1\11\12\55\1\11\1\55\70\0\6\5"+
    "\15\0\3\5\1\0\15\5\2\0\1\5\1\0\6\5"+
    "\10\0\1\5\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\15\6\2\0\1\6\1\0\6\6\10\0\1\6"+
    "\1\0\2\5\1\7\1\5\1\7\1\57\15\0\1\5"+
    "\1\7\1\5\1\0\2\5\1\7\2\5\1\60\1\57"+
    "\2\5\1\61\1\5\1\60\1\61\1\62\1\0\1\5"+
    "\1\0\6\5\10\0\1\5\7\0\1\63\3\0\1\64"+
    "\65\0\1\12\71\0\1\65\67\0\1\66\2\0\1\67"+
    "\31\0\1\70\20\0\7\71\1\0\1\71\1\72\4\71"+
    "\1\73\4\71\1\74\44\71\12\0\1\75\67\0\1\76"+
    "\67\0\1\77\67\0\1\100\7\0\1\101\45\0\7\24"+
    "\1\102\13\24\1\103\2\24\1\104\41\24\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\15\6\2\0\1\6\1\0"+
    "\4\6\1\105\1\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\15\6\2\0\1\6\1\0"+
    "\4\6\1\106\1\6\10\0\1\6\1\0\2\5\1\7"+
    "\1\5\1\7\1\57\15\0\1\5\1\7\1\107\1\0"+
    "\2\5\1\7\2\5\1\60\1\57\2\5\1\61\1\5"+
    "\1\60\1\61\1\62\1\0\1\5\1\0\6\5\10\0"+
    "\1\5\1\0\1\5\5\6\15\0\1\5\2\6\1\0"+
    "\4\6\1\110\10\6\2\0\1\6\1\0\6\6\10\0"+
    "\1\6\1\0\1\5\5\6\15\0\1\5\2\6\1\0"+
    "\6\6\1\111\6\6\2\0\1\6\1\0\6\6\10\0"+
    "\1\6\1\0\1\5\5\6\15\0\1\5\2\6\1\0"+
    "\11\6\1\112\3\6\2\0\1\6\1\0\6\6\10\0"+
    "\1\6\1\0\1\5\5\6\15\0\1\5\2\6\1\0"+
    "\10\6\1\113\4\6\2\0\1\6\1\0\6\6\10\0"+
    "\1\6\3\0\1\62\1\0\1\62\17\0\1\62\4\0"+
    "\1\62\50\0\1\114\55\0\1\5\5\6\15\0\1\5"+
    "\2\6\1\0\7\6\1\115\5\6\2\0\1\6\1\0"+
    "\1\6\1\116\4\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\5\6\1\117\7\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\15\6\2\0\1\6\1\0"+
    "\3\6\1\120\2\6\10\0\1\6\13\0\1\121\141\0"+
    "\1\122\70\0\1\123\2\0\1\5\5\6\15\0\1\5"+
    "\2\6\1\0\15\6\2\0\1\6\1\0\3\6\1\124"+
    "\2\6\10\0\1\6\1\0\7\53\1\0\26\53\1\0"+
    "\10\53\1\0\3\53\1\0\12\53\1\0\1\53\15\0"+
    "\1\56\52\0\7\55\1\0\26\55\1\0\14\55\1\0"+
    "\12\55\1\0\1\55\2\5\1\125\1\5\1\125\1\5"+
    "\1\126\13\0\1\126\1\5\1\125\1\5\1\0\2\5"+
    "\1\125\12\5\2\0\1\5\1\0\6\5\10\0\1\5"+
    "\1\0\6\5\15\0\3\5\1\0\11\5\1\127\2\5"+
    "\1\127\2\0\1\5\1\0\6\5\10\0\1\5\1\0"+
    "\6\5\15\0\3\5\1\0\5\5\1\127\5\5\1\127"+
    "\1\5\2\0\1\5\1\0\6\5\10\0\1\5\1\0"+
    "\2\5\1\62\1\5\1\62\1\130\15\0\1\5\1\62"+
    "\1\5\1\0\2\5\1\62\3\5\1\130\6\5\2\0"+
    "\1\5\1\0\6\5\10\0\1\5\1\0\7\131\1\0"+
    "\6\131\1\132\60\131\1\0\1\131\1\133\4\131\1\132"+
    "\55\131\1\134\2\131\1\0\1\131\1\71\4\131\1\135"+
    "\4\131\1\71\1\136\1\137\3\71\1\136\2\71\2\131"+
    "\2\71\11\131\1\71\16\131\23\102\1\140\2\102\1\73"+
    "\45\102\1\24\2\102\1\0\1\102\1\24\4\102\1\24"+
    "\4\102\2\24\1\141\6\24\2\102\2\24\11\102\1\24"+
    "\16\102\1\5\5\6\15\0\1\5\2\6\1\0\15\6"+
    "\2\0\1\6\1\0\1\142\5\6\10\0\1\6\1\0"+
    "\1\5\5\6\15\0\1\5\2\6\1\0\15\6\2\0"+
    "\1\6\1\0\4\6\1\143\1\6\10\0\1\6\1\0"+
    "\2\5\4\144\15\0\1\5\1\144\1\5\1\0\1\5"+
    "\2\144\3\5\3\144\4\5\2\0\1\5\1\0\2\5"+
    "\1\144\2\5\1\144\10\0\1\5\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\5\6\1\145\7\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\3\6\1\146\11\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\12\6\1\147\2\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\11\6\1\150\3\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\3\6\1\151\11\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\11\6\1\152\3\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\10\6\1\153\4\6\2\0"+
    "\1\6\1\0\6\6\10\0\1\6\1\0\1\5\5\6"+
    "\15\0\1\5\2\6\1\0\15\6\2\0\1\6\1\0"+
    "\1\154\5\6\10\0\1\6\1\0\2\5\1\125\1\5"+
    "\1\125\1\5\15\0\1\5\1\125\1\5\1\0\2\5"+
    "\1\125\2\5\1\60\3\5\1\61\1\5\1\60\1\61"+
    "\2\0\1\5\1\0\6\5\10\0\1\5\3\0\1\125"+
    "\1\0\1\125\17\0\1\125\4\0\1\125\36\0\6\5"+
    "\1\155\13\0\1\155\3\5\1\0\15\5\2\0\1\5"+
    "\1\0\6\5\10\0\1\5\1\0\7\131\1\0\6\131"+
    "\1\73\60\131\1\0\2\131\4\71\1\135\4\71\51\131"+
    "\1\71\2\131\1\0\6\131\1\132\5\131\1\71\4\131"+
    "\1\71\36\131\16\0\1\132\51\0\4\131\1\134\2\131"+
    "\1\0\6\131\1\132\5\131\1\134\4\131\1\134\40\131"+
    "\4\156\1\131\1\0\6\131\1\73\5\131\1\156\3\131"+
    "\2\156\3\131\3\156\12\131\1\156\2\131\1\156\12\131"+
    "\7\102\1\0\62\102\4\157\15\102\1\140\1\157\1\102"+
    "\1\73\1\102\2\157\3\102\3\157\12\102\1\157\2\102"+
    "\1\157\12\102\1\5\5\6\15\0\1\5\2\6\1\0"+
    "\15\6\2\0\1\6\1\0\5\6\1\151\10\0\1\6"+
    "\1\0\1\5\5\6\15\0\1\5\2\6\1\0\11\6"+
    "\1\160\3\6\2\0\1\6\1\0\6\6\10\0\1\6"+
    "\1\0\2\5\4\144\15\0\1\5\1\144\1\5\1\0"+
    "\1\5\2\144\2\5\1\161\3\144\1\162\1\5\1\161"+
    "\1\162\2\0\1\5\1\0\2\5\1\144\2\5\1\144"+
    "\10\0\1\5\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\6\6\1\163\6\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\5\6\1\164\7\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\6\6\1\165\6\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\12\6\1\145\2\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\11\6\1\166\3\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\4\6\1\151\10\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\11\6\1\167\3\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\3\0\1\5\1\0\1\5\17\0\1\5"+
    "\4\0\1\5\36\0\2\131\4\71\1\131\1\0\6\131"+
    "\1\73\5\131\1\71\3\131\2\71\3\131\3\71\12\131"+
    "\1\71\2\131\1\71\12\131\2\102\4\24\15\102\1\140"+
    "\1\24\1\102\1\73\1\102\2\24\3\102\3\24\12\102"+
    "\1\24\2\102\1\24\12\102\1\5\5\6\15\0\1\5"+
    "\2\6\1\0\6\6\1\170\6\6\2\0\1\6\1\0"+
    "\6\6\10\0\1\6\1\0\6\5\15\0\3\5\1\0"+
    "\11\5\1\171\2\5\1\171\2\0\1\5\1\0\6\5"+
    "\10\0\1\5\1\0\6\5\15\0\3\5\1\0\5\5"+
    "\1\171\5\5\1\171\1\5\2\0\1\5\1\0\6\5"+
    "\10\0\1\5\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\4\6\1\172\10\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\6\6\1\173\6\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\10\6\1\174\4\6\2\0\1\6\1\0\6\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\15\6\2\0\1\6\1\0\1\6\1\175\4\6"+
    "\10\0\1\6\1\0\1\5\5\6\15\0\1\5\2\6"+
    "\1\0\15\6\2\0\1\6\1\0\1\6\1\151\4\6"+
    "\10\0\1\6\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4928];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\10\4\1\1\11\2\1\2\11\23\1\5\11"+
    "\4\1\1\11\3\1\1\11\4\1\6\11\2\1\1\11"+
    "\1\1\5\11\2\1\1\11\7\1\1\11\4\1\3\11"+
    "\2\1\1\0\3\1\1\11\22\1\1\0\20\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[125];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    public int parse() throws IOException
    {
        return yylex();
    }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public TencTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public TencTokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 156) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 2: 
          { addToken(Token.IDENTIFIER);
          }
        case 48: break;
        case 12: 
          { addToken(Token.ANTI);
          }
        case 49: break;
        case 42: 
          { addToken(Token.DATA_TYPE_NAME);
          }
        case 50: break;
        case 29: 
          { addToken(Token.LESSEQ);
          }
        case 51: break;
        case 20: 
          { addToken(Token.MOD);
          }
        case 52: break;
        case 21: 
          { addToken(Token.SEMICOLON);
          }
        case 53: break;
        case 5: 
          { addToken(Token.ASSIGN);
          }
        case 54: break;
        case 28: 
          { yybegin(MLC);
          }
        case 55: break;
        case 36: 
          { addToken(Token.IF);
          }
        case 56: break;
        case 47: 
          { addToken(Token.RETURN);
          }
        case 57: break;
        case 45: 
          { addToken(Token.NULL);
          }
        case 58: break;
        case 11: 
          { addToken(Token.GREATER);
          }
        case 59: break;
        case 39: 
          { addToken(Token.OR);
          }
        case 60: break;
        case 26: 
          { addToken(Token.DIVIDEASSIGN);
          }
        case 61: break;
        case 24: 
          { addToken(Token.PLUSASSIGN);
          }
        case 62: break;
        case 30: 
          { addToken(Token.GREATEREQ);
          }
        case 63: break;
        case 32: 
          { addToken(Token.MINUSASSIGN);
          }
        case 64: break;
        case 22: 
          { yybegin(YYINITIAL);
          }
        case 65: break;
        case 41: 
          { addToken(Token.DATA_HEX);
          }
        case 66: break;
        case 27: 
          { yybegin(EOL_COMMENT);
          }
        case 67: break;
        case 31: 
          { addToken(Token.NOTEQUAL);
          }
        case 68: break;
        case 17: 
          { addToken(Token.OPEN_SQUARE);
          }
        case 69: break;
        case 3: 
          { addToken(Token.DATA_INTEGER);
          }
        case 70: break;
        case 35: 
          { addToken(Token.TIMESASSIGN);
          }
        case 71: break;
        case 43: 
          { addToken(Token.DATA_BOOLEAN);
          }
        case 72: break;
        case 14: 
          { addToken(Token.TIMES);
          }
        case 73: break;
        case 10: 
          { addToken(Token.LESSTHAN);
          }
        case 74: break;
        case 4: 
          { addToken(Token.PLUS);
          }
        case 75: break;
        case 46: 
          { addToken(Token.WHILE);
          }
        case 76: break;
        case 25: 
          { addToken(Token.EQUAL);
          }
        case 77: break;
        case 16: 
          { addToken(Token.CLOSE_BRACK);
          }
        case 78: break;
        case 18: 
          { addToken(Token.CLOSE_SQUARE);
          }
        case 79: break;
        case 44: 
          { addToken(Token.ELSE);
          }
        case 80: break;
        case 37: 
          { addToken(Token.MODASSIGN);
          }
        case 81: break;
        case 15: 
          { addToken(Token.OPEN_BRACK);
          }
        case 82: break;
        case 8: 
          { addToken(Token.DIVIDE);
          }
        case 83: break;
        case 33: 
          { addToken(Token.MINUSMINUS);
          }
        case 84: break;
        case 7: 
          { addToken(Token.CLOSE_PAREN);
          }
        case 85: break;
        case 6: 
          { addToken(Token.OPEN_PAREN);
          }
        case 86: break;
        case 13: 
          { addToken(Token.MINUS);
          }
        case 87: break;
        case 40: 
          { addToken(Token.DATA_CHAR);
          }
        case 88: break;
        case 9: 
          { addToken(Token.UNKNOWN);
          }
        case 89: break;
        case 34: 
          { addToken(Token.DATA_STRING);
          }
        case 90: break;
        case 38: 
          { addToken(Token.AND);
          }
        case 91: break;
        case 19: 
          { addToken(Token.COMMA);
          }
        case 92: break;
        case 23: 
          { addToken(Token.PLUSPLUS);
          }
        case 93: break;
        case 1: 
          { 
          }
        case 94: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case EOL_COMMENT: {
              return YYEOF;
            }
            case 126: break;
            case YYINITIAL: {
              return YYEOF;
            }
            case 127: break;
            case MLC: {
              return YYEOF;
            }
            case 128: break;
            default:
            return YYEOF;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
