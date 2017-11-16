import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Money {

	private final Map<Integer, Integer> avialbaleDenominations;
	
	private List<BankStatment> statementList = null;	
	
	private static int[] CURRENT_DENOMINATIONS = { 50, 20, 10 };
	
	private static int[] COUNT = {0,0,0};

	public Money() {
		avialbaleDenominations = new HashMap<>();
		statementList = new ArrayList<>();
	}

	public static int[] getCurrentDenominations() {
		return CURRENT_DENOMINATIONS;
	}
	
	public synchronized Map<Integer, Integer> getAvialbaleDenominations() {
		return avialbaleDenominations;
	}

	public  double getClosingBalance() {
		return calcTotalCorpus();
	}
	
	public  double getAvailableBalance() {
		return calcTotalCorpus();
	}
	
	public  List<BankStatment> getBankStatement() {
		return statementList;
	}


	public int calcTotalCorpus() {
		int totalCorpus = 0;
		if (!avialbaleDenominations.isEmpty()) {
			for (Entry<Integer, Integer> entry : avialbaleDenominations.entrySet()) {
				totalCorpus += entry.getKey() * entry.getValue();
			}
		}
		return totalCorpus;
	}
	
	
	/**
	 * Cash Deposit
	 * @param depositAmount
	 */
	public synchronized void depositCash(final String depositAmount){
		
		double creditedAmount = 0;
		
		if (depositAmount != null && !depositAmount.trim().isEmpty()) {

			final String[] result = depositAmount.trim().split("\\s");
			
			for (int x = 0; x < result.length; x++) {
				try {
					int notes = Integer.valueOf(result[x]);
					/*if (notes % 10 != 0) {
						System.out.println("Enter amount the multiples of 10");
					}*/
					if (isValidDenom(notes)) {						
						if(!avialbaleDenominations.containsKey(notes)){
							avialbaleDenominations.put(notes, 1);
						}else{
							avialbaleDenominations.put(notes, avialbaleDenominations.get(notes)+1);
						}
						
						creditedAmount+=notes;
						
						System.out.printf("Accepted:%d\n", notes);
					} else {
						System.out.printf("Invalid denomination:%d$\n", notes);
					}			
					

				} catch (NumberFormatException nfe) {
					System.out.printf("Invalid denomination:%s$\n",result[x]);
				}

			}
			
			if(creditedAmount>0){
				creditStatement(creditedAmount);
			}

		}
		
	}

	
	/**
	 * 
	 * @param creditedAmount
	 */
	private void creditStatement(final double creditedAmount){		
		setStatement(TransactionType.CREDIT,creditedAmount);
	}
	
	
	/**
	 * 
	 * @param debitedAmount
	 */
	private void debitStatement(final double debitedAmount){		
		setStatement(TransactionType.DEBIT,debitedAmount);
	}
	/**
	 * 
	 * @param amount
	 */
	private void setStatement(final TransactionType transactionType, final double amount) {
		final BankStatment creditStatement = new BankStatment();
		creditStatement.setTransactionDate(new Date());
		creditStatement.setTransactionType(transactionType);
		creditStatement.setAvailableBalance(getAvailableBalance());
		creditStatement.setAmount(amount);					
		statementList.add(creditStatement);
	}
	
	/**
	 * 
	 * Cash Withdraw
	 * @param amount
	 */
	 public  synchronized  void  withdrawCash(final int amount){	
		 if(isValidDenom(amount)){	
		 double debitedAmount = 0.0;
		 int totalCorpus = (int)getAvailableBalance();
		 int withdrawAmount = amount;
	        if(withdrawAmount<=totalCorpus){	        	
	            for (int i = 0; i < CURRENT_DENOMINATIONS.length; i++) {
	                if (CURRENT_DENOMINATIONS[i] <= withdrawAmount) {
	                	//If the withdrawAmount is less than the current denomination then that particular denomination cannot be dispensed
	                    int noteCount = withdrawAmount / CURRENT_DENOMINATIONS[i];
	                    int currNo = avialbaleDenominations.get(CURRENT_DENOMINATIONS[i]);
	                    if(currNo>0){
	                    	//To check whether the ATM Vault is left with the currency denomination under iteration
	                        //If the Note COUNT is greater than the number of notes in ATM vault for that particular denomination then utilize all of them 
	                    	COUNT[i] = noteCount>=currNo?currNo:noteCount;
	                        currNo =  noteCount>=currNo?0:currNo-noteCount;
	                        //Deduct the total amount left in the ATM Vault with the cash being dispensed in this iteration
	                        totalCorpus=totalCorpus-(COUNT[i]*CURRENT_DENOMINATIONS[i]);
	                        //Calculate the withdrawAmount that need to be addressed in the next iterations
	                        withdrawAmount = withdrawAmount -(COUNT[i]*CURRENT_DENOMINATIONS[i]);
	                        avialbaleDenominations.put(CURRENT_DENOMINATIONS[i], 
	    	            			(avialbaleDenominations.get(CURRENT_DENOMINATIONS[i])-COUNT[i]));
	                        debitedAmount += (CURRENT_DENOMINATIONS[i] * COUNT[i]);
	                    }                
	                }
	            }
	           displayNotes();
	           //displayLeftNotes();
	         //Add it in bank statement
		       debitStatement(debitedAmount);

	        }
	        else{
	            System.out.println("Insufficent Fund");
	        }
		 }else{
			 System.out.println("invalid Denominations eg. 50 ,20, 10");
		 }
	    }
	 
	 /**
	  * Display the dispensing cash
	  */
	 private void displayNotes(){		 
	        for (int i = 0; i < COUNT.length; i++) {
	            if (COUNT[i] != 0) {
	                System.out.printf("Dispensing %d$\n", (CURRENT_DENOMINATIONS[i] * COUNT[i]));
	            }
	        }        
	        
	    }
	 
	 /**
	  * Display the left notes
	  */
	 
	   @SuppressWarnings("unused")
	private void displayLeftNotes(){	      
		   if (!avialbaleDenominations.isEmpty()) {
				for (Entry<Integer, Integer> entry : avialbaleDenominations.entrySet()) {
					System.out.println("Notes of "+entry.getKey()+" left are "+entry.getValue());					
				}
			}

	    } 
	   
	   /**
		 * Check the deposited amount is valid denominations or not
		 * @param depositAmt
		 * @return true - valid denomination
		 */
		private boolean isValidDenom(int depositAmt) {
			boolean isValid = false;
			for (int currDenom : CURRENT_DENOMINATIONS) {			
				if (depositAmt == currDenom) {
					isValid = true;
					break;
				}
			}
			return isValid;
		}
	 
	
	/**
	 * 	Displays the ministatment
	 */
	 public void displayMiniStatement(){	      
		   
		 final StringBuffer buffer = new StringBuffer();
		 buffer.append("-------------------------------------------------------------------\n");
		 buffer.append("Date Time    Transaction   	  Amount      Closing Balance               \n");
		 buffer.append("-------------------------------------------------------------------\n");
		 if(statementList!=null && !statementList.isEmpty()){
			 for (BankStatment  miniStatement: statementList) {			
				 buffer.append(formatDate(miniStatement.getTransactionDate())+"   "+
				 miniStatement.getTransactionType().getValue() +"       "+
			     miniStatement.getAmount()+"    "+
				 miniStatement.getAvailableBalance()+"               \n");
			}
			 
		 }

		 buffer.append("-------------------------------------------------------------------\n");
		 System.out.println(buffer);
	    } 
	 
	 
	 /**
	  * 
	  * @param date
	  * @return
	  */
	 private String formatDate(final Date date){
		 
		 final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		 
		 return format.format(date.getTime());
	 }
    
	

}
