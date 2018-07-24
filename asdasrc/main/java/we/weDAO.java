package we;
import org.springframework.beans.factory.annotation.Autowired;
import com.siertech.stapi.model.GenericDAO;
import org.springframework.stereotype.Repository;

@Repository
public class weDAO  extends GenericDAO<we> {
	
	

	public weDAO() {
		
		super(we.class);
		
	}
	
	
	
	
}
