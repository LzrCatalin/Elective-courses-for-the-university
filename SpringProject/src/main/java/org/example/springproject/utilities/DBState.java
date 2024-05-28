package org.example.springproject.utilities;

public class DBState {
	private static DBState instance;
	private boolean readOnly = false;

	private DBState() {}

	public static synchronized DBState getInstance() {
		if (instance == null) {
			instance = new DBState();
		}

		return instance;
	}

	public synchronized void setReadOnly(boolean newState) {
		readOnly = newState;
	}

	public synchronized boolean isReadOnly() {
		return readOnly;
	}
}
