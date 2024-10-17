public class Admin {
    private int id;  
    private String name;
    private String username;
    private String travelsname;
    private long mobile;
    private String email;
    private String password;
    public Admin(String name, String username,String travelsname, String email,long mobile, String password) {
        this.name = name;
        this.username = username;
        this.travelsname=travelsname;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        
    }
   public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public long getMobile() {
        return this.mobile;
    }

    public String getMail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTravels(){
    	return this.travelsname;
    }
    
    public String toString() {
        return "----------------------------------\n" +
               "Id         : " + id + "\n" +
               "Name       : " + name + "\n" +
               "Mail       : " + email + "\n" +
               "Mobile     : " + mobile + "\n" +
               "Mobile     : " + travelsname + "\n" +
               "-----------------------------------\n";
    }
}
