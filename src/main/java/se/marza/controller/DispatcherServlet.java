package se.marza.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.marza.App;
import se.marza.db.TestEntity;
import se.marza.model.Test;

/**
 * Dispatcher servlet.
 */
@WebServlet("*.html")
public class DispatcherServlet extends HttpServlet
{
	/**
	 * Initializes the servlet.
	 *
	 * @throws ServletException
	 */
	@Override
	public void init() throws ServletException
	{
		App.init();
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException
	{
		doProcess(req, res);
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException
	{
		doProcess(req, res);
	}

	protected void doProcess(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException
	{
		final EntityManager em = App.getEntityManager();
		final TypedQuery<TestEntity> query = em.createQuery("SELECT t FROM TestEntity t", TestEntity.class);
		final List<Test> values = new ArrayList<>();
		for (final TestEntity entity : query.getResultList())
		{
			values.add(entity.pojo());
		}

		// Send values to the template.
		req.setAttribute("values", values);

		// Dispatch to the view.
		final RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/view/home.jsp");
		dispatcher.forward(req, res);
	}
}
