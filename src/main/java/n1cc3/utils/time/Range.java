package n1cc3.utils.time;

import java.util.Objects;

abstract class Range<T> implements Comparable<Range<T>> {

	protected final T start;
	protected final T end;

	protected Range(T start, T end) {
		this.start = start;
		this.end = end;
	}

	public abstract boolean overlaps(Range<T> other);

	public T getStart() {
		return start;
	}

	public T getEnd() {
		return end;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Range)) return false;
		Range range = (Range) o;
		return start.equals(range.start) && end.equals(range.end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}

}
