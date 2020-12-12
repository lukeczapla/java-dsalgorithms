
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

}
