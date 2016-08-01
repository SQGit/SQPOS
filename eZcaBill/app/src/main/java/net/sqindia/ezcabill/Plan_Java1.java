package net.sqindia.ezcabill;

/**
 * Created by User on 19-11-2015.
 */
public class Plan_Java1 {
    String _IDTAG;
    String _NAME;
    String _AMOUNT;





    public Plan_Java1(){}


    public Plan_Java1(String id, String type, String amount){

        this._IDTAG= id;
        this._NAME = type;
        this._AMOUNT = amount;


    }

    public String get_IDTAG() {
        return _IDTAG;
    }

    public String get_NAME() {
        return _NAME;
    }

    public String get_AMOUNT() {
        return _AMOUNT;
    }




    public void set_IDTAG(String _IDTAG) {
        this._IDTAG = _IDTAG;
    }

    public void set_NAME(String _NAME) {
        this._NAME = _NAME;
    }

    public void set_AMOUNT(String _AMOUNT) {
        this._AMOUNT = _AMOUNT;
    }


}

