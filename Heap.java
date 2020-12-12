
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Heap<T> implements Cloneable, Serializable {

	static final long serialVersionUID = -12388768391420L;
	private List<T> elements;
	private Comparator<T> comparator;
	
	public Heap() {
	  this.elements = new ArrayList<>();
	  this.comparator = Comparator.comparingInt(T::hashCode);
	}

	public Heap(Comparator<T> comparator) {
		this.elements = new ArrayList<>();
		this.comparator = comparator;
	}

	public Heap(int n, Comparator<T> comparator) {
		  this.elements = new ArrayList<>(n);
		  this.comparator = comparator;
	}
	
	public void add(T value) {
		elements.add(value);
		int index = elements.size()-1;
		while (index != 0 && comparator.compare(elements.get(index), elements.get((index-1)/2)) < 0) {
			int parentIndex = (index-1)/2;
			Collections.swap(elements, parentIndex, index);
			index = parentIndex;
		}
	}
	
	public List<T> sort() {
		List<T> result = new ArrayList<>(elements.size());
		while (!elements.isEmpty()) {
			result.add(elements.get(0));
			elements.set(0, elements.get(elements.size()-1));
			elements.remove(elements.size()-1);
			int index = 0;
			while ((2*index+1 < elements.size() && comparator.compare(elements.get(index), elements.get(2*index+1)) > 0) ||
					(2*index+2 < elements.size() && comparator.compare(elements.get(index), elements.get(2*index+2)) > 0)) {
				int smallerIndex = 2*index+1;
				if (2*index+2 < elements.size() && comparator.compare(elements.get(2*index+2), elements.get(2*index+1)) < 0)
					smallerIndex = 2*index+2;
				T temp = elements.get(index);
				elements.set(index, elements.get(smallerIndex));
				elements.set(smallerIndex, temp);
				index = smallerIndex;
			}
		}
		return result;
	}

	public int size() {
		return elements.size();
	}

	@SuppressWarnings("unchecked")
	public Heap<T> clone() throws CloneNotSupportedException {
		Heap<T> copy = (Heap<T>)super.clone();
		copy.elements = new ArrayList<>(this.elements);
		return copy;
	}

	public void setComparator(Comparator<T> comparator) {
		List<T> values = elements;
		this.comparator = comparator;
		elements = new ArrayList<>(values.size());
		// recreate the heap with new comparator
		while (!values.isEmpty()) add(values.remove(0));
	}
	
}
