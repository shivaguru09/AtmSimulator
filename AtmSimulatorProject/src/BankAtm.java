import java.util.Scanner;

public class BankAtm {

	private Money money = null;

	public BankAtm() {
		money = new Money();
	}

	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		new BankAtm().savingsAccount();
	}

	public void savingsAccount() {
		int selection;

		System.out.println("\nATM simulator:");
		System.out.println("1.Deposit");
		System.out.println("2.Withdraw");
		System.out.println("3.Display Balance");
		System.out.println("4.Mini Statement");
		System.out.println("5.Exit");
		System.out.print("Select an option:");
		selection = input.nextInt();

		switch (selection) {
		case 1: {
			System.out.println("Enter ccy to deposit terminated by. e.g. 10 20 50 .");
			final String depositAmount = new Scanner(System.in).nextLine();
			deposit(depositAmount);
			break;
		}
		case 2: {
			System.out.println("Enter amount to withdraw");
			try {
				final int withDrawAmount = new Scanner(System.in).nextInt();
				if (withDrawAmount % 10 != 0) {
					System.out.println("Enter the amount multiples of 10");
					savingsAccount();
				}
				withdraw(withDrawAmount);
			} catch (java.util.InputMismatchException | NumberFormatException nfe) {
				System.out.println("Invalid Amount,enter the multiples of 10");
				savingsAccount();
			}
			
			break;
		}
		case 3:
			displayBalance();
			break;
		case 4:
			displayMiniStatement();
			break;
		case 5:
			System.out.println("Have a good day");
			break;
		default:
			System.out.println("Invalid choice.");
			savingsAccount();

		}
	}

	/**
	 * deposit cash
	 * 
	 * @param depositAmount
	 */
	public void deposit(final String depositAmount) {


		money.depositCash(depositAmount);

		savingsAccount();

	}

	/**
	 * withDraw
	 */
	public void withdraw(final int withDrawAmount) {
		money.withdrawCash(withDrawAmount);
		savingsAccount();
	}

	/**
	 * Display balance
	 */
	public void displayBalance() {
		System.out.println("Available balance:" + money.getAvailableBalance());
		savingsAccount();
	}

	/**
	 * Display Ministatment
	 */
	private void displayMiniStatement() {
		money.displayMiniStatement();
		savingsAccount();

	}

}