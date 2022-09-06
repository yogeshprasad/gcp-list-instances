
package example.smallest.controllers;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.Instance;
import com.google.api.services.compute.model.InstanceList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Command-line sample to demo listing Google Compute Engine instances using Java and the Google
 * Compute Engine API.
 *
 * @author Jonathan Simon
 */
@Controller
public class WelcomeController {
	/** Set PROJECT_ID to your Project ID from the Overview pane in the Developers console. */
	private static final String PROJECT_ID = System.getenv("GCP_PROJECT_ID");

	/** Set Compute Engine zone. */
	private static final String ZONE_NAME = "us-central1-a";

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public @ResponseBody
	String helloWorld() {
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			// Authenticate using Google Application Default Credentials.
			GoogleCredentials credential = GoogleCredentials.getApplicationDefault();
			if (credential.createScopedRequired()) {
				List<String> scopes = new ArrayList<>();
				// Set Google Cloud Storage scope to Full Control.
				scopes.add(ComputeScopes.DEVSTORAGE_FULL_CONTROL);
				// Set Google Compute Engine scope to Read-write.
				scopes.add(ComputeScopes.COMPUTE);
				credential = credential.createScoped(scopes);
			}
			HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);
			// Create Compute Engine object for listing instances.
			Compute compute =
					new Compute.Builder(httpTransport, JSON_FACTORY, requestInitializer)
							.setApplicationName("")
							.build();

			// List out instances, looking for the one created by this sample app.
			printInstances(compute);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "Task Completed!!! Check Logs...";
	}

	// [START list_instances]
	/**
	 * Print available machine instances.
	 *
	 * @param compute The main API access point
	 * @return {@code true} if the instance created by this sample app is in the list
	 */
	public static void printInstances(Compute compute) throws IOException {
		System.out.println("================== Listing Compute Engine Instances ==================");
		Compute.Instances.List instances = compute.instances().list(PROJECT_ID, ZONE_NAME);
		InstanceList list = instances.execute();
		if (list.getItems() == null) {
			System.out.println(
					"No instances found. Sign in to the Google Developers Console and create "
							+ "an instance at: https://console.developers.google.com/");
		} else {
			for (Instance instance : list.getItems()) {
				System.out.println("Instance Name: " + instance.getName());
				System.out.println("Instance Labels: " + instance.getLabels());
				System.out.println("=================================================================================");
			}
		}
	}
	// [END list_instances]
}