
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RetseptiGeneraator extends Application {
	Stage aken;

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		aken = primaryStage;
		primaryStage.setTitle("Retseptigeneraator");

		GridPane grid = new GridPane();
		
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setMinSize(300, 300);
		grid.setVgap(5);
		grid.setHgap(5);

		String[][] komponendid = { { "Munad", "tk" }, { "Piim", "dl" }, { "Jahu", "g" }, {"Suhkur", "g"} };

		HBox hb = new HBox();
		Label küsimus = new Label("Mitmele?  ");
		TextField vastus = new TextField("1");
		vastus.setPrefColumnCount(1);
		hb.getChildren().addAll(küsimus, vastus);
		grid.add(hb, 0, 0);

		
		HashMap<Label, TextField> olemasOlev = new HashMap<Label, TextField>();
		int i = 6;
		for (String[] komponent : komponendid) {

			Label nimi = new Label(komponent[0]);
			TextField kogus = new TextField("0");
			Label ühik = new Label(komponent[1]);

			olemasOlev.put(nimi, kogus);

			kogus.setPrefColumnCount(2);

			grid.add(nimi, 0, i);
			grid.add(kogus, 1, i);
			grid.add(ühik, 2, i);

			i += 1;
			
		}
		
		Retseptiraamat heleniRetseptiraamat = new Retseptiraamat();
		heleniRetseptiraamat.loeSisseKoikRetseptid("C:\\Users\\HelenHen\\Documents\\OOP\\värk");
		
		grid.setStyle("-fx-background-color: #D2BFD0;");
		Button valmis = new Button("Valmis");
		valmis.setOnAction((event) -> {

			try {
				
				HashMap<String, Integer> olemas = new HashMap<String, Integer>();		
				for (Label nimi : olemasOlev.keySet()) {
					if (!olemasOlev.get(nimi).getText().equals("0")) {
						olemas.put(nimi.getText(), Integer.parseInt(olemasOlev.get(nimi).getText()));
						// loeb olemasolevad toiduained ja nende kogused uude Hashmapi
					}
				}
				System.out.println(olemas);
				int mitmele = Integer.parseInt(vastus.getText());
				
				ArrayList<Retsept> retseptivalikud = heleniRetseptiraamat.suurendaKoguseid(mitmele).pakuRetsepte(olemas); //tagastab
				System.out.println(heleniRetseptiraamat.suurendaKoguseid(mitmele));
				// suurendab retseptides koguseid vastavalt soovitud portsude arvule ja pakub retseptid, mida olemasolevast teha saab
				ArrayList <Button> nupud = teeNupud(retseptivalikud);
				
				GridPane grid1 = new GridPane();
				HBox hb1 = new HBox();
				Label lause = new Label("Siin on sinu valikud: ");
				grid1.setPadding(new Insets(20, 20, 20, 20));
				grid1.setMinSize(300, 300);
				grid1.setVgap(5);
				grid1.setHgap(5);
				grid1.add(lause, 0, 2);
				int n = 4;
				
				for(Button nupp: nupud){
					grid1.add(nupp, 0, n);
					n+=1;
				}
					
				
				Scene taust1 = new Scene(grid1, 300, 300);
				valmis.setOnAction(e -> aken.setScene(taust1));
				aken.setScene(taust1);
				aken.show(); // nuppudega ekraan

				for (Button nupp: nupud){
					nupp.setOnAction((event1) -> {
						try {
							VBox layout2 = new VBox(30); 
							Scene taust2 = new Scene(layout2, 200,200);						
							String retseptisisu = "";

								if (event1.getSource() == nupp){   //kui vajutab seda nuppu, otsib sellele vastava retsepti failist
									for (Retsept retsept: heleniRetseptiraamat.suurendaKoguseid(mitmele).retseptid){
										if (retsept.nimi.equals(nupp.getText())){
											retseptisisu = retsept.toString();
										}
									
									}
									
									aken.setScene(taust2);
									layout2.setPadding(new Insets(20,20,20,20));
									layout2.getChildren().add(new Text(retseptisisu));
									primaryStage.show();
									
								}
									
							
						}catch (Exception e) {
							e.printStackTrace();
						}
					});
				
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		});

		grid.add(valmis, i, 0);
		Scene scene = new Scene(grid, 300, 300, Color.BLUE);
		aken.setScene(scene);
		aken.show();
	}
	public ArrayList <Button> teeNupud(ArrayList <Retsept> järjend) {
		ArrayList <Button> nupud = new ArrayList <>();
		for(Retsept retsept : järjend){
		
			Button nupp = new Button(retsept.getNimi());
			nupud.add(nupp);		
		}
		return nupud;
	}


	public static void main(String[] args) {
		launch(args);
	}
}