
public class Pair<R,S> {
	private R key;
	private S value;

	public Pair(R key, S value) {
		this.key = key;
		this.value = value;
	}

	public R getKey() {
		return key;
	}
	
	public S getValue() {
		return value;
	}

	public void setKey(R key) {
		this.key = key;
	}

	public void setValue(S value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			Pair other = (Pair)obj;
			if (this.key.equals(other.key) && this.value.equals(other.value)) return true;
		}
		return false;
	}


}
