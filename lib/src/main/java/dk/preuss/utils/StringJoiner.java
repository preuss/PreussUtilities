package dk.preuss.utils;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The StringJoiner
 */
public class StringJoiner {
	private final java.util.StringJoiner localJoiner;

	/**
	 * Constructs {@code StringJoiner} with no characters in it, with no
	 * {@code prefix} or {@code suffix}, and "," as the supplied
	 * {@code delimiter}.
	 */
	public StringJoiner() {
		this(",");
	}

	/**
	 * Constructs a {@code StringJoiner} with no characters in it, with no
	 * {@code prefix} or {@code suffix}, and a copy of the supplied
	 * {@code delimiter}.
	 * If no characters are added to the {@code StringJoiner} and methods
	 * accessing the value of it are invoked, it will not return a
	 * {@code prefix} or {@code suffix} (or properties thereof) in the result,
	 * unless {@code setEmptyValue} has first been called.
	 *
	 * @param delimiter the sequence of characters to be used between each
	 *                  element added to the {@code StringJoiner} value
	 * @throws NullPointerException if {@code delimiter} is {@code null}
	 */
	public StringJoiner(CharSequence delimiter) {
		this(delimiter, "", "");
	}

	/**
	 * Constructs a {@code StringJoiner} with no characters in it using copies
	 * of the supplied {@code prefix}, {@code delimiter} and {@code suffix}.
	 * If no characters are added to the {@code StringJoiner} and methods
	 * accessing the string value of it are invoked, it will return the
	 * {@code prefix + suffix} (or properties thereof) in the result, unless
	 * {@code setEmptyValue} has first been called.
	 *
	 * @param delimiter the sequence of characters to be used between each
	 *                  element added to the {@code StringJoiner}
	 * @param prefix    the sequence of characters to be used at the beginning
	 * @param suffix    the sequence of characters to be used at the end
	 * @throws NullPointerException if {@code prefix}, {@code delimiter}, or
	 *                              {@code suffix} is {@code null}
	 */
	public StringJoiner(CharSequence delimiter,
						CharSequence prefix,
						CharSequence suffix) {
		localJoiner = new java.util.StringJoiner(delimiter, prefix, suffix);
	}

	/**
	 * Sets the sequence of characters to be used when determining the string
	 * representation of this {@code StringJoiner} and no elements have been
	 * added yet, that is, when it is empty.  A copy of the {@code emptyValue}
	 * parameter is made for this purpose. Note that once an add method has been
	 * called, the {@code StringJoiner} is no longer considered empty, even if
	 * the element(s) added correspond to the empty {@code String}.
	 *
	 * @param emptyValue the characters to return as the value of an empty
	 *                   {@code StringJoiner}
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 * @throws NullPointerException when the {@code emptyValue} parameter is
	 *                              {@code null}
	 */
	public StringJoiner setEmptyValue(CharSequence emptyValue) {
		localJoiner.setEmptyValue(emptyValue);
		return this;
	}


	/**
	 * Adds a copy of the given {@code CharSequence} value as the next
	 * element of the {@code StringJoiner} value. If {@code newElement} is
	 * {@code null}, then {@code "null"} is added.
	 *
	 * @param newElement The element to add
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 */
	public StringJoiner add(CharSequence newElement) {
		localJoiner.add(newElement);
		return this;
	}

	/**
	 * Adds the contents of the given {@code StringJoiner} without prefix and
	 * suffix as the next element if it is non-empty. If the given {@code
	 * StringJoiner} is empty, the call has no effect.
	 *
	 * <p>A {@code StringJoiner} is empty if {@link #add(CharSequence) add()}
	 * has never been called, and if {@code merge()} has never been called
	 * with a non-empty {@code StringJoiner} argument.
	 *
	 * <p>If the other {@code StringJoiner} is using a different delimiter,
	 * then elements from the other {@code StringJoiner} are concatenated with
	 * that delimiter and the result is appended to this {@code StringJoiner}
	 * as a single element.
	 *
	 * @param other The {@code StringJoiner} whose contents should be merged
	 *              into this one
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 * @throws NullPointerException if the other {@code StringJoiner} is null
	 */
	public StringJoiner merge(StringJoiner other) {
		localJoiner.merge(other.localJoiner);
		return this;
	}

