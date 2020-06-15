package show;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private String status;

    
    @PostPersist
    public void onPostPersist(){
    	
        Payed payed = new Payed();
        BeanUtils.copyProperties(this, payed);
//        payed.setStatus("PAYED"); ??
        payed.publishAfterCommit();       
        
    }

    @PreRemove
    public void onPreRemove(){
    	
//        Canceled canceled = new Canceled();
//        BeanUtils.copyProperties(this, canceled);
//        canceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        show.external.TicketIssuance ticketIssuance = new show.external.TicketIssuance();
        ticketIssuance.setId(this.bookId);
        // mappings goes here
        Application.applicationContext.getBean(show.external.TicketIssuanceService.class)
            .ticketIssue(ticketIssuance);


    }
    
    @PreUpdate
    public void onPreUpdate() {
    	
    	show.external.TicketIssuance ticketIssuance = new show.external.TicketIssuance();
    	ticketIssuance.setId(this.bookId);
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
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Payment [" + (id != null ? "id=" + id + ", " : "") + (bookId != null ? "bookId=" + bookId + ", " : "")
				+ (status != null ? "status=" + status : "") + "]";
	}
}
