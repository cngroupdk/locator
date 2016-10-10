package actors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by cano on 3.10.2016.
 */

@Entity
@DiscriminatorValue("HR")
public class HR extends StaffMemberFactory {

    HR(){

    }

    HR(StaffMember sm){

        this.setId(sm.getId());
        this.setManagerId(sm.getManagerId());
        this.setFirstName(sm.getFirstName());
        this.setLastName(sm.getLastName());
        this.setAbbreviation(sm.getAbbreviation());
        this.setExtension(sm.getExtension());
        this.setVoIP(sm.getVoIP());
        this.setRole(sm.getRole());
        this.setEmail(sm.getEmail());
        this.setLocation(sm.getLocation());
        this.setDetail(sm.getDetail());

    }

}
