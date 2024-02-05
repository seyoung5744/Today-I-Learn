package chap09;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chap02.FilteringApples.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    public void testMoveRightBy() throws Exception {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);

        assertThat(p2.getX()).isEqualTo(15);
        assertThat(p2.getY()).isEqualTo(5);
    }

    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);
        int result = Point.compareByXAndThenY.compare(p1, p2);
        assertTrue(result < 0);
    }

    @Test
    public void testMoveAllPointsRightBy() throws Exception {
        List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));
        List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        assertThat(newPoints).isEqualTo(expectedPoints);
    }

    @Test
    public void testFilter() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> even = filter(numbers, i -> i % 2 == 0);
        List<Integer> smallerThanThree = filter(numbers, i -> i < 3);

        assertThat(even).isEqualTo(Arrays.asList(2, 4));
        assertThat(smallerThanThree).isEqualTo(Arrays.asList(1, 2));
    }

    public static <T> List<T> filter(List<T> numbers, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e : numbers) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
}