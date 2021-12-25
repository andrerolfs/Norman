package de.wartbar.norman.spring.data.persistence;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
