package www.rsagroups.example.myapplication;

/**
 * Created by User on 19-11-2015.
 */
public class Java1 {

    String _DATE;
    String _BILLNO;
    String _CUSTOMERNAME;
    String _TABLE;
    String _GRANDTOTAL;



    public Java1(){}


    public Java1(String id, String date, String billno, String c_name,String tableno,String gtotal){


        this._DATE= date;
        this._BILLNO = billno;
        this._CUSTOMERNAME = c_name;
        this._TABLE = tableno;
        this._GRANDTOTAL = gtotal;

    }



    public String get_DATE() {return  _DATE;}


    public String get_BILLNO() {
        return _BILLNO;
    }


    public String get_CUSTOMERNAME() {
        return _CUSTOMERNAME;
    }

    public String get_TABLE() {
        return _TABLE;
    }

    public String get_GRANDTOTAL() {
        return _GRANDTOTAL;
    }





    public void set_DATE(String _DATE) {
        this._DATE = _DATE;
    }

    public void set_BILLNO(String _BILLNO) {
        this._BILLNO = _BILLNO;
    }

   public void set_CUSTOMERNAME(String _CUSTOMERNAME) { this._CUSTOMERNAME = _CUSTOMERNAME;}

    public void set_TABLE(String _TABLE) { this._TABLE = _TABLE;}

    public void set_GRANDTOTAL(String _GRANDTOTAL) {
        this._GRANDTOTAL = _GRANDTOTAL;
    }



}

