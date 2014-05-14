package com.quicktour.dto;


import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Roman Lukash
 */
public class LoginzaResponse {
    public class Name {
        @JsonProperty("full_name")
        private String fullname;

        public String getFullname() {
            return fullname;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Name{");
            sb.append("fullname='").append(fullname).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }

    @JsonProperty("error_type")
    private String errorType;
    @JsonProperty("error_message")
    private String errorMessage;
    private String identity;
    private String provider;
    private Name name;
    private String nickname;
    private String email;
    private String gender;
    //Date of birth
    private String dob;

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginzaResponse{");
        sb.append("errorType='").append(errorType).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", identity='").append(identity).append('\'');
        sb.append(", provider='").append(provider).append('\'');
        sb.append(", name=").append(name);
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", dob='").append(dob).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
