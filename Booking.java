public class Booking {
    private String bookingID;
    private String studentEmail;
    private String propertyID;
    private boolean isApproved;

    public Booking(String bookingID, String studentEmail, String propertyID) {
        this.bookingID = bookingID;
        this.studentEmail = studentEmail;
        this.propertyID = propertyID;
        this.isApproved = false;
    }

    public String getBookingId() { return bookingID; }
    public String getPropertyID() { return propertyID; }
    public String getStudentEmail() { return studentEmail; }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    @Override
    public String toString() {
        return String.format("Booking ID: %s | Student: %s | Property: %s| Status: %s",
        bookingID, studentEmail, propertyID, isApproved ? "APPROVED" : "PENDING");
    }

}
