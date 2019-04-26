package n1cc3.utils.time;

import java.time.Duration;
import java.time.LocalTime;

import static java.time.Duration.between;

public class TimeRange extends Range<LocalTime> {

	protected TimeRange(LocalTime start, LocalTime end) {
		super(end.isBefore(start) ? end : start, end.isBefore(start) ? start : end);
	}

	/**
	 * Start inclusive, end exclusive,
	 */
	@Override
	public final boolean overlaps(Range<LocalTime> other) {
		if (other.compareTo(this) < 0) {
			return start.isBefore(other.end) && !end.isBefore(other.start);
		} else {
			return other.start.isBefore(end) && !other.end.isBefore(start);
		}
	}

	@Override
	public final int compareTo(Range<LocalTime> other) {
		return start.compareTo(other.start);
	}

	public Duration getDuration() {
		return between(start, end);
	}

	public static TimeRange range(LocalTime start, LocalTime end) {
		return new TimeRange(start, end);
	}

}
