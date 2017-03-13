package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

/**
 * Created by junzhuo on 3/12/17.
 */

import java.io.Serializable;
public class User implements Serializable{

    private String username;
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public void setUsername(String username) {
        this.username = username;
    }
}