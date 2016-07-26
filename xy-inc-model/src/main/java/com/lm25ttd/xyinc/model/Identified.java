package com.lm25ttd.xyinc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.lm25ttd.xyinc.core.exceptions.IdentifierUpdatedException;
import com.lm25ttd.xyinc.core.utils.Utils;

/**
 * Base class for every entity in the system.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
@MappedSuperclass
public abstract class Identified<T extends Serializable & Comparable<T>> implements Identifiable<T> {
	@Id
	@GeneratedValue(generator = "ID")
	@Column(name = "ID")
	private T id;

	protected Identified() {
		this.setId(null);
	}

	protected Identified(T id) {
		this.setId(id);
	}

	@Override
	public T getId() {
		return this.id;
	}

	@Override
	public void setId(T id) {

		/*
		 * Id update is illegal, usually bug... An update occurs when the id: 1.
		 * is already set and 2. is different of provided id
		 */
		if ((this.id != null) && !this.id.equals(id)) {
			throw new IdentifierUpdatedException();
		}

		// Set the id since it is all right
		this.id = id;

	}

	@Override
	public boolean hasId(T value) {
		return Utils.equals(this.getId(), value);
	}

	@Override
	@Transient
	public boolean anonymous() {
		return this.getId() == null;
	}

	@Override
	@Transient
	public boolean selfsame(Identifiable<?> other) {

		// Null is never selfsame to instanced objects
		if (other == null) {
			return false;
		}

		/*
		 * It does not make sense to say objects are the same entity or not
		 * while identification is pending. The id must be provided before
		 * selfsameness comparison.
		 */
		if (this.anonymous() || other.anonymous()) {
			return false;
		}

		// Selfsame objects must have same class
		if (this.getClass() != other.getClass()) {
			return false;
		}

		// Selfsame objects must have same id
		return this.getId().equals(other.getId());

	}

	@Override
	public boolean equals(Object other) {

		/*
		 * At this point, all we now is that the object has an Id. Thus, for
		 * convenience, the Id is used for equality comparison by default
		 * (selfsameness). But selfsameness is not the same of equality, so this
		 * behavior may be overridden by subclasses.
		 */
		if (other instanceof Identified) {
			return this.selfsame((Identified<?>) other);
		}
		return false;

	}

	@Override
	public int hashCode() {

		if (this.anonymous()) {
			return 0;
		}
		return this.getId().hashCode();

	}

	@Override
	public String toString() {
		return String.valueOf(this.getId());
	}

}
