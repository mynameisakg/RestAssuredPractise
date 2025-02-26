package Pojo;
/*
1) POJO- Plain old Java object
2) For creating a java file, create a package. For creating a non java
file, create a folder.
3) Pojo class can not extend/implement anything
4) private data fields (variables). ***Remember This***
5) public getters/setters for accessing variables (ENCAPSULATION)
6) public constructor.

 */
public class  Credentials {

    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //getter and setter


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
