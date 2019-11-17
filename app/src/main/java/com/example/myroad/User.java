package com.example.myroad;

class User {
        public String name,email,phone;
        public User(){}
        public User(String nam,String email,String phone){
            this.name=nam;
            this.email=email;
            this.phone=phone;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        phone = phone;
    }
}
