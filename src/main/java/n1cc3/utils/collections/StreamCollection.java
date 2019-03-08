package n1cc3.utils.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Contains stream methods to be used on collection.
 *
 * @author nlindgre, bengberg
 */
public interface StreamCollection<E> extends Collection<E> {

	/**
	 * @see Stream#allMatch(Predicate)
	 */
	default boolean allMatch(Predicate<E> predicate) {
		return stream().allMatch(predicate);
	}

	/**
	 * @see Stream#anyMatch(Predicate)
	 */
	default boolean anyMatch(Predicate<E> predicate) {
		return stream().anyMatch(predicate);
	}

	/**
	 * @see Stream#noneMatch(Predicate)
	 */
	default boolean noneMatch(Predicate<E> predicate) {
		return stream().noneMatch(predicate);
	}

	/**
	 * @see Stream#min(Comparator)
	 */
	default Optional<E> min(Comparator<E> comparator) {
		return stream().min(comparator);
	}

	/**
	 * @see Stream#max(Comparator)
	 */
	default Optional<E> max(Comparator<E> comparator) {
		return stream().max(comparator);
	}

	/**
	 * Returns the first element fulfilling predicate.
	 */
	default Optional<E> find(Predicate<E> predicate) {
		return stream().filter(predicate).findFirst();
	}

	/**
	 * See <a href="https://www.scala-lang.org/api/current/scala/Array.html#groupBy[K](f:A=%3EK):scala.collection.immutable.Map[K,Repr]">groupBy scala doc</a>
	 */
	default <K> Map<K, StreamList<E>> groupBy(Function<E, K> groupBy) {
		HashMap<K, StreamList<E>> map = new HashMap<>();
		forEach(e -> map.merge(groupBy.apply(e), new StreamList<>(e), (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		}));
		return map;
	}

}
