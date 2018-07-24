package er;
import org.springframework.beans.factory.annotation.Autowired;
import com.siertech.stapi.model.GenericDAO;
import org.springframework.stereotype.Repository;

@Repository
public class erDAO  extends GenericDAO<er> {
	
	

	public erDAO() {
		
		super(er.class);
		
	}
	
	
	
	
}
