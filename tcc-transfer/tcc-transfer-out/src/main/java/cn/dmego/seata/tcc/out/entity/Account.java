package cn.dmego.seata.tcc.out.entity;

public class Account {

    private String id;

    private String balance;

    private String freezed;

    private String incoming;

    public Account(String id, String balance, String freezed, String incoming) {
        this.id = id;
        this.balance = balance;
        this.freezed = freezed;
        this.incoming = incoming;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFreezed() {
        return freezed;
    }

    public void setFreezed(String freezed) {
        this.freezed = freezed;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }
}
