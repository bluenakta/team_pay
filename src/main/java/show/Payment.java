package show;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Integer bookId;
    private String status;
    private Integer amount;

    @PreUpdate
    public void onPreUpdate(){
        Payed payed = new Payed();
        BeanUtils.copyProperties(this, payed);
        payed.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){
        Canceled canceled = new Canceled();
        BeanUtils.copyProperties(this, canceled);
        canceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        show.external.TicketIssuance ticketIssuance = new show.external.TicketIssuance();
        // mappings goes here
        Application.applicationContext.getBean(show.external.TicketIssuanceService.class)
            .ticketIssue(ticketIssuance);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }




}
