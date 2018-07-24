package outra;
import org.springframework.beans.factory.annotation.Autowired;
import com.siertech.stapi.model.GenericDAO;
import org.springframework.stereotype.Repository;

@Repository
public class outraDAO  extends GenericDAO<outra> {
	
	

	public outraDAO() {
		
		super(outra.class);
		
	}
	
	
	
	
}
