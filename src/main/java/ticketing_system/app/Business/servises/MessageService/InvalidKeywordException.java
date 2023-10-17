package ticketing_system.app.Business.servises.MessageService;

class InvalidMessageException extends RuntimeException {
    public InvalidMessageException(String message) {
        super(message);
    }
}
