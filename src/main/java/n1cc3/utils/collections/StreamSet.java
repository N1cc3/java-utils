package n1cc3.utils.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TreeSet with stream methods.
 *
 * @param <E> Element type
 * @author nlindgre
 */
public class StreamSet<E> extends HashSet<E> implements StreamCollection<E> {

	private StreamSet() {
		super();
	}

	public StreamSet(Iterable<? extends E> iterable) {
		super();

		for (E e : iterable) {
			add(e);
		}
	}

	@SafeVarargs
	public StreamSet(E... elements) {
		super();
		addAll(Arrays.asList(elements));
	}

	/**
	 * @see Stream#filter(Predicate)
	 */
	public StreamSet<E> filter(Predicate<E> predicate) {
		return stream().filter(predicate).collect(Collectors.toCollection(StreamSet::new));
	}

	/**
	 * @see Stream#map(Function)
	 */
	public <R> StreamSet<R> map(Function<? super E, ? extends R> function) {
		return stream().map(function).collect(Collectors.toCollection(StreamSet::new));
	}

	/**
	 * @see Stream#flatMap(Function)
	 */
	public <R> StreamSet<R> flatMap(Function<? super E,? extends Collection<? extends R>> function) {
		return stream().map(function).flatMap(Collection::stream).collect(Collectors.toCollection(StreamSet::new));
	}

	/**
	 * @see Collections#unmodifiableSet(Set)
	 */
	public Set<E> toImmutable() {
		return Collections.unmodifiableSet(this);
	}

	/**
	 * Uses {@link Objects#toString(Object)} on each element.
	 *
	 * @see String#join(CharSequence, Iterable)
	 */
	public String join(String separator) {
		return String.join(separator, map(Objects::toString));
	}

	@SafeVarargs
	public static <E> StreamSet<E> set(E... elements) {
		return new StreamSet<>(elements);
	}

	public static <E> StreamSet<E> set(Iterable<? extends E> iterable) {
		return new StreamSet<>(iterable);
	}

	public static <E> StreamSet<E> set() {
		return new StreamSet<>();
	}

	/**
	 * @return List of values of the optionals that has one.
	 */
	public static <T, E extends Optional<T>> StreamSet<T> existing(StreamSet<E> list) {
		return list.filter(Optional::isPresent).map(Optional::get);
	}

}
