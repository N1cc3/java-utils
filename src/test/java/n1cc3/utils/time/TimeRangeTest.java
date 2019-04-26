package n1cc3.utils.time;

import org.junit.Test;

import java.time.LocalTime;

import static n1cc3.utils.time.TimeRange.range;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class TimeRangeTest {

	@Test
	public void constructorAndEquals() {
		TimeRange t1to2 = range(LocalTime.of(1, 0), LocalTime.of(2, 0));
		TimeRange t2to3 = range(LocalTime.of(2, 0), LocalTime.of(3, 0));
		TimeRange t2to1 = range(LocalTime.of(2, 0), LocalTime.of(1, 0));

		assertSoftly(softly -> {
			softly.assertThat(t1to2.getStart()).isEqualTo(LocalTime.of(1, 0));
			softly.assertThat(t1to2.getEnd()).isEqualTo(LocalTime.of(2, 0));

			softly.assertThat(t1to2).isEqualTo(t2to1);
			softly.assertThat(t1to2).isNotEqualTo(t2to3);

			softly.assertThat(t1to2.hashCode()).isEqualTo(t1to2.hashCode());
		});
	}

	@Test
	public void overlaps() {
		TimeRange t1to2 = range(LocalTime.of(1, 0), LocalTime.of(2, 0));
		TimeRange t2to3 = range(LocalTime.of(2, 0), LocalTime.of(3, 0));
		TimeRange t1to4 = range(LocalTime.of(1, 0), LocalTime.of(4, 0));
		TimeRange tAlmost2to3 = range(LocalTime.of(2, 0).minusNanos(1), LocalTime.of(3, 0));


		assertSoftly(softly -> {
			softly.assertThat(t1to2.overlaps(t2to3)).isFalse();
			softly.assertThat(t2to3.overlaps(t1to2)).isFalse();
			softly.assertThat(t1to2.overlaps(t1to4)).isTrue();
			softly.assertThat(t2to3.overlaps(t1to4)).isTrue();
			softly.assertThat(t1to2.overlaps(tAlmost2to3)).isTrue();
		});
	}

	@Test
	public void compare() {
		TimeRange t1to2 = range(LocalTime.of(1, 0), LocalTime.of(2, 0));
		TimeRange t2to3 = range(LocalTime.of(2, 0), LocalTime.of(3, 0));
		TimeRange t1to4 = range(LocalTime.of(1, 0), LocalTime.of(4, 0));

		assertSoftly(softly -> {
			softly.assertThat(t1to2).isLessThan(t2to3);
			softly.assertThat(t1to2).isEqualByComparingTo(t1to4);
		});
	}

}
