package com.viva.vivahomepro;

public class FirebaseVariables {

    private String Image1, CompanyName , City , Authorize, Budget, Contact, EstTime, ProjectDesc, SpecificDetails, Location, proId, userId ;

    private String Title, Body, ProfId;

    private String JobTitle, ClientPhone, ClientEmail, ReqStatus, JobDate;

    public FirebaseVariables(){}

    public FirebaseVariables(String image1, String companyName, String city, String authorize, String budget, String contact, String estTime, String projectDesc, String specificDetails, String location, String proId, String userId, String title, String body, String profId, String jobTitle, String clientPhone, String clientEmail, String reqStatus, String jobDate) {
        Image1 = image1;
        CompanyName = companyName;
        City = city;
        Authorize = authorize;
        Budget = budget;
        Contact = contact;
        EstTime = estTime;
        ProjectDesc = projectDesc;
        SpecificDetails = specificDetails;
        Location = location;
        this.proId = proId;
        this.userId = userId;
        Title = title;
        Body = body;
        ProfId = profId;
        JobTitle = jobTitle;
        ClientPhone = clientPhone;
        ClientEmail = clientEmail;
        ReqStatus = reqStatus;
        JobDate = jobDate;
    }

    public String getImage1() {
        return Image1;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getClientPhone() {
        return ClientPhone;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }

    public String getReqStatus() {
        return ReqStatus;
    }

    public void setReqStatus(String reqStatus) {
        ReqStatus = reqStatus;
    }

    public String getJobDate() {
        return JobDate;
    }

    public void setJobDate(String jobDate) {
        JobDate = jobDate;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCity() {
        return City;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getProfId() {
        return ProfId;
    }

    public void setProfId(String profId) {
        ProfId = profId;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAuthorize() {
        return Authorize;
    }

    public void setAuthorize(String authorize) {
        Authorize = authorize;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEstTime() {
        return EstTime;
    }

    public void setEstTime(String estTime) {
        EstTime = estTime;
    }

    public String getProjectDesc() {
        return ProjectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        ProjectDesc = projectDesc;
    }

    public String getSpecificDetails() {
        return SpecificDetails;
    }

    public void setSpecificDetails(String specificDetails) {
        SpecificDetails = specificDetails;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
