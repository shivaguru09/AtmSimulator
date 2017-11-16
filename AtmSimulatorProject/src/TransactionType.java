
public enum TransactionType {

	DEBIT("Debit"),CREDIT("Credit");
	
	private String value;
	
	private TransactionType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

	public String getName() {
		return this.name();
	}
	
	public static TransactionType fromString(String value) {
		for (TransactionType status : values()) {
			if (status.getValue().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException(value
				+ " is not a valid transaction type.");
	}
}
