package n1cc3.utils.time;

import static java.time.Duration.between;

import java.time.Duration;
import java.time.LocalDate;

public class DateRange extends Range<LocalDate> {

	protected DateRange(LocalDate start, LocalDate end) {
		super(end.isBefore(start) ? end : start, end.isBefore(start) ? start : end);
	}

	protected DateRange(LocalDate start, int days) {
		this(start, start.plusDays(days));
	}

	/**
	 * Start inclusive, end inclusive,
	 */
	@Override
	public final boolean overlaps(Range<LocalDate> other) {
		return !(other.start.isAfter(end) || other.end.isBefore(start));
	}

	@Override
	public final int compareTo(Range<LocalDate> other) {
		return start.compareTo(other.start);
	}

	public Duration getDuration() {
		return between(start, end);
	}

	public static DateRange range(LocalDate start, LocalDate end) {
		return new DateRange(start, end);
	}

	public static DateRange range(LocalDate start, int days) {
		return new DateRange(start, days);
	}

}
