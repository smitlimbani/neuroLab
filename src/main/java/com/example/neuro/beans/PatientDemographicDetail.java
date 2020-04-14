package com.example.neuro.beans;

import com.example.neuro.utils.SexEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonRootName("PatientDemographicDetail")
public class PatientDemographicDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(unique = true)
    private String UHID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientDemographicDetail", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"patientDemographicDetail", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Master> masters;

//    private String firstName;
//    private String lastName;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    private String emailId;
    private String contactNo;
    private String address;
    private String hospitalName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public void setMasters(List<Master> masters) {
        this.masters = masters;
    }

    public String getUHID() {
        return UHID;
    }

    public void setUHID(String UHID) {
        this.UHID = UHID;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

//    @Override
//    public String toString() {
//        return "PatientDemographicDetail{" +
//                "id=" + id +
//                ", UHID='" + UHID + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", age=" + age +
//                ", sex='" + sex + '\'' +
//                ", emailId='" + emailId + '\'' +
//                ", contactNo='" + contactNo + '\'' +
//                ", hospitalName='" + hospitalName + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "PatientDemographicDetail{" +
                "id=" + id +
                ", UHID='" + UHID + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", emailId='" + emailId + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                '}';
    }

    public PatientDemographicDetail() {
    }
}
