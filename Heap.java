package edu.czapla.reactdemo.misc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Heap<T> {

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
			Collections.swap(elements, index, parentIndex);
//			T temp = elements.get(parentIndex);
//			elements.set(parentIndex, elements.get(index));
//			elements.set(index, temp);
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
				Collections.swap(elements, index, smallerIndex);
//				T temp = elements.get(index);
//				elements.set(index, elements.get(smallerIndex));
//				elements.set(smallerIndex, temp);
				index = smallerIndex;
			}
		}
		return result;
	}

	public int size() {
		return elements.size();
	}
	
}
