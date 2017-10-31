import com.aries.web.annotation.GET;
import com.aries.web.annotation.Path;

@Path("/test")
public class AWebTest {

	@GET(value = "/good")
	public String g() {
		return "S";
	}
	
}
