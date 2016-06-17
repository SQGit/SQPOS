package net.sqindia.sqpos;

/**
 * Created by Admin on 23-02-2016.
 */
public class Java11 {

    String _KEYID1;
    String _DATE1;
    String _BILLNO1;
    String _CUSTOMER;
    String _TABLE;
    String _ITEMNAME1;
    String _ITEMPRICE1;
    String _QTY1;
    String _TOTAL1;



    public Java11(){}


    public Java11(String id, String date, String billno,String customer,String table, String itemname, String itemprize, String qty, String total){

        this._KEYID1= id;
        this._DATE1= date;
        this._BILLNO1 = billno;
        this._CUSTOMER=customer;
        this._TABLE=table;
        this._ITEMNAME1=itemname;
        this._ITEMPRICE1=itemprize;
        this._QTY1=qty;
        this._TOTAL1=total;

    }


    //getter
    public String get_KEYID1() {
        return _KEYID1;
    }

    public String get_DATE1() {return  _DATE1;}

    public String get_BILLNO1() {return  _BILLNO1;}

    public String get_CUSTOMER() {return _CUSTOMER;}

    public String get_TABLE() {return _TABLE;}


    public String get_ITEMNAME1() {
        return _ITEMNAME1;
    }

    public String get__ITEMPRICE1() {
        return _ITEMPRICE1;
    }

    public String get__QTY1() {
        return _QTY1;
    }

    public String get_TOTAL1() {
        return _TOTAL1;
    }


    //setter
    public void set_KEYID1(String _KEYID1) {
        this._KEYID1 = _KEYID1;
    }

    public void set_DATE1(String _DATE1) {
        this._DATE1 = _DATE1;
    }

    public void set_BILLNO1(String _BILLNO1) {
        this._BILLNO1 = _BILLNO1;
    }

    public void set_CUSTOMER(String _CUSTOMER) { this._CUSTOMER =_CUSTOMER;}

    public void set_TABLE(String _TABLE) {this._TABLE=_TABLE;}

    public void set_ITEMNAME1(String _ITEMNAME1) {
        this._ITEMNAME1 = _ITEMNAME1;
    }

    public void set_ITEMPRICE1(String _ITEMPRICE1) {
        this._ITEMPRICE1 = _ITEMPRICE1;
    }

    public void set_QTY1(String _QTY1) {
        this._QTY1 = _QTY1;
    }

    public void set_TOTAL1(String _TOTAL1) {
        this._TOTAL1 = _TOTAL1;
    }



}