	/**
	 * Returns the length of the {@code String} representation
	 * of this {@code StringJoiner}. Note that if
	 * no add methods have been called, then the length of the {@code String}
	 * representation (either {@code prefix + suffix} or {@code emptyValue})
	 * will be returned. The value should be equivalent to
	 * {@code toString().length()}.
	 *
	 * @return the length of the current value of {@code StringJoiner}
	 */
	public int length() {
		return localJoiner.length();
	}

	/**
	 * Returns the current value, consisting of the {@code prefix}, the values
	 * added so far separated by the {@code delimiter}, and the {@code suffix},
	 * unless no elements have been added in which case, the
	 * {@code prefix + suffix} or the {@code emptyValue} characters are returned.
	 *
	 * @return the string representation of this {@code StringJoiner}
	 */
	@Override
	public String toString() {
		return localJoiner.toString();
	}

	/**
	 * Only add newElement, if predicate testIfTrue is true.
	 * @param prefix     the sequence of characters to be used at the beginning
	 * @param newElement to be testet and added if legal, added as a String.valueOf
	 * @param testIfTrue Predicate to test if newElement is to be added.
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 * @param <T> The type of newElement, to be used in testIfTrue
	 */
	public <T> StringJoiner addIfTrue(CharSequence prefix, T newElement, Predicate<T> testIfTrue) {
		if (testIfTrue.test(newElement)) {
			return addConcatenated(prefix, String.valueOf(newElement));
		} else {
			return this;
		}
	}

	/**
	 * Only add newElement, if predicate testIfTrue is true else it will
	 * add newOtherElement.
	 * @param prefix           the sequence of characters to be used at the beginning
	 * @param newElement to be testet and added if legal, added as a String.valueOf
	 * @param newOtherElement to be added if newElement is not added.
	 * @param testIfTrue Predicate to test if newElement is to be added.
	 * @param <T> The type of newElement, to be used in testIfTrue
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 */
	public <T> StringJoiner addTrueElseOther(CharSequence prefix, T newElement, CharSequence newOtherElement,
											 Predicate<T> testIfTrue) {
		return testIfTrue.test(newElement) ?
				addConcatenated(prefix, String.valueOf(newElement)) :
				addConcatenated(prefix, newOtherElement);
	}

	/**
	 * Adds multiple elements.
	 * @param newElements  The elements to add
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 */
	public StringJoiner addConcatenated(CharSequence... newElements) {
		StringBuilder element = new StringBuilder();
		for (var newElement : newElements) {
			element.append(newElement);
		}
		return add(element.toString());
	}

	/**
	 * Only adds newElement if newElement is not empty.
	 * Where not empty, means not null or string value is
	 * not zero.
	 * @param prefix  the sequence of characters to be used at the beginning
	 * @param newElement  The element to add
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 */
	public StringJoiner addIfNotEmpty(String prefix, Object newElement) {
		if (null != newElement) {
			return addIfNotEmpty(prefix, String.valueOf(newElement));
		}
		return this;
	}

	/**
	 * Adds if not empty, meaning element can not be
	 * null or empty.
	 *
	 * @param prefix  the sequence of characters to be used at the beginning
	 * @param newElement  The element to add
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 */
	public StringJoiner addIfNotEmpty(String prefix, CharSequence newElement) {
		if (null != newElement && newElement.length() > 0) {
			return addConcatenated(prefix, newElement);
		}
		return this;
	}

	/**
	 * Adds if not blank for element.
	 * Meanning element can not be null, empty
	 * or whitespace.
	 *
	 * @param prefix  the sequence of characters to be used at the beginning
	 * @param newElement  The element to add
	 * @return this {@code StringJoiner} itself so the calls may be chained
	 */
	public StringJoiner addIfNotBlank(String prefix, CharSequence newElement) {
		int strLen;
		if (null != newElement && (strLen = newElement.length()) > 0) {
			for (int i = 0; i < strLen; i++) {
				if ((!Character.isWhitespace(newElement.charAt(i)))) {
					return add(newElement);
				}
			}
		}
		return this;
	}
}
