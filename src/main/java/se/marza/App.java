package se.marza;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.hsqldb.jdbc.JDBCDataSource;
import se.marza.db.TestEntity;

/**
 * This class is responsible for starting up the servers and doing the required initialization.
 */
public class App
{
	private static final String PERSISTENCE_UNIT_NAME = "persistenceUnit";
	private static EntityManager em = null;

	/**
	 * Does the required initialization for the application.
	 */
	public static synchronized void init()
	{
		if (em == null)
		{
			em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
			initDB();
		}
	}

	/**
	 * Returns the entity manager.
	 *
	 * @return the entity manager.
	 */
	public static EntityManager getEntityManager()
	{
		return em;
	}

	/**
	 * Starts the DB and Jetty server.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception
	{
		final String warFilePath = args[0];

		// Create embedded HSQLDB server.
		final org.hsqldb.Server hsqlServer = new org.hsqldb.Server();

		// HSQLDB prints out a lot of information when
		// starting and closing, which we don't need now.
		// Normally you should point the setLogWriter
		// to some Writer object that could store the logs.
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);

		// The actual database will be named 'xdb' and its
		// settings and data will be stored in files
		// testdb.properties and testdb.script
		hsqlServer.setDatabaseName(0, "xdb");
		//hsqlServer.setDatabasePath(0, "file:testdb");
		hsqlServer.setDatabasePath(0, "mem:testdb");

		// Start the database!
		hsqlServer.start();

		// Create embedded Jetty server.
		final Server server = new Server(8080);

		// Enable parsing of jndi-related parts of web.xml and jetty-env.xml
		final org.eclipse.jetty.webapp.Configuration.ClassList classList = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
		classList.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
		classList.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

		final WebAppContext webApp = new WebAppContext();
		webApp.setWar(warFilePath);

		final HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { webApp, new DefaultHandler() });
		server.setHandler(handlers);

		// Register new transaction manager in JNDI
		// At runtime, the webapp accesses this as java:comp/UserTransaction
		//org.eclipse.jetty.plus.jndi.Transaction transactionMgr = new org.eclipse.jetty.plus.jndi.Transaction(new com.acme.MockUserTransaction());

		// Register a DataSource scoped to the webapp.
		org.eclipse.jetty.plus.jndi.Resource mydatasource = new org.eclipse.jetty.plus.jndi.Resource(webApp, "jdbc/mydatasource", new JDBCDataSource());

		server.start();
		System.out.println("Application started.");
		System.out.println("Press CTRL+C to shutdown.");
		server.join();
	}

	private static void initDB()
	{
		em.getTransaction().begin();
		em.persist(new TestEntity("Hello", "world"));
		em.persist(new TestEntity("Hej", "världen"));
		em.flush();
		em.getTransaction().commit();
	}
}
