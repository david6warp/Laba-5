package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Controller {
    public RadioButton function1;
    public RadioButton function2;
    public Label func;
    public TextField variable;
    public Label sol;
    public TextField xstart;
    public TextField xfinish;
    public LineChart<String, Double> chart;
    double b = 1.5;
    double a = 15.5;
    double x = -2.8;
    Function first = new DiffFunction(new PowerFunction(1.0/2,new SumFunction(new PowerFunction(2,new LinearFunction(1)),new ConstFunction(b))), new MultFunction(new PowerFunction(2,new ConstFunction(b)),new FracFunction(new PowerFunction(3,new SinFunction(new SumFunction(new LinearFunction(1),new ConstFunction(a)))),new LinearFunction(1))));
    Function second = new DiffFunction(new PowerFunction(2,new CosFunction(new PowerFunction(3,new LinearFunction(1)))),new MultFunction(new LinearFunction(1),new PowerFunction(-1.0/2,new SumFunction(new PowerFunction(2,new ConstFunction(a)),new PowerFunction(2,new ConstFunction(b))))));
    //Function second = new SumFunction(new ConstFunction(23),new LinearFunction(5));
    Function general = new ConstFunction(0);


    @FXML
    void set(ActionEvent event){
        function1.setText(first.print());
        function2.setText(second.print());
        if(function1.isSelected()){
            general = first;
        }
        if(function2.isSelected()){
            general = second;
        }
        func.setText("y = "+general.print());
    }

    public void derivative(ActionEvent actionEvent) {
        general = general.derivative();
        func.setText("y = "+general.print());
    }

    public void calculate(ActionEvent actionEvent) {
        sol.setText("y = "+general.calculate(Double.parseDouble(variable.getText())));
    }

    public void drawchart(ActionEvent actionEvent) {
        final XYChart.Series<String, Double> series = new XYChart.Series<>();
        double x0 = Double.parseDouble(xstart.getText());
        double xn = Double.parseDouble(xfinish.getText());
        if(x0>xn){
            double x = x0;
            x0 = xn;
            xn = x;
        }
        for(double i = x0;i<xn;i+=0.1){
            double x = i;
            double y = general.calculate(x);
            series.getData().add(new XYChart.Data<>(String.format("%.2f",x),y));
        }
        chart.getData().clear();
        chart.getData().add(series);
    }
}
