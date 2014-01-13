package com.notenet.feedparser.entity;

public abstract class IndexDocument<E> {
	String _index;
	String _type;
	String _id;
	long _version;
	String exists;
	E _source;

	public String get_index() {
		return _index;
	}

	public void set_index(String _index) {
		this._index = _index;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long get_version() {
		return _version;
	}

	public void set_version(long _version) {
		this._version = _version;
	}

	public String getExists() {
		return exists;
	}

	public void setExists(String exists) {
		this.exists = exists;
	}

	public E get_source() {
		return _source;
	}

	public void set_source(E _source) {
		this._source = _source;
	}
}
