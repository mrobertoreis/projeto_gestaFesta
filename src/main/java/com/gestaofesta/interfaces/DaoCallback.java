package com.gestaofesta.interfaces;

public interface DaoCallback<T> {
	
	public void onStart();
	
	public void onResult(T result, int status);
	
	public void onError(Exception error);

}
