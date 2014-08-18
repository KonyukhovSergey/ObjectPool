import java.util.Iterator;
import java.util.Random;

import js.data.ObjectPoolBase;
import js.data.ObjectPoolStandart;
import js.data.ObjectPoolTwoLists;

public class Prog
{
	private static final int FRAMES = 3000;

	public static void main(String[] args)
	{
		System.out.println("ready!");

		testPool(new ObjectPoolTwoLists<Bullet>(Bullet.factory));
		testPool(new ObjectPoolStandart<Bullet>(Bullet.factory));

	}

	private static void testPool(ObjectPoolBase<Bullet> pool)
	{
		System.out.println("testing pool name: " + pool.getClass().getSimpleName());
		
		long startTick = System.currentTimeMillis();
		test(pool);
		long endTick = System.currentTimeMillis();

		System.out.println("elapsed = " + (endTick - startTick));
		System.out.println();
	}

	private static void test(ObjectPoolBase<Bullet> pool)
	{
		Random rnd = new Random(2839);

		pool.allocate().setup(rnd.nextFloat() * 2.0f + 1.0f, 0);
		float dt = 0.1f;

		int counter = 0;

		while (pool.usedCount() > 0 || counter < FRAMES)
		{
			counter++;

			if (counter % 100 == 0 && counter < FRAMES)
			{
				pool.allocate().setup(rnd.nextFloat() * 2.0f + 1.0f, 0);
			}

			for (Iterator<Bullet> it = pool.iterator(); it.hasNext();)
			{
				Bullet item = it.next();

				item.time -= dt;

				if (item.time < 0)
				{
					if (item.level < 4)
					{
						int count = rnd.nextInt(12) + 4;

						for (int i = 0; i < count; i++)
						{
							pool.allocate().setup(rnd.nextFloat() * 2.0f + 1.0f, item.level + 1);
						}
					}
					it.remove();
				}
			}
			//System.out.println(pool.info());
		}
		System.out.println(pool.info());
	}
}
