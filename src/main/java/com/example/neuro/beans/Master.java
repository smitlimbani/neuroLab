package com.example.neuro.beans;

import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.SampleTypeEnum;
import com.example.neuro.utils.StatusEnum;
import com.example.neuro.utils.TestStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private ExternalSample externalSample;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Payment> payments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"master", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Vial> vials;

    private String ULID;
    private String nNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum ANCA = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum MOG = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum NMDA = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum ANA = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum PARA = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum MYO = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum GANGIGG = TestStatusEnum.NOT_RAISED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatusEnum GANGIGM = TestStatusEnum.NOT_RAISED;

    private Double totalAmount;
    private Double remainingAmount;

    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IsValidEnum isValid = IsValidEnum.Y;

    private String remark="ok";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status = StatusEnum.NOT_RECEIVED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SampleTypeEnum sampleType;

    private String linked = "0";
    private String drName;
    private String drEmailId;
    private String drContactNo;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "Asia/Kolkata")
    private Date reqDate = Date.valueOf(LocalDate.now());

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public ExternalSample getExternalSample() {
        return externalSample;
    }

    public void setExternalSample(ExternalSample externalSample) {
        this.externalSample = externalSample;
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

    public TestStatusEnum getANCA() {
        return ANCA;
    }

    public void setANCA(TestStatusEnum ANCA) {
        this.ANCA = ANCA;
    }

    public TestStatusEnum getMOG() {
        return MOG;
    }

    public void setMOG(TestStatusEnum MOG) {
        this.MOG = MOG;
    }

    public TestStatusEnum getNMDA() {
        return NMDA;
    }

    public void setNMDA(TestStatusEnum NMDA) {
        this.NMDA = NMDA;
    }

    public TestStatusEnum getANA() {
        return ANA;
    }

    public void setANA(TestStatusEnum ANA) {
        this.ANA = ANA;
    }

    public TestStatusEnum getPARA() {
        return PARA;
    }

    public void setPARA(TestStatusEnum PARA) {
        this.PARA = PARA;
    }

    public TestStatusEnum getMYO() {
        return MYO;
    }

    public void setMYO(TestStatusEnum MYO) {
        this.MYO = MYO;
    }

    public TestStatusEnum getGANGIGG() {
        return GANGIGG;
    }

    public void setGANGIGG(TestStatusEnum GANGIGG) {
        this.GANGIGG = GANGIGG;
    }

    public TestStatusEnum getGANGIGM() {
        return GANGIGM;
    }

    public void setGANGIGM(TestStatusEnum GANGIGM) {
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

    public String getDrEmailId() {
        return drEmailId;
    }

    public void setDrEmailId(String drEmailId) {
        this.drEmailId = drEmailId;
    }

    public String getDrContactNo() {
        return drContactNo;
    }

    public void setDrContactNo(String drContactNo) {
        this.drContactNo = drContactNo;
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
                ", PARA=" + PARA +
                ", MYO=" + MYO +
                ", GANGIGG=" + GANGIGG +
                ", GANGIGM=" + GANGIGM +
                ", totalAmount=" + totalAmount +
                ", remainingAmount=" + remainingAmount +
                ", isActive=" + isActive +
                ", isValid=" + isValid +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", sampleType=" + sampleType +
                ", linked='" + linked + '\'' +
                ", drName='" + drName + '\'' +
                ", drEmailId='" + drEmailId + '\'' +
                ", drContactNo='" + drContactNo + '\'' +
                ", reqDate=" + reqDate +
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
