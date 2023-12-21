import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    void hippodromeExceptionNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void hippodromeExceptionMessageNull() {
        Throwable exception = assertThrows(Exception.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void hippodromeExceptionParam() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));
    }

    @Test
    void hippodromeExceptionMessageParam() {
        Throwable exception = assertThrows(Exception.class, () -> new Hippodrome(new ArrayList<Horse>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> expected = new ArrayList<>();
        for(int i = 1; i <= 30; i++) {
            expected.add(new Horse("horse" + i, i + 7));
        }
        List<Horse> actual = new Hippodrome(expected).getHorses();
        assertEquals(expected, actual);
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        new Hippodrome(horses).move();

        for(Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("horse1", 1, 2.5);
        Horse horse2 = new Horse("horse2", 1.1, 2.9);
        Horse horse3 = new Horse("horse3", 1.5, 3.3);
        Horse horse4 = new Horse("horse4", 1, 2.6);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse3, hippodrome.getWinner());
    }
}
