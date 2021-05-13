package com.example.networkrequest;

public class MemberResult {

    public int states;
    public String msg;
    public MemberEntity memberEntity;

    public class MemberEntity{
        public int number_id;
        public String user_name;
        public String pwd;
        public int sex;
        public String email;
        public String phone;
        public long reg_time;
        public long last_time;
        public String memberAddress;
        public String image;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }
}
