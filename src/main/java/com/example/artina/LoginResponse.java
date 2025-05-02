package com.example.artina;

import java.util.List;

public class LoginResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private boolean enabled;
    private List<Authority> authorities;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    // Ajoutez les autres getters et setters pour tous les champs...

    public static class Authority {
        private String authority;

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }
}