package com.example.neuro.beans;

import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.SampleTypeEnum;
import com.example.neuro.utils.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@JsonRootName("Master")
public class Master implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patientDemographicDetailId")
    @JsonIgnoreProperties(value = {"masters", "hibernateLazyInitializer"}, allowSetters = true)
    private PatientDemographicDetail patientDemographicDetail;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentCategoryId")
    @JsonIgnoreProperties(value = {"masters", "hibernateLazyInitializer"}, allowSetters = true)
    private PaymentCategory paymentCategory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Sample> samples;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private List<GeneratedSample> generatedSamples;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Payment> payments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Vial> vials;

    @Column(unique = true)
    private String ULID;
    private String nNo;

    private Integer ANCA = 0;
    private Integer MOG = 0;
    private Integer NMDA = 0;
    private Integer ANA = 0;
    private Integer PANA = 0;
    private Integer MYU = 0;
    private Integer GANGIGG = 0;
    private Integer GANGIGM = 0;

    private Double totalAmount;
    private Double remainingAmount;

    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IsValidEnum isValid = IsValidEnum.Y;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status = StatusEnum.NOT_RECEIVED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SampleTypeEnum sampleType;

    private String linked = "0";
    private String drName;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date reqDate = new Date();

    public List<Vial> getVials() {
        return vials;
    }

    public void setVials(List<Vial> vials) {
        this.vials = vials;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public List<GeneratedSample> getGeneratedSamples() {
        return generatedSamples;
    }

    public void setGeneratedSamples(List<GeneratedSample> generatedSamples) {
        this.generatedSamples = generatedSamples;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientDemographicDetail getPatientDemographicDetail() {
        return patientDemographicDetail;
    }

    public void setPatientDemographicDetail(PatientDemographicDetail patientDemographicDetail) {
        this.patientDemographicDetail = patientDemographicDetail;
    }

    public String getULID() {
        return ULID;
    }

    public void setULID(String ULID) {
        this.ULID = ULID;
    }

    public String getnNo() {
        return nNo;
    }

    public void setnNo(String nNo) {
        this.nNo = nNo;
    }

    public Integer getANCA() {
        return ANCA;
    }

    public void setANCA(Integer ANCA) {
        this.ANCA = ANCA;
    }

    public Integer getMOG() {
        return MOG;
    }

    public void setMOG(Integer MOG) {
        this.MOG = MOG;
    }

    public Integer getNMDA() {
        return NMDA;
    }

    public void setNMDA(Integer NMDA) {
        this.NMDA = NMDA;
    }

    public Integer getANA() {
        return ANA;
    }

    public void setANA(Integer ANA) {
        this.ANA = ANA;
    }

    public Integer getPANA() {
        return PANA;
    }

    public void setPANA(Integer PANA) {
        this.PANA = PANA;
    }

    public Integer getMYU() {
        return MYU;
    }

    public void setMYU(Integer MYU) {
        this.MYU = MYU;
    }

    public Integer getGANGIGG() {
        return GANGIGG;
    }

    public void setGANGIGG(Integer GANGIGG) {
        this.GANGIGG = GANGIGG;
    }

    public Integer getGANGIGM() {
        return GANGIGM;
    }

    public void setGANGIGM(Integer GANGIGM) {
        this.GANGIGM = GANGIGM;
    }

    public PaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public IsValidEnum getIsValid() {
        return isValid;
    }

    public void setIsValid(IsValidEnum isValid) {
        this.isValid = isValid;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public SampleTypeEnum getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleTypeEnum sampleType) {
        this.sampleType = sampleType;
    }

    public String getLinked() {
        return linked;
    }

    public void setLinked(String linked) {
        this.linked = linked;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", patientDemographicDetail=" + patientDemographicDetail +
                ", paymentCategory=" + paymentCategory +
                ", ULID='" + ULID + '\'' +
                ", nNo='" + nNo + '\'' +
                ", ANCA=" + ANCA +
                ", MOG=" + MOG +
                ", NMDA=" + NMDA +
                ", ANA=" + ANA +
                ", PANA=" + PANA +
                ", MYU=" + MYU +
                ", GANGIGG=" + GANGIGG +
                ", GANGIGM=" + GANGIGM +
                ", totalAmount=" + totalAmount +
                ", remainingAmount=" + remainingAmount +
                ", isValid=" + isValid +
                ", status=" + status +
                ", sampleType='" + sampleType + '\'' +
                ", linked='" + linked + '\'' +
                ", drName='" + drName + '\'' +
                ", reqDate=" + reqDate +
                ", isActive=" + isActive +
                '}';
    }

    public Master() {
    }

    @Override
    public int compareTo(Object B) {
        String a= this.getPatientDemographicDetail().getUHID();
        String b= ((Master)B).getPatientDemographicDetail().getUHID();
        return a.compareTo(b);
    }
}
