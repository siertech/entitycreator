package outra;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.siertech.stapi.crud.CrudClass;

@Entity
@Table(name = "outra")
@Getter @Setter
public  class outra extends CrudClass {

	

}
