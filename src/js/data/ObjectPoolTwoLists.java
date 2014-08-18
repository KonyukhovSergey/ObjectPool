package js.data;

import java.util.Iterator;

public class ObjectPoolTwoLists<T> extends ObjectPoolBase<T>
{
	private PoolItem free = new PoolItem();
	private PoolItem used = new PoolItem();

	private PoolIterator<T> iterator;

	public ObjectPoolTwoLists(ObjectFactory<T> factory)
	{
		super(factory);
		iterator = new PoolIterator<T>(free);
	}

	@Override
	public Iterator<T> iterator()
	{
		iterator.item = used;
		return iterator;
	}

	@Override
	public T allocate()
	{
		PoolItem item = null;

		if (free.next == null)
		{
			item = new PoolItem();
			item.data = factory.create();
			createCount++;
		}
		else
		{
			item = free.next;
			item.remove();
			freeCount--;
		}

		used.insert(item);
		usedCount++;
		allocateCount++;

		return item.data;
	}

	private class PoolIterator<T> implements Iterator<T>
	{
		PoolItem item;
		PoolItem free;

		public PoolIterator(PoolItem free)
		{
			this.free = free;
		}

		@Override
		public boolean hasNext()
		{
			return item.next != null;
		}

		@Override
		public T next()
		{
			item = item.next;
			return (T) item.data;
		}

		@Override
		public void remove()
		{
			PoolItem temp = item;
			item = item.prev;
			temp.remove();
			usedCount--;
			free.insert(temp);
			freeCount++;
		}
	}

	private class PoolItem
	{
		T data;

		PoolItem prev, next;

		final void insert(PoolItem item)
		{
			item.prev = this;
			item.next = next;

			if (next != null)
			{
				next.prev = item;
			}

			next = item;
		}

		final void remove()
		{
			if (prev != null)
			{
				prev.next = next;
			}

			if (next != null)
			{
				next.prev = prev;
			}

			prev = null;
			next = null;
		}
	}
}
