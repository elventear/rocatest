public class Account {
    private long id;
    private int availableFunds;
    // Getters and setters for the above fields.

    public Account(long id) {
        this.id = id;
        this.availableFunds = 0;
    }

    public Account(long id, int availableFunds) {
        this.id = id;
        this.availableFunds = availableFunds;
    }

    public int getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(int availableFunds) {
        this.availableFunds = availableFunds;
    }

    public static void transferFunds(Account source, Account destination, int amount) {
        if (source.getAvailableFunds() >= amount) {
            source.setAvailableFunds(source.getAvailableFunds() - amount);
            destination.setAvailableFunds(destination.getAvailableFunds() + amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds in source: " + source);
    
        }
    }
}
