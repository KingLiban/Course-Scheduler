package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Scene1 extends Application {
    private static final String ERR_USERNAME = "A proper name must be provided (Numbers must NOT be included).";
    private static final String ERR_EMAIL = "Your WIT Email must be in the following Format: username@wit.edu (replace with your own WIT Email).";
	private static final String ERR_STUDENT_YEAR = "Please select a student year out of the ones provided";
	private static final String ERR_MAJOR = "Please select a major out of the ones provided (As of now, only the ones listed are available.)";
	private static final String ERR_SEMESTER = "Please select your current semester out of the ones provided";
    private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";
    @Override
    public void start(Stage stage) throws FileNotFoundException {
    	stage.setTitle("Course Generator");

		Menu fileMenu = new Menu("File");
		MenuItem menuItem1 = new MenuItem("Item 1");
		MenuItem menuItem2 = new MenuItem("Item 2");
		fileMenu.getItems().add(menuItem1);
		fileMenu.getItems().add(menuItem2);
		Menu helpMenu = new Menu("Help");
		MenuItem menuItem3 = new MenuItem("Item 3");
		MenuItem menuItem4 = new MenuItem("Item 4");
		helpMenu.getItems().add(menuItem3);
		helpMenu.getItems().add(menuItem4);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu,helpMenu);

		VBox vBox = new VBox();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Hello Wentworth Student! Please enter the following information to begin:");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Label userName = new Label("Name:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		String[] years = {
				"Freshman", "Sophomore", "Junior", "Senior"
		};
		Label studYear = new Label("Your Year:");
		grid.add(studYear, 0, 2);
		ComboBox<String> studentYear = new ComboBox<>(FXCollections.observableArrayList(years));
		grid.add(studentYear, 1, 2);
		
		String[] seasons = {
				"Fall", "Spring", "Summer"
		};
		Label  season = new Label("Current Semester:");
		grid.add(season, 0, 3);
		ComboBox<String> choicesOfSeasons = new ComboBox<>(FXCollections.observableArrayList(seasons));
		grid.add(choicesOfSeasons, 1, 3);
		
		Label witEmail = new Label("WIT Email:");
		grid.add(witEmail, 0, 4);
		TextField userWitEmail = new TextField();
		grid.add(userWitEmail, 1, 4);
		
		String[] majors = {
				"Computer Science", "Information Technology", "Computer Networking", "Data Science", "CyberSecurity", "Applied Mathematics"
		};
		Label major = new Label("Your Major:");
		grid.add(major, 0, 5);
		ComboBox<String> comboBox2 = new ComboBox<>(FXCollections.observableArrayList(majors));
		grid.add(comboBox2, 1, 5);
		
		Button nextButton = getButton(
				userTextField, choicesOfSeasons, studentYear,userWitEmail, comboBox2, stage, menuBar
		);
		nextButton.setText("Next");
		grid.add(nextButton, 0, 6);

		Image image = new Image(new FileInputStream("src/images/WIT.png"), 500.0, 132.5, false, false);
		ImageView imageView = new ImageView(image);
		vBox.getChildren().add(menuBar);
		vBox.getChildren().add(grid);
		vBox.getChildren().add(imageView);
		vBox.setAlignment(Pos.CENTER);

		VBox root = new VBox();
		root.getChildren().addAll(menuBar, vBox);
		Scene scene = new Scene(root, 1200, 780);
		stage.setScene(scene);

		stage.show();
    }
    
    public static boolean validName(String name) {
    	if (name.isEmpty()) return false;

		for (char c : name.toCharArray()) {
			if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
				return false;
			}
		}
    	return true;
    }
    public static void invalidNameAlert() {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please try again");
		alert.setHeaderText("Incorrect input for Name");
		alert.setContentText(ERR_USERNAME);
		alert.showAndWait();
    }
    public static void invalidEmailAlert() {
    	Alert alert3 = new Alert(AlertType.ERROR);
		alert3.setTitle("Please try again");
		alert3.setHeaderText("Incorrect input for WIT Email");
		alert3.setContentText(ERR_EMAIL);
		alert3.showAndWait();
    }
    public static void invalidSelection(String ERR) {
    	Alert alert4 = new Alert(AlertType.ERROR);
		alert4.setTitle("Please Try Again");
		alert4.setHeaderText("No selection made");
		alert4.setContentText(ERR);
		alert4.showAndWait();
    }
    public static boolean validEmail(String email) {
    	if (email.length() <= 8) {
    		return false;
    	}
    	String subString = email.substring(email.length() - 8);
    	return subString.equals("@wit.edu");
    }
	private static Button getButton(
			TextField userTextField,
			ComboBox choicesOfSeasons,
			ComboBox studentYear,
			TextField userWitEmail,
			ComboBox comboBox2,
			Stage stage,
			MenuBar menuBar
	) {
		Button nextButton = new Button();
		nextButton.setOnAction(event -> {
				String userN = userTextField.getText();
				String email = userWitEmail.getText();
				if (!validName(userN)) {
					invalidNameAlert();
				} else if (studentYear.getSelectionModel().isEmpty()) {
					invalidSelection(ERR_STUDENT_YEAR);
				} else if(choicesOfSeasons.getSelectionModel().isEmpty()) {
					invalidSelection(ERR_SEMESTER);
				} else if (!validEmail(email)) {
					invalidEmailAlert();
				} else if (comboBox2.getSelectionModel().isEmpty()) {
					invalidSelection(ERR_MAJOR);
				} else {
					Optional<ButtonType> result = getButtonType();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						Student userEntries = new Student(
								userTextField.getText(),
                                (String) choicesOfSeasons.getValue(),
                                (String) studentYear.getValue(),
								userWitEmail.getText(),
                                (String) comboBox2.getValue()
                        );
						stage.setScene(Scene2.createScene2(stage, userEntries, menuBar));
					}
				}
				}
		);
		return nextButton;
	}

	private static Optional<ButtonType> getButtonType() {
		Alert confirm = getAlert();
        return confirm.showAndWait();
	}

	private static Alert getAlert() {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Confirmation");
		confirm.setHeaderText("Are you sure you wish to move on?");
		confirm.setContentText(CONFIRM_ENTRIES);
		return confirm;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
