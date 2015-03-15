package ru.hh.data;

/**
 * Created by HonneyHelen on 15-Mar-15.
 */
public class HHUser {
    private String last_name;
    private String resumes_url;
    private Boolean is_admin = false;
    private Boolean is_employer = false;
    private Object personal_manager;
    private String id;
    private String first_name;
    private String middle_name;
    private Boolean is_in_search = false;
    private Boolean is_anonymous = false;
    private String negotiations_url;
    private Boolean is_applicant = false;
    private String email;
    private Counters counters;
    private String password;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getResumes_url() {
        return resumes_url;
    }

    public void setResumes_url(String resumes_url) {
        this.resumes_url = resumes_url;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public Object getPersonal_manager() {
        return personal_manager;
    }

    public void setPersonal_manager(Object personal_manager) {
        this.personal_manager = personal_manager;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public boolean isIs_in_search() {
        return is_in_search;
    }

    public void setIs_in_search(boolean is_in_search) {
        this.is_in_search = is_in_search;
    }

    public boolean isIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public String getNegotiations_url() {
        return negotiations_url;
    }

    public void setNegotiations_url(String negotiations_url) {
        this.negotiations_url = negotiations_url;
    }

    public boolean isIs_applicant() {
        return is_applicant;
    }

    public void setIs_applicant(boolean is_applicant) {
        this.is_applicant = is_applicant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Counters getCounters() {
        return counters;
    }

    public void setCounters(Counters counters) {
        this.counters = counters;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_employer() {
        return is_employer;
    }

    public void setIs_employer(boolean is_employer) {
        this.is_employer = is_employer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HHUser hhUser = (HHUser) o;

        if (!email.equals(hhUser.email)) return false;
        if (!first_name.equals(hhUser.first_name)) return false;
        if (!is_admin.equals(hhUser.is_admin)) return false;
        if (!is_anonymous.equals(hhUser.is_anonymous)) return false;
        if (!is_applicant.equals(hhUser.is_applicant)) return false;
        if (!is_employer.equals(hhUser.is_employer)) return false;
        if (!is_in_search.equals(hhUser.is_in_search)) return false;
        if (!last_name.equals(hhUser.last_name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = last_name.hashCode();
        result = 31 * result + is_admin.hashCode();
        result = 31 * result + is_employer.hashCode();
        result = 31 * result + first_name.hashCode();
        result = 31 * result + is_in_search.hashCode();
        result = 31 * result + is_anonymous.hashCode();
        result = 31 * result + is_applicant.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
