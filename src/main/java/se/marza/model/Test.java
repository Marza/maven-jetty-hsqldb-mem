package se.marza.model;

/**
 * Test.
 */
public final class Test
{
	private final String name;
	private final String world;

	/**
	 * Constructor.
	 *
	 * @param name
	 * @param world
	 */
	public Test(final String name, final String world)
	{
		this.name = name;
		this.world = world;
	}

	public String getName()
	{
		return this.name;
	}

	public String getWorld()
	{
		return this.world;
	}
}
