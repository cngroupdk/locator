package dk.cngroup.intranet.locator.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * The Person class is used to consume the Timur Web Services API
 *
 * @author Victor Cano
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Login")
    private String login;
    @JsonProperty("Abbreviation")
    private String abbreviation;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Department")
    private String department;
    @JsonProperty("JobCategory")
    private String jobCategory;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("BossId")
    private Integer bossId;

    public Person(){

    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            Person otherPerson = (Person)obj;
            result = id.equals(otherPerson.id) && bossId.equals(otherPerson.bossId)
                    && name.equals(otherPerson.name) && login.equals(otherPerson.login)
                    && email.equals(otherPerson.email)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = id.hashCode()
                * bossId.hashCode()
                * name.hashCode()
                * login.hashCode()
                * email.hashCode();

        return result;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

}
