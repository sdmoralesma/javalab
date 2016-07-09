import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkspaceProcessorResourceIT {

    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:48080/rest/process");

    @Test
    public void shouldGetJavaWorkspace() {
        Response response = provider.addPath("/init/java").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus()).isEqualTo(200);
        JsonObject result = response.readEntity(JsonObject.class);
        assertThat(result.getString("terminal")).isNotNull().contains("Welcome to Javalab");
    }

    @Test
    public void shouldGetScalaWorkspace() {
        Response response = provider.addPath("/init/scala").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus()).isEqualTo(200);
        JsonObject result = response.readEntity(JsonObject.class);
        assertThat(result.getString("terminal")).isNotNull().contains("Welcome to Javalab");
    }

    @Test
    public void shouldGetGroovyWorkspace() {
        Response response = provider.addPath("/init/groovy").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus()).isEqualTo(200);
        JsonObject result = response.readEntity(JsonObject.class);
        assertThat(result.getString("terminal")).isNotNull().contains("Welcome to Javalab");
    }
}
