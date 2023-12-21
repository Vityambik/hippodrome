import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 32.0));
    }

    @Test
    void nullNameMessage() {
        Throwable exception = assertThrows(Exception.class, () -> new Horse(null, 32.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    static Stream<String> argsProviderFactory() {
        return Stream.of("", "\s", "\t", "\n", "\r");
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void horseExceptionNameAllParams(String arg) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(arg, 32.0));
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void horseExceptionMessageNameAllParams(String arg) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(arg, 32.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void horseExceptionSpeedParam() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Spirit", -32.0));
    }

    @Test
    void horseExceptionMessageSpeedParam() {
        Throwable exception = assertThrows(Exception.class, () -> new Horse("Spirit", -32.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void horseExceptionDistanceParam() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Spirit", 32.0, -30.0));
    }

    @Test
    void horseExceptionMessageDistanceParam() {
        Throwable exception = assertThrows(Exception.class, () -> new Horse("Spirit", 32.0, -30.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        assertEquals("Spirit", new Horse("Spirit", 32.0).getName());
    }

    @Test
    void getSpeed() {
        assertEquals(32.0, new Horse("Spirit", 32.0).getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(30.0, new Horse("Spirit", 32.0, 30.0).getDistance());
    }

    @Test
    void getDistanceNull() {
        assertEquals(0.0, new Horse("Spirit", 32.0).getDistance());
    }

    @Test
    void moveUsesGetRandom() {
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Spirit", 32.0).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.3, 0.4})
    void moveCheckTheDistance(double values) {
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Spirit", 32, 52);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(values);
            horse.move();
            assertEquals(52 + 32 * values, horse.getDistance());
        }
    }
}
