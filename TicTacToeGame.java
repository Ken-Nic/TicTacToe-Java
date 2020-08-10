import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = -1;
			}
		}
		launch(args);
	}
	
	private static int [][] board = new int[3][3];
	private int player_Turn = 0; //This will determine if its player one (O) or player two (x)
	private int player1_O = 1; //number of overrides for player
	private int player2_O = 1;
	private int move_Counter = 1; //This is a move counter, as it will not be possible for a 3 in a row in one move why waste the time checking for a winner?
	private boolean override = false;
	BorderPane borderPane;
	
	//Will update board with values
	private void updateBoard(int x, int y,int z){
		board[x][y] = z;
	}
	
	//Will check if there is a empty spot on the board
	private boolean checkBoard() {
		boolean isFull = true;
		for(int i = 0; i <3;i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == -1) {
					isFull = false;
					break;
				}
			}
		}
		return isFull;
	}
	
	//Will refresh the boards array
	private void resetBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = -1;
			}
		}
	}
	
	//Will change the current player back and forth
	private void changePlayer() {
		if(player_Turn == 0) {
			player_Turn++;
		} else {
			player_Turn--;
		}
	}
	
	//Method to see if a winning combination is on the board by the specified player
	private boolean checkWinner(int player) {
		
		//Checking if any of rows are filled by the player
		for(int i = 0; i <3; i++) {
			if(board[i][0] == player && board[i][1] == player && board[i][2] == player) { //Row check
				return true;
			}
		}
		
		//Checking if  any verticals are by current player
		for(int i = 0; i <3; i++) {
				if(board[0][i] == player && board[1][i] == player && board[2][i] == player) {
					return true;
				}
		}
		
		//Checking if diagonal columns are filled
		if((board[0][0] == player && board[1][1] == player && board[2][2] == player) || (board[2][0] == player && board[1][1] == player && board[0][2] == player)) {
			return true;
			}	
		return false;
	}

	//Method to enact a players turn
	private void playerAction(int player_ID,boolean override,Event e,int row,int col) {
		if(player_Turn == 0) {
			
			//Placing a X on the board
			
			((Button)(e.getSource())).setText("X");
			((Button)(e.getSource())).setStyle("-fx-font-size: 2em;-fx-text-fill: #ffffff;-fx-border-color:#000000;-fx-border-width: 2px;-fx-background-color: #FF0000");
			
			//Updating the board data to reflect the change
			updateBoard(col,row,player_Turn);
			
			//Checking for winner
			if(checkWinner(player_Turn)) {
				StackPane winner = new StackPane();
				Label display = new Label("Player1 wins (X)");
				winner.getChildren().add(display);
				borderPane.setCenter(winner);
			
			
			} else if(move_Counter >= 9) {
				
				//Checking if the board is full, and if it is then we say there is no winner 
				if(checkBoard()) {
					StackPane winner = new StackPane();
					Label display = new Label("No winner");
					winner.getChildren().add(display);
					borderPane.setCenter(winner);
				}
			}
			move_Counter++;
			changePlayer();
			override = false;
		} else {
			((Button)(e.getSource())).setText("O");
			((Button)(e.getSource())).setStyle("-fx-font-size: 2em;-fx-text-fill: #ffffff;-fx-border-color:#000000;-fx-border-width: 2px;-fx-background-color: #008000");
			updateBoard(col,row,player_Turn);
			
			if(checkWinner(player_Turn)) {
				
				//Result Screen
				StackPane winner = new StackPane();
				Label display = new Label("Player2 wins(O)");
				winner.getChildren().add(display);
				borderPane.setCenter(winner);
			
			} else if(move_Counter >= 9) {
				
				//Checking if the board is full, and if it is then we say there is no winner 
				if(checkBoard()) {
					StackPane winner = new StackPane();
					Label display = new Label("No winner");
					winner.getChildren().add(display);
					borderPane.setCenter(winner);
				}
			}
			move_Counter++;
			changePlayer();
			override = false;
		}
		
	}
	@Override
	public void start(Stage mainWindow) throws Exception {
		 //Naming main window
		 mainWindow.setTitle("Tic-Tac-Toe Game");
		
		 //Borderpane
		  borderPane = new BorderPane();
		
		 
		 //Setting up top of borderPane to house the  "Quick Game" and "Custom game" Button
		 //First Creating a box to hold the buttons in
		 HBox gameButtons = new HBox();
		 
		 //Making the quickGame button
		 Button quickGame = new Button("Quick Game");
		 
		 quickGame.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				
				//Some safety measures to make sure no old data or previous games mess up the new game
				player_Turn = 0;
				move_Counter = 1;
				override = false;
				resetBoard();
				
				//Now making a gridPane to house the buttons
				GridPane gameBoard = new GridPane();
				
				//Creating buttons for the gameBoard and then populating board with these buttons
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						Button btn = new Button();
						btn.setStyle("-fx-border-color:#000000; -fx-border-width: 2px");
						btn.setPrefSize(250,250);
						btn.setText(" ");
						btn.setOnAction(new EventHandler() {
							@Override
							public void handle(Event e) {
								//Variables
								int row = GridPane.getRowIndex((Node)(e.getSource())).intValue();
								int col = GridPane.getColumnIndex((Node)(e.getSource())).intValue();
								
								//Checking if its first player who clicked the button 
								if(((Button) e.getSource()).getText() != " " && override == false) {
									//Print label saying that the play Can not place anything there unless the button is active
									Label text = new Label();
									text.setText("I'm afraid you cannot do that");
									borderPane.setBottom(text);
								} else if(override == true) {
									playerAction(player_Turn,false,e,row,col);
									
								} else {
									playerAction(player_Turn,false,e,row,col);
									
							
								}
								
							}
							
						});
						gameBoard.add(btn, i, j);
					}
				}
				borderPane.setCenter(gameBoard);
			}
		 });
		 
		 //Making the custom Game button
		 Button override_Button = new Button("OVERRIDE");
		 override_Button.setOnAction(e -> override = true);
		 
		 //Adding buttons 
		 gameButtons.getChildren().addAll(quickGame,override_Button);
		 
		 //Adding button bar to the top of the borderPane
		 borderPane.setTop(gameButtons);
		 
		 
	     Scene scene = new Scene(borderPane,450,450);
	     mainWindow.setScene(scene);
	     mainWindow.show();
	}
	
	

}
