package net.sqindia.ezcabill;

/**
 * Created by User on 19-11-2015.
 */
public class Java1 {
    String _IDTAG;
    String _NAME;
    String _MAIL;
    String _NUMBER;
    String _PLAN;
    String _PAYMENT;
    int _GRANDTOTAL;




    public Java1(){}


    public Java1(String id, String name, String mail, String num, String plan, String payment,int g_total){

        this._IDTAG= id;
        this._NAME = name;
        this._MAIL = mail;
        this._NUMBER = num;
        this._PLAN = plan;
        this._PAYMENT = payment;
        this._GRANDTOTAL = g_total;

    }

    public String get_IDTAG() {
        return _IDTAG;
    }

    public String get_NAME() {
        return _NAME;
    }

    public String get_MAIL() {
        return _MAIL;
    }

    public String get_NUMBER() {
        return _NUMBER;
    }

    public String get_PLAN() {
        return _PLAN;
    }

    public String get_PAYMENT() {
        return _PAYMENT;
    }

    public int get_GRANDTOTAL() {
        return _GRANDTOTAL;
    }


    public void set_IDTAG(String _IDTAG) {
        this._IDTAG = _IDTAG;
    }

    public void set_NAME(String _NAME) {
        this._NAME = _NAME;
    }

    public void set_MAIL(String _MAIL) {
        this._MAIL = _MAIL;
    }

    public void set_NUMBER(String _NUMBER) {
        this._NUMBER = _NUMBER;
    }

    public void set_PLAN(String _PLAN) {
        this._PLAN = _PLAN;
    }

    public void set_PAYMENT(String _PAYMENT) {
        this._PAYMENT = _PAYMENT;
    }
    public void set_GRANDTOTAL(int _GRANDTOTAL) {
        this._GRANDTOTAL = _GRANDTOTAL;
    }

}

