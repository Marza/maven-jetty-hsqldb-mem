package se.marza.db;

import se.marza.model.Test;
import javax.persistence.*;

@Entity
@Table(name = "TESTS")
public class TestEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_id")
	private long id;

	@Column(name = "name", length = 64, nullable = false)
	private String name;

	@Column(name = "world", length = 128, nullable = false)
	private String world;

	protected TestEntity() {}

	public TestEntity(final String name, final String world)
	{
		this.name = name;
		this.world = world;
	}

	public final Test pojo()
	{
		return new Test(this.name, this.world);
	}
}
