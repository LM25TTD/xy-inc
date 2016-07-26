package com.lm25ttd.xyinc.model;

import java.io.Serializable;

/**
 * Interface for everything that is capable of being identified.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public interface Identifiable<T extends Serializable & Comparable<T>> {

	/**
	 * Returns the id of this object. The id can be null.
	 */
	public T getId();

	/**
	 * Sets the id of this object.
	 */
	public void setId(T id);

	/**
	 * Verifies if this object has an specified id. It should work for null
	 * values.
	 *
	 * @param id
	 *            The value to verify.
	 */
	public boolean hasId(T id);

	/**
	 * Selfsameness is the quality of being identical with itself. Thus, it
	 * indicates whether objects are the exact same one; not any other. It means
	 * the objects must have same class and identifier.<br>
	 *
	 * Selfsameness differs from equality, since equal domain objects may not be
	 * the same entity. Take twin siblings as example, they are equal but not
	 * selfsame, since they are identified by different names and documents.<br>
	 *
	 * Anonymous objects are never selfsame.
	 *
	 * @param object
	 *            The identifiable object to compare
	 * @return true if objects have the same class and same non-null identifier;
	 *         false otherwise.
	 */
	public boolean selfsame(Identifiable<?> object);

	/**
	 * Indicates whether this object has a null id.
	 *
	 * @param object
	 *            The identifiable object to compare
	 * @return true if id is null.
	 * @since v 0.0.1
	 */
	public boolean anonymous();

}
