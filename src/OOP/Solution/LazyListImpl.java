package OOP.Solution;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import OOP.Provided.LastItemException;
import OOP.Provided.LazyList;
import OOP.Provided.NoMoreItemsException;
import OOP.Provided.NotNaturalException;
import OOP.Provided.Wrapper;

public class LazyListImpl<T> implements LazyList<T>{

	T head;
	Supplier<T> f;
	
    LazyListImpl(T head, Supplier<T> f) {
    	this.head = head;
    	this.f = f;
    }

    @Override
    public T getHead() {
        return head;
    }

    @Override
    public void tail() throws LastItemException {
        if (f == null)
			throw new LastItemException();
        head = f.get();
    }

    @Override
    public void tail(Integer count) throws NoMoreItemsException, NotNaturalException {
        // Natural contain zero
    	if (count < 0)
    		throw new NotNaturalException();
    	for(@SuppressWarnings("unused") Integer ¢ : IntStream.rangeClosed(0, count - 1).toArray())
			try {
				tail();
			} catch (LastItemException e) {
				throw new NoMoreItemsException();
			}
    }

    @Override
    public String printElements(Integer count) throws NoMoreItemsException, NotNaturalException {
        StringBuilder s = new StringBuilder();
        if (count < 0)
    		throw new NotNaturalException();
        
        for(Integer ¢ : IntStream.rangeClosed(0, count - 1).toArray()) {
    		s.append(head);
    		if (¢ < count - 1) {
    			s.append(" ");
    			try{
    				tail();
    			}	catch (LastItemException e) {
    				throw new NoMoreItemsException();
    			}
    		}
        }
    	return s + "";
    }

    @Override
    public void map(Function<T, T> mapper) {
    	Supplier<T> oldF = f;
    	f = () -> mapper.apply(oldF.get());
    	head = mapper.apply(head);
    }

    @Override
    public void filter(Predicate<T> t) throws NoMoreItemsException {
        
    }
}
