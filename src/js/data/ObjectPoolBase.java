package js.data;

public abstract class ObjectPoolBase<T> implements Iterable<T>
{
	protected int freeCount = 0;
	protected int usedCount = 0;
	protected int createCount = 0;
	protected int allocateCount = 0;

	protected ObjectFactory<T> factory = null;

	public ObjectPoolBase(ObjectFactory<T> factory)
	{
		this.factory = factory;
	}

	public abstract T allocate();

	public interface ObjectFactory<T>
	{
		T create();
	}

	public String info()
	{
		return "[freeCount=" + freeCount + ", usedCount=" + usedCount + ", createCount=" + createCount
				+ ", allocateCount=" + allocateCount + "]";
	}

	public int freeCount()
	{
		return freeCount;
	}

	public int usedCount()
	{
		return usedCount;
	}

	public int createdCount()
	{
		return createCount;
	}
}
