package com.googlecode.g2re.excel;

/**
 * An enumeration type listing all available Excel number formats.
 * @author Brad Rydzewski
 */
public enum NumberFormat {
  /**
   * The default format.  This is equivalent to a number format of '#'.
   */
  DEFAULT,

  /**
   * Formatting for an integer number.  This is equivalent to a DecimalFormat.
   * of "0"
   */
  INTEGER,

  /**
   * Formatting for a float.  This formats number to two decimal places.  It
   * is equivalent to a DecimalFormat of "0.00".
   */
  FLOAT,

  /**
   * Formatting for an integer that has a thousands separator.
   * Equivalent to a DecimalFormat of "#,##0".
   */
  THOUSANDS_INTEGER,

  /**
   * Formatting for a float that has a thousands separator.
   * Equivalent to a DecimalFormat of "#,##0.00".
   */
  THOUSANDS_FLOAT,

  /**
   * Formatting for an integer which is presented in accounting format
   * (ie. deficits appear in parentheses).
   * Equivalent to a DecimalFormat of "$#,##0;($#,##0)".
   */
  ACCOUNTING_INTEGER,

  /**
   * As ACCOUNTING_INTEGER except that deficits appear coloured red.
   */
  ACCOUNTING_RED_INTEGER,

  /**
   * Formatting for an integer which is presented in accounting format
   * (ie. deficits appear in parentheses).
   * Equivalent to a DecimalFormat of  "$#,##0;($#,##0)".
   */
  ACCOUNTING_FLOAT,

  /**
   * As ACCOUNTING_FLOAT except that deficits appear coloured red.
   */
  ACCOUNTING_RED_FLOAT,

  /**
   * Formatting for an integer presented as a percentage.
   * Equivalent to a DecimalFormat of "0%".
   */
  PERCENT_INTEGER,

  /**
   * Formatting for a float percentage.
   * Equivalent to a DecimalFormat "0.00%".
   */
  PERCENT_FLOAT,

  /**
   * Formatting for exponential or scientific notation.
   * Equivalent to a DecimalFormat "0.00E00".
   */
  EXPONENTIAL,

  /**
   * Formatting for one digit fractions.
   */
  FRACTION_ONE_DIGIT,

  /**
   * Formatting for two digit fractions.
   */
  FRACTION_TWO_DIGITS,

  // Now describe the more obscure formats

  /**
   * Equivalent to a DecimalFormat "#,##0;(#,##0)".
   */
  FORMAT1,

  /**
   * Equivalent to FORMAT1 except deficits are coloured red.
   */
  FORMAT2,

  /**
   * Equivalent to DecimalFormat "#,##0.00;(#,##0.00)".
   */
  FORMAT3,

  /**
   * Equivalent to FORMAT3 except deficits are coloured red.
   */
  FORMAT4,

  /**
   * Equivalent to DecimalFormat "#,##0;(#,##0)".
   */
  FORMAT5,

  /**
   * Equivalent to FORMAT5 except deficits are coloured red.
   */
  FORMAT6,

  /**
   * Equivalent to DecimalFormat "#,##0.00;(#,##0.00)".
   */
  FORMAT7,

  /**
   * Equivalent to FORMAT7 except deficits are coloured red.
   */
  FORMAT8,

  /**
   * Equivalent to FORMAT7.
   */
  FORMAT9,

  /**
   * Equivalent to DecimalFormat "##0.0E0".
   */
  FORMAT10,

  /**
   * Forces numbers to be interpreted as text.
   */
  TEXT
}
