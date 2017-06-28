package sunmi.com.sunmiauto;

/**
 * Created by Administrator on 2017/6/27.
 */

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    private String username = null;
    private String password = null;

    public MyAuthenticator() {
    };

    public MyAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
