package www.rsagroups.example.myapplication;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by RSA on 08-02-2016.
 */
public class Category_search implements SearchSuggestion {
    public String Category_name;
    public String Category_name1;

    @Override
    public String getBody() {
        return Category_name;
    }
public  Category_search(String cn) {
    Category_name=cn;
    Category_name1=cn;
}
    @Override
    public Creator getCreator() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Category_name);
        dest.writeString(Category_name1);
    }
}
