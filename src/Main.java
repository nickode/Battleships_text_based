
public class Main {

	public static int turnCount = 1, currentTurn = 0;
	
	public static void main(String[] args) {
		
		
		Board bsBoard = new Board();
		
		bsBoard.placeShips();
		bsBoard.placeGrenades();
		bsBoard.placeEnemyShips();
		bsBoard.placeEnemyGrenades();
		System.out.println("-------------------------------------------");
		bsBoard.updateBoard();
		
		
		do{
			if(currentTurn == 1){
				
				bsBoard.enemyShootRocket();
				currentTurn--;
			}else if (currentTurn == 0){
				
				bsBoard.shootRocket();
				currentTurn++;
			}
		}while(bsBoard.allyCount < 6 && bsBoard.enemyCount < 6);
		
		if(bsBoard.allyCount == 6){
			System.out.println("Congratulations, you've won!");
		}else
			System.out.println("You lose. Better luck next time.");
		
		bsBoard.showBoard();
		
		
		

	}

}
