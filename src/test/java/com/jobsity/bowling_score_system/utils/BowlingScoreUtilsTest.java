package com.jobsity.bowling_score_system.utils;

import static com.jobsity.bowling_score_system.utils.BowlingScoreUtils.getFileContent;
import static com.jobsity.bowling_score_system.utils.BowlingScoreUtils.getScoreNumberFromText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BowlingScoreUtilsTest {

  @Test
  void shouldLoadFileContent() {
    String fileName = "src/test/resources/negative/free-text.txt";
    String fileContent = String.join("", getFileContent(fileName));
    assertNotNull(fileContent);
    assertTrue(fileContent.length() > 0);
  }

  @Test
  void shouldConvertValidNumberTextToInteger() {
    int convertedNumber = getScoreNumberFromText("10");
    assertEquals(10, convertedNumber);
  }

  @Test
  void shouldReturnZeroForInvalidNumberText() {
    int convertedNumber = getScoreNumberFromText("test");
    assertEquals(0, convertedNumber);
  }
}
