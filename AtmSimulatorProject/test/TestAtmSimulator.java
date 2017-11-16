import junit.framework.TestCase;

public class TestAtmSimulator extends TestCase {

	// only writing for the happy scenario
	

	
	public void testValidDenominationDeposit() {
		Money money = new Money();
		money.depositCash("20 10 50 20");
		assertEquals(100.0, money.getAvailableBalance());
	}

	
	public void testInvalidDenominationDeposit() {
		Money money = new Money();
		money.depositCash("20 10 50 30");
		assertEquals(80.0, money.getAvailableBalance());
	}

	
	public void testWithDraw() {
		Money money = new Money();
		money.depositCash("20 50 10 10");
		System.out.println("Avial-->"+ money.getAvailableBalance());
		money.withdrawCash(20);
		assertEquals(70.0, money.getAvailableBalance());
	}
	
	
	public void testValidAvailableBalance() {
		Money money = new Money();
		money.depositCash("50 10 10");
		money.withdrawCash(50);
		assertEquals(20.0, money.getAvailableBalance());
	}
	
	
	public void testInValidAvailableBalance() {
		Money money = new Money();
		money.depositCash("50 10 10");
		money.withdrawCash(40);
		assertEquals(70.0, money.getAvailableBalance());
	}
	
	public void testMiniStatement() {
		Money money = new Money();
		money.depositCash("50 10 10");		
		assertEquals(70.0, money.getBankStatement().get(0).getAvailableBalance());
	}

}
