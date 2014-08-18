import js.data.ObjectPoolBase.ObjectFactory;

public class Bullet
{
	public float time;
	public int level = 0;

	public void setup(float time, int level)
	{
		this.time = time;
		this.level = level;
	}

	public static ObjectFactory<Bullet> factory = new ObjectFactory<Bullet>()
	{
		@Override
		public Bullet create()
		{
			return new Bullet();
		}
	};

}
