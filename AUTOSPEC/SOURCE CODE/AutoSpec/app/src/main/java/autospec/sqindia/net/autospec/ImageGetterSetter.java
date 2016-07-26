package autospec.sqindia.net.autospec;

/**
 * Created by Admin on 15-06-2016.
 */
public class ImageGetterSetter {

    String _USERID;
    String _IMAGEID;
    String _IMAGEPATH;


    public ImageGetterSetter()
    {
    }


    public ImageGetterSetter(String _USERID, String _IMAGEID, String _IMAGEPATH) {
        this._USERID = _USERID;
        this._IMAGEID = _IMAGEID;
        this._IMAGEPATH = _IMAGEPATH;
    }

    //***********getter
    public String get_USERID() {
        return _USERID;
    }

    public String get_IMAGEID() {
        return _IMAGEID;
    }

    public String get_IMAGEPATH() {
        return _IMAGEPATH;
    }


    //***********setter
    public String set_USERID(String _USERID) {
        this._USERID = _USERID;
        return _USERID;
    }

    public String set_IMAGEID(String _IMAGEID) {
        this._IMAGEID = _IMAGEID;
        return _IMAGEID;
    }

    public String set_IMAGEPATH(String _IMAGEPATH) {
        this._IMAGEPATH = _IMAGEPATH;
        return _IMAGEPATH;
    }
}


