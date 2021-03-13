
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;


public class Bucket<T> {
	private T value = null;
	private LinkedList<T> extras = null;
	
	public void add(T value) {
		if (this.value == null) this.value = value;
		else if (this.extras == null) {
			extras = new LinkedList<>();
			extras.add(value);
		} else {
			extras.add(value);
		}
	}

	// delete the item val from the Bucket, return false if not there.
	public boolean delete(T val) {
		if (this.value == null) return false;
		if (extras == null || extras.size() == 0) {
			if (value.equals(val)) {
				value = null;
				return true;
			}
			return false;
		} else {
			if (value.equals(val)) {
				value = extras.remove(0);
				return true;
			}
			else {
				if (extras.contains(val)) {
		        		extras.remove(val);
					return true;
				}
			}
		}
		return false;
	}
	
	public List<T> getValues() {
		if (value == null) return null;
		if (extras == null) return Collections.singletonList(value);
		ArrayList<T> result = new ArrayList<>(extras.size()+1);
		result.add(value);
		result.addAll(extras);
		return result;
	}
	
	public int size() {
		if (value == null) return 0;
		if (extras == null) return 1;
		return 1+extras.size();
	}
	
}

