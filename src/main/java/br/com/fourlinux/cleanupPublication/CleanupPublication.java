package br.com.fourlinux.cleanupPublication;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

@Path("/CleanupPublication")
public class CleanupPublication {
	private RepositoryService repositoryService;
	private SessionProviderService providerservice;
	private static final Log LOG = ExoLogger
			.getLogger(CleanupPublication.class);

	public CleanupPublication(RepositoryService repository,
			SessionProviderService seProviderService) {
		repositoryService = repository;
		providerservice = seProviderService;
	}

	@GET
	@Path("Sitename/{sitename}")
	public String Fixpublication(@PathParam("sitename") String sitename)
			throws RepositoryException {

		System.out
				.println("**********************************************************************************************");
		System.out.println("Searching node tree for sitename: " + sitename);
		System.out
				.println("**********************************************************************************************");

		ManageableRepository repository = repositoryService
				.getCurrentRepository();
		
		Session session = providerservice.getSessionProvider(null).getSession(
				"collaboration", repository);
		
		QueryManager manager = session.getWorkspace().getQueryManager();
		
		String statement = "select * from nt:base where jcr:path LIKE '/sites content/live/"
				+ sitename + "/web contents/site artifacts/%'";
		
		Query query = null;
		
		try {
			query = manager.createQuery(statement.toString(), Query.SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		NodeIterator iter = query.execute().getNodes();

		while (iter.hasNext()) {
			Node node = iter.nextNode();
			if (node.hasProperty("publication:liveRevision")
					&& node.hasProperty("publication:currentState")) {

				node.setProperty("publication:liveRevision", "");
				node.setProperty("publication:currentState", "published");
				session.save();
				LOG.info("\"" + node.getName()
						+ "\" publication lifecycle has been cleaned up");
			}
		}

		return "Cleanup Done";
	}
}
