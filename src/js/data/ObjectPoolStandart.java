package js.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectPoolStandart<T> extends ObjectPoolBase<T>
{
	private List<T> free = new LinkedList<T>();
	private List<T> used = new LinkedList<T>();

	private PoolIterator<T> iterator;

	public ObjectPoolStandart(ObjectFactory<T> factory)
	{
		super(factory);
		iterator = new PoolIterator<T>(free);
	}

	@Override
	public Iterator<T> iterator()
	{
		iterator.index = 0;
		return iterator;
	}

	@Override
	public T allocate()
	{
		T item = null;

		if (free.size() == 0)
		{
			item = factory.create();
			createCount++;
		}
		else
		{
			item = free.remove(0);
			freeCount--;
		}

		used.add(0,item);
		iterator.index++;
		usedCount++;
		allocateCount++;

		return item;
	}

	private class PoolIterator<T> implements Iterator<T>
	{
		int index;
		List<T> free;

		public PoolIterator(List<T> free)
		{
			this.free = free;
		}

		@Override
		public boolean hasNext()
		{
			return index < used.size();
		}

		@Override
		public T next()
		{
			index++;
			return (T) used.get(index - 1);
		}

		@Override
		public void remove()
		{
			index--;
			free.add((T) used.remove(index));
			usedCount--;
			freeCount++;
		}
	}
}
