package n1cc3.utils.time;

import static java.time.Duration.ofSeconds;
import static java.time.LocalDate.ofEpochDay;
import static java.time.LocalTime.ofSecondOfDay;
import static n1cc3.utils.time.DateTimeRange.range;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDateTime;

import org.junit.Test;

public class DateTimeRangeTest {

	@Test
	public void constructorAndEquals() {
		LocalDateTime t1 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(1));
		LocalDateTime t2 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(2));
		LocalDateTime t3 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(3));
		DateTimeRange t1to2 = range(t1, t2);
		DateTimeRange t2to1 = range(t2, t1);
		DateTimeRange t2to3 = range(t2, t3);
		DateTimeRange t1to2byDuration = range(t1, ofSeconds(1));

		assertSoftly(softly -> {
			softly.assertThat(t1to2.getStart()).isEqualTo(t1);
			softly.assertThat(t1to2.getEnd()).isEqualTo(t2);

			softly.assertThat(t1to2).isEqualTo(t2to1);
			softly.assertThat(t1to2).isNotEqualTo(t2to3);

			softly.assertThat(t1to2).isEqualTo(t1to2byDuration);
			softly.assertThat(t1to2.hashCode()).isEqualTo(t1to2byDuration.hashCode());
		});
	}

	@Test
	public void overlaps() {
		LocalDateTime t1 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(1));
		LocalDateTime t2 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(2));
		LocalDateTime t3 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(3));
		LocalDateTime t4 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(4));
		DateTimeRange t1to2 = range(t1, t2);
		DateTimeRange t1to3 = range(t1, t3);
		DateTimeRange t2to3 = range(t2, t3);
		DateTimeRange t2to4 = range(t2, t4);
		DateTimeRange t3to4 = range(t3, t4);
		DateTimeRange t1to4 = range(t1, t4);

		assertSoftly(softly -> {
			softly.assertThat(t1to2.overlaps(t2to3)).isFalse();
			softly.assertThat(t2to3.overlaps(t1to2)).isFalse();
			softly.assertThat(t1to2.overlaps(t3to4)).isFalse();
			softly.assertThat(t2to3.overlaps(t1to4)).isTrue();
			softly.assertThat(t1to3.overlaps(t2to4)).isTrue();
		});
	}

	@Test
	public void compare() {
		LocalDateTime t1 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(1));
		LocalDateTime t2 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(2));
		LocalDateTime t3 = LocalDateTime.of(ofEpochDay(0), ofSecondOfDay(3));

		DateTimeRange t1to3 = range(t1, t3);
		DateTimeRange t2to3 = range(t2, t3);
		DateTimeRange t1to2byDuration = range(t1, ofSeconds(1));

		assertSoftly(softly -> {
			softly.assertThat(t1to3).isLessThan(t2to3);
			softly.assertThat(t1to3).isEqualByComparingTo(t1to2byDuration);
		});
	}

}
