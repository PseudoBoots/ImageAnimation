package imageanimation;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;

import java.util.Collection;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class ImageAnimation  extends Application
{
	private ImageView iView;
	private Label lblSpeed;
	private TextField tfSpeed;
	private Label lblNumImages;
	private TextField tfNumImages;
	private Label lblPrefix;
	private TextField tfPrefix;
	private Button btnAnimate;
	private Label lblImageType;
	private TextField tfImageType;
	
	private Timeline animation;
	private int frameNum = 0;
	
	

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Animation");
		
		//initialize nodes
		iView = new ImageView();
		lblSpeed = new Label("Speed: ");
		tfSpeed = new TextField();
		lblNumImages = new Label("Number of Images: ");
		tfNumImages = new TextField();
		lblPrefix = new Label("Image prefix: ");
		tfPrefix = new TextField();
		lblImageType = new Label("Image Type: ");
		tfImageType = new TextField();
		btnAnimate = new Button("Animate");
		
		
		//create layouts
		BorderPane rootLayout = new BorderPane();
        GridPane inputLayout = new GridPane();
        for(int i = 0; i < 10; i++)
        {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(10);
            inputLayout.getColumnConstraints().add(col);
        }
        for(int i = 0; i < 5; i++)
        {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(20.0);
            inputLayout.getRowConstraints().add(row);
        }
        
        inputLayout.getChildren().addAll(lblSpeed, tfSpeed, lblNumImages, tfNumImages, lblPrefix, tfPrefix, lblImageType, tfImageType, btnAnimate);
        GridPane.setConstraints(lblSpeed, 0, 1, 3, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(tfSpeed, 3, 1);
        GridPane.setConstraints(lblNumImages, 0, 3, 3, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(tfNumImages, 3, 3);
        GridPane.setConstraints(lblPrefix, 5, 1, 2, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(tfPrefix, 7, 1, 2, 1);
        GridPane.setConstraints(lblImageType, 5, 3, 2, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(tfImageType, 7, 3, 2, 1);
        GridPane.setConstraints(btnAnimate, 3, 5, 2, 1, HPos.CENTER, VPos.CENTER);
        
        rootLayout.setTop(inputLayout);
        rootLayout.setCenter(iView);
        
        btnAnimate.setOnAction(event ->{
        		int speed = Integer.parseInt(tfSpeed.getText());
        		String prefix = tfPrefix.getText();
        		String fileType = tfImageType.getText();
        		int numFrames = Integer.parseInt(tfNumImages.getText());
        		
        		/*animation = new Timeline(new KeyFrame(Duration.millis(speed), e -> changeImage(prefix, fileType)));
        		animation.setCycleCount(Timeline.INDEFINITE);
        		animation.play();*/
        		
        		//this.imgView = new ImageView(images[0]);
        	    animation = new Timeline();
        	    Collection<KeyFrame> frames = animation.getKeyFrames();
        	    Duration frameGap = Duration.millis(speed);
        	    Duration frameTime = Duration.ZERO ;
        	    for (Image img : createImgArray(prefix, fileType, numFrames))
        	    {
        	        frameTime = frameTime.add(frameGap);
        	        frames.add(new KeyFrame(frameTime, e -> iView.setImage(img)));
        	    }
        	    animation.setCycleCount(Timeline.INDEFINITE);
        	    animation.play();
        });

		
		Scene scene = new Scene(rootLayout, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private Image[] createImgArray(String prefix, String type, int size)
	{
		Image[] imgs = new Image[size];
		String fileName;
		
		for(int i = 1; i <= size; i++)
		{
			fileName = prefix + i + "." + type;
			imgs[i-1] = new Image("res/" + fileName);
		}
		return imgs;
	}
	
	private void changeImage(String prefix, String type)
	{
		String fileName;
		
		frameNum++;
		if(frameNum > Integer.parseInt(tfNumImages.getText())) frameNum = 1;
		fileName = prefix + frameNum + "." + type;
		System.out.println(fileName);
		iView.setImage(new Image("res/" + fileName));
	}

}
