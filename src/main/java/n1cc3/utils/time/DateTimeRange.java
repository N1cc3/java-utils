package n1cc3.utils.time;

import static java.time.Duration.between;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeRange extends Range<LocalDateTime> {

	protected DateTimeRange(LocalDateTime start, LocalDateTime end) {
		super(end.isBefore(start) ? end : start, end.isBefore(start) ? start : end);
	}

	protected DateTimeRange(LocalDateTime start, Duration duration) {
		this(start, start.plus(duration));
	}

	/**
	 * Start inclusive, end exclusive.
	 */
	@Override
	public final boolean overlaps(Range<LocalDateTime> other) {
		return other.start.isBefore(end) && other.end.isAfter(start);
	}

	@Override
	public final int compareTo(Range<LocalDateTime> other) {
		return start.compareTo(other.start);
	}

	public Duration getDuration() {
		return between(start, end);
	}

	public static DateTimeRange range(LocalDateTime start, LocalDateTime end) {
		return new DateTimeRange(start, end);
	}

	public static DateTimeRange range(LocalDateTime start, Duration duration) {
		return new DateTimeRange(start, duration);
	}

}
