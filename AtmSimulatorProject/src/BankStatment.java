import java.util.Date;

public final class BankStatment {

	private Date transactionDate;
	private TransactionType transactionType;
	private double amount;
	private double availableBalance;
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	@Override
	public String toString() {
		return "BankStatment [transactionDate=" + transactionDate + ", transactionType=" + transactionType + ", amount="
				+ amount + ", availableBalance=" + availableBalance + "]";
	}
	
	

	
	
}

