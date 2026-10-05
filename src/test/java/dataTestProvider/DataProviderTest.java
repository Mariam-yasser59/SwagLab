package dataTestProvider;

import org.testng.annotations.DataProvider;

public class DataProviderTest {
    @DataProvider(name = "validCredentials")
    public Object[][] getValidCredentials(){
        return new Object[][] {
                {"standard_user","secret_sauce"},
//                {"visual_user","secret_sauce"},
//                {"error_user","secret_sauce"}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] getInvalidCredentials(){
        return new Object[][] {
                {"locked_out_user","secret_sauce"},
        };
    }

    @DataProvider (name = "getInfo")
    public Object[][] getInfo(){
        return new Object[][] {
                {"Mariam","Yasser","13612"},
                {"Sama","Ali","13612"},
                {"ALI","Ahmed","13612"}
        };
    }
}
