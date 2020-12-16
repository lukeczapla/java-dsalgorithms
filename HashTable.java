
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class HashTable<K, V> {
	
	private int size;
	private List<Bucket<Pair<K, V>>> buckets;

	private Set<K> keys;
	
	public HashTable(int size) {
		this.size = size;
		buckets = new ArrayList<>(size);
		keys = new TreeSet<>();
		for (int i = 0; i < size; i++) {
			buckets.add(new Bucket<>());
		}
	}
	
	public void put(K key, V value) {
		int hash = key.hashCode();
		int bucket = hash % size;
		if (get(key) == null) {
			buckets.get(bucket).add(new Pair<>(key, value));
		} else {
			for (Pair<K,V> p : buckets.get(bucket).getValues()) {
				if (p.getKey().equals(key)) p.setValue(value);
			}
		}
		if (!keys.contains(key)) keys.add(key);
	}

	public boolean remove(K key) {
		if (get(key) == null) return false;
		int hash = key.hashCode();
                int bucket = hash % size;
		Bucket<Pair<K,V>> bin = buckets.get(bucket);
		for (Pair<K,V> p : bin.getValues()) {
			if (p.getKey().equals(key)) {
				bin.delete(p);
				return true;
			}
		}
		// shouldn't get here, maybe throw an exception
		return false;
	}

	public Set<K> keys() {
		return keys;
	}
	
	public V get(K key) {
		int hash = key.hashCode();
		int bucket = hash % size;
		if (buckets.get(bucket).size() == 0) return null;
		for (Pair<K,V> p : buckets.get(bucket).getValues()) {
			if (p.getKey().equals(key)) return p.getValue();
		}
		return null;
	}
	
}

