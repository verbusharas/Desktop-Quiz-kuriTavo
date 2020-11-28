package lt.verbus.util;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class StatsTextBuilder {

    private String prefix;
    private double ratio;
    private String suffix;

    public StatsTextBuilder() {
        prefix = "";
        ratio = 0.0;
        suffix = "";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public TextFlow build() {

        Text txtPrefix = new Text(prefix);
        Text txtPercentage = new Text();
        Text txtSuffix = new Text();

        txtPrefix.setStyle("-fx-fill: white");
        txtSuffix.setStyle("-fx-fill: white");


        TextFlow statisticalText = new TextFlow();
        statisticalText.getChildren().add(0, txtPrefix);
        statisticalText.getChildren().add(1, txtPercentage);
        statisticalText.getChildren().add(2, txtSuffix);

        int roundedRatio = (int) Math.round(ratio * 100);
        if (roundedRatio > 0) {
            txtPercentage.setStyle("-fx-fill: #d54b3c");
            txtPercentage.setText("▼ " + Math.abs(roundedRatio) + "%");
            txtSuffix.setText(" pesimistiškiau nei " + suffix);
        } else if (roundedRatio < 0) {
            txtPercentage.setStyle("-fx-fill: #fff600");
            txtPercentage.setText("▲ " + Math.abs(roundedRatio) + "%");
            txtSuffix.setText(" optimistiškiau nei " + suffix);
        } else {
            txtPercentage.setText("▬");
            txtSuffix.setText("manai lygiai taip, kaip ir " + suffix);
        }
        return statisticalText;
    }
}
