package outra;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siertech.stapi.model.GenericService;

@Service
public class outraService extends GenericService<outra>   {

              @Autowired
	private outraDAO outraDAO;
	
	
}
