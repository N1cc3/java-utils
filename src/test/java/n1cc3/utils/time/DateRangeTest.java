package n1cc3.utils.time;

import static java.time.LocalDate.ofEpochDay;
import static n1cc3.utils.time.DateRange.range;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.Test;

public class DateRangeTest {

	@Test
	public void constructorAndEquals() {
		DateRange d1to2 = range(ofEpochDay(1), ofEpochDay(2));
		DateRange d2to1 = range(ofEpochDay(2), ofEpochDay(1));
		DateRange d2to3 = range(ofEpochDay(2), ofEpochDay(3));
		DateRange d1to2byDuration = range(ofEpochDay(1), 1);

		assertSoftly(softly -> {
			softly.assertThat(d1to2.getStart()).isEqualTo(ofEpochDay(1));
			softly.assertThat(d1to2.getEnd()).isEqualTo(ofEpochDay(2));

			softly.assertThat(d1to2).isEqualTo(d2to1);
			softly.assertThat(d1to2).isNotEqualTo(d2to3);

			softly.assertThat(d1to2).isEqualTo(d1to2byDuration);
			softly.assertThat(d1to2.hashCode()).isEqualTo(d1to2byDuration.hashCode());
		});
	}

	@Test
	public void overlaps() {
		DateRange d1to2 = range(ofEpochDay(1), ofEpochDay(2));
		DateRange d2to3 = range(ofEpochDay(2), ofEpochDay(3));
		DateRange d3to4 = range(ofEpochDay(3), ofEpochDay(4));
		DateRange d1to4 = range(ofEpochDay(1), ofEpochDay(4));

		assertSoftly(softly -> {
			softly.assertThat(d1to2.overlaps(d2to3)).isTrue();
			softly.assertThat(d2to3.overlaps(d1to2)).isTrue();
			softly.assertThat(d1to2.overlaps(d3to4)).isFalse();
			softly.assertThat(d2to3.overlaps(d1to4)).isTrue();
		});
	}

	@Test
	public void compare() {
		DateRange d1to3 = range(ofEpochDay(1), ofEpochDay(3));
		DateRange d2to3 = range(ofEpochDay(2), ofEpochDay(3));
		DateRange d1to2byDuration = range(ofEpochDay(1), 1);

		assertSoftly(softly -> {
			softly.assertThat(d1to3).isLessThan(d2to3);
			softly.assertThat(d1to3).isEqualByComparingTo(d1to2byDuration);
		});
	}

}
