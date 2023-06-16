package stocksageservice.utils;

import org.junit.jupiter.api.Test;

public class EpochTimeDateConversionTest {


//    private EpochTimeDateConversionTest() {
//
//    }

    @Test
    void getCurrentEpochTime_currentTimeInEpoch() {
        // When
        long currentEpochTime = EpochTimeDateConversion.getCurrentEpochTime();
        System.out.println(currentEpochTime);
    }

    @Test
    void convertEpochToDateTime_pochConvertedToDateTime() {
        // Given
        long currentEpochTime = EpochTimeDateConversion.getCurrentEpochTime();

        // When
        String dateTime = EpochTimeDateConversion.convertEpochToDateTime(currentEpochTime);
        System.out.println(dateTime);
    }

    @Test
    void convertEpochToDateTime_epochConvertedToDate() {
        // Given
        long currentEpochTime = EpochTimeDateConversion.getCurrentEpochTime();

        // When
        String date = EpochTimeDateConversion.convertEpochToDate(currentEpochTime);
        System.out.println(date);
    }
}
