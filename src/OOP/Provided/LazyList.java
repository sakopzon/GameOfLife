package OOP.Provided;


import java.util.function.Function;
import java.util.function.Predicate;

public interface LazyList<T> {

    public T getHead();

    public void tail() throws LastItemException;

    public void tail(Integer count) throws NoMoreItemsException, NotNaturalException;

    public String printElements(Integer count) throws NoMoreItemsException, NotNaturalException;

    public void map(Function<T, T> mapper);

    public void filter(Predicate<T> predicate) throws NoMoreItemsException;

}
