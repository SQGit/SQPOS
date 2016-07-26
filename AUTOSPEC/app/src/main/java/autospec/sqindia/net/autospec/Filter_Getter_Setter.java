package autospec.sqindia.net.autospec;


public class Filter_Getter_Setter{
        String _ID;
        String _UNIT_NO;
        String _RENTAL_NO;
        String _DATE;

        public Filter_Getter_Setter()
        {
        }


public Filter_Getter_Setter(String _ID,String _UNIT_NO, String _RENTAL_NO, String _DATE) {
        this._ID=_ID;
        this._UNIT_NO = _UNIT_NO;
        this._RENTAL_NO = _RENTAL_NO;
        this._DATE = _DATE;
        }

        //**************getter*************
        public  String get_ID(){return _ID;}
        public String get_UNIT_NO(){return _UNIT_NO;}
        public String get_RENTAL_NO() {return _RENTAL_NO;}
        public String get_DATE() {return _DATE;}

        //**************setter*************
        public void set_ID(String _ID) {this._ID=_ID;}
        public void set_UNIT_NO(String _UNIT_NO) {this._UNIT_NO=_UNIT_NO;}
        public void set_RENTAL_NO(String _RENTAL_NO) {this._RENTAL_NO=_RENTAL_NO;}
        public void set_DATE(String _DATE) {this._DATE=_DATE;}
        }

