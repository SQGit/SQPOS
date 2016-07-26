package autospec.sqindia.net.autospec;

/**
 * Created by Admin on 16-06-2016.
 */
public class SignatureGetterSetter{
    String _USERID;
    String _SIGNATUREPATH;

        public SignatureGetterSetter()
        {
        }


                public SignatureGetterSetter(String _USERID,String _SIGNATUREPATH) {
                this._USERID = _USERID;
                this._SIGNATUREPATH = _SIGNATUREPATH;
                }


        //getter
        public String get_USERID() {
                return _USERID;
                }

        public String get_SIGNATUREPATH() {
                return _SIGNATUREPATH;
                }



        //setter
        public String set_USERID(String _USERID) {
                this._USERID = _USERID;
                return _USERID;
                }

        public String set_SIGNATUREPATH(String _SIGNATUREPATH) {
                this._SIGNATUREPATH = _SIGNATUREPATH;
                return _SIGNATUREPATH;
                }
        }
