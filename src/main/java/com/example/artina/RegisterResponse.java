package com.example.artina;

import java.util.List;

public class RegisterResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String motDePasse;
    private String password;
    private boolean enabled;
    private String username;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private List<Authority> authorities;

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTel() { return tel; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public String getPassword() { return password; }
    public boolean isEnabled() { return enabled; }
    public String getUsername() { return username; }
    public boolean isAccountNonExpired() { return accountNonExpired; }
    public boolean isCredentialsNonExpired() { return credentialsNonExpired; }
    public boolean isAccountNonLocked() { return accountNonLocked; }
    public List<Authority> getAuthorities() { return authorities; }

    public static class Authority {
        private String authority;

        public String getAuthority() {
            return authority;
        }
    }
}
