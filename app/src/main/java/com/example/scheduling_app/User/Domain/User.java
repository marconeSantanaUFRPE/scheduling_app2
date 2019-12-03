package com.example.scheduling_app.User.Domain;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String dataCriacao;


    public User(){


    }

    public String getDataCriacao(){

        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao){

        this.dataCriacao = dataCriacao;
    }


    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        final User other = (User) obj;
        return this.getUserId() == other.getUserId();
    }

    @Override
    public int hashCode() {
        return this.userId;
    }

}