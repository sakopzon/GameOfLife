package OOP.Solution;

public class Wrapper<T>{
	T element;
	
	Wrapper(T ¢) {
		set(¢);
	}
	
	public T get() {
		return element;
	}
	
	public void set(T ¢) {
		element = ¢;
	}
}
