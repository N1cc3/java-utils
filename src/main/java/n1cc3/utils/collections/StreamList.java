package n1cc3.utils.collections;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ArrayList with stream methods.
 *
 * @param <E> Element type
 * @author nlindgre
 */
public class StreamList<E> extends ArrayList<E> implements StreamCollection<E> {

	public StreamList() {
		super();
	}

	public StreamList(Iterable<? extends E> iterable) {
		super();

		for (E e : iterable) {
			add(e);
		}
	}

	@SafeVarargs
	public StreamList(E... elements) {
		super();
		addAll(asList(elements));
	}

	public Optional<E> first() {
		if (isEmpty()) return empty();
		return of(get(0));
	}

	public Optional<E> last() {
		if (isEmpty()) return empty();
		return of(get(size() - 1));
	}

	/**
	 * @see Stream#filter(Predicate)
	 */
	public StreamList<E> filter(Predicate<E> predicate) {
		return stream().filter(predicate).collect(Collectors.toCollection(StreamList::new));
	}

	/**
	 * @see Stream#map(Function)
	 */
	public <R> StreamList<R> map(Function<? super E, ? extends R> function) {
		return stream().map(function).collect(Collectors.toCollection(StreamList::new));
	}

	/**
	 * @see Stream#flatMap(Function)
	 */
	public <R> StreamList<R> flatMap(Function<? super E, ? extends Collection<? extends R>> function) {
		return stream().map(function)
		               .flatMap(Collection::stream)
		               .collect(Collectors.toCollection(StreamList::new));
	}

	/**
	 * @see Stream#distinct()
	 */
	public StreamList<E> distinct() {
		return stream().distinct().collect(Collectors.toCollection(StreamList::new));
	}

	/**
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/splice">Javascript splice doc</a>
	 */
	public StreamList<E> splice(final int start, final int deleteCount) {
		final List<E> sublist = subList(start, start + Math.min(size() - start, deleteCount));
		final StreamList<E> subStreamList = new StreamList<>(sublist);
		sublist.clear();
		return subStreamList;
	}

	/**
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/splice">Javascript splice doc</a>
	 */
	public StreamList<E> splice(final int start) {
		return splice(start, size());
	}

	/**
	 * @see Collections#shuffle(List)
	 */
	public StreamList<E> shuffled() {
		StreamList<E> list = new StreamList<>(this);
		Collections.shuffle(list);
		return list;
	}

	/**
	 * @see Collections#unmodifiableList(List)
	 */
	public List<E> toImmutable() {
		return Collections.unmodifiableList(this);
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
	public static <E> StreamList<E> list(E... elements) {
		return new StreamList<>(elements);
	}

	public static <E> StreamList<E> list(Iterable<? extends E> iterable) {
		return new StreamList<>(iterable);
	}

	public static <E> StreamList<E> list() {
		return new StreamList<>();
	}

	/**
	 * @return A list containing all integers between given parameters (end exclusive).
	 */
	public static StreamList<Integer> range(int from, int to) {
		return IntStream.range(from, to).boxed().collect(Collectors.toCollection(StreamList::new));
	}

	/**
	 * @return List of values of the optionals that has one.
	 */
	public static <T, E extends Optional<T>> StreamList<T> existing(StreamList<E> list) {
		return list.filter(Optional::isPresent).map(Optional::get);
	}

}
