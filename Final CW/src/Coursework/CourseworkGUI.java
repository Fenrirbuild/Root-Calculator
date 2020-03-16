package Coursework;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mohammad Ali ma2367y-001042988
 */
public class CourseworkGUI extends javax.swing.JFrame {

    public JFrame mainFrame;
    public DefaultTableModel newtonTableModel, secantTableModel, bisectionTableModel, steffensenTableModel;
    //public Expression exprF, exprFd, exprG;  // https://docs.oracle.com/javase/7/docs/api/java/beans/Expression.html 
    public Graph plotF, plotG, plotError, plotFx;

    //option variable
    public int maxIteration; //maximum iteration 
    public double x0, x1, es, graphdata; //initial guess, es, root answer
    public static double graphrange; //plot range

    //arrayList of Data
    public static ArrayList<Variables> raphsonData;
    public static ArrayList<Variables> secantData;
    public static ArrayList<Variables> bisectionData;
    public static ArrayList<Variables> steffensenData;

    public CourseworkGUI() {
        initComponents();
        init();
    }

    public CourseworkGUI(JFrame mainFrame) {
        initComponents();
        init();

        this.mainFrame = mainFrame;
        this.mainFrame.setVisible(false);
    }

    private void init() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximized

        //Get table model
        newtonTableModel = (DefaultTableModel) raphsonTable.getModel();
        secantTableModel = (DefaultTableModel) secantTable.getModel();
        steffensenTableModel = (DefaultTableModel) steffensenTable.getModel();
        bisectionTableModel = (DefaultTableModel) bisectionTable.getModel();

        setDefaultText();
    }

    private void setDefaultText() {
        //Sample test values which are called when reset button is pressed 
        txtF.setText("");
        txtX0.setText("0.5");
        txtX1.setText("2");
        txtRange.setText("20");
        functioncmb.setSelectedItem("[Select Function]");
        methodcmb.setSelectedItem("[Select Method]");
        roundcmb.setSelectedItem("Standard");
        txtX1.setVisible(true);
        jLabel3.setVisible(true);
        this.setVisible(true);

    }
// https://www.dotnetperls.com/double-truncate-java

    private String decimalpoint(double x) {

        if (roundcmb.getSelectedItem().equals("Standard")) {
            return new DecimalFormat("#.###############").format(x); // standard is set to 15 digits
        } else if (roundcmb.getSelectedItem().equals("1")) {

            return new DecimalFormat("#.#").format(x);
        } else if (roundcmb.getSelectedItem().equals("2")) {

            return new DecimalFormat("#.##").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("3")) {

            return new DecimalFormat("#.###").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("4")) {

            return new DecimalFormat("#.####").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("5")) {

            return new DecimalFormat("#.#####").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("6")) {

            return new DecimalFormat("#.######").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("7")) {

            return new DecimalFormat("#.#######").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("8")) {

            return new DecimalFormat("#.########").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("9")) {

            return new DecimalFormat("#.#########").format(x);
        } else if (roundcmb.getSelectedItem()
                .equals("10")) {

            return new DecimalFormat("#.##########").format(x);
        }

        return new DecimalFormat(
                "#.###############").format(x);

    }

    /* private double f(double x) {
        //Set the expression for variables like x,e and ln
        exprF.setVariable("x", x);
        exprF.setVariable("e", Math.E);
        exprF.setVariable("ln", Math.log(x0));

        //Evaluate expression
        double result = exprF.evaluate();
        return result;
    }

    private double fd(double x) {
        //Calculate differential with low but finite difference
        if (txtFd.getText().isEmpty()) {
            double h = 0.00001;
            return (f(x + h) - f(x)) / h;
        } else {
            //Set the expression for variables like x,e and ln
            exprFd.setVariable("x", x);
            exprFd.setVariable("e", Math.E);
            exprFd.setVariable("ln", Math.log10(x0));
            //Evaluate expression
            double result = exprFd.evaluate();
            return result;
        }
    }

    private double g(double x) {
        //Calculate g(x)
        if (txtG.getText().isEmpty()) {
            return f(x) + x;
        } else {
            //Set the expression variable
            exprG.setVariable("x", x);
            exprG.setVariable("e", Math.E);
            exprG.setVariable("ln", Math.log10(x0));
            //Evaluate expression
            double result = exprG.evaluate();
            return result;
        }
    } */
    private void newton1() {
        raphsonData = new ArrayList<>();
        double xnew = 0, fxold, fxnew = 0, fdashxold, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel in case of any error in convergence 
                Raphsonlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Raphsonlabel.setForeground(Color.RED);
                return;
            }

            fxold = x0 - Math.pow(x0, 2.0);
            fdashxold = 1.0 - (2.0 * x0);
            xnew = x0 - (fxold / fdashxold);
            fxnew = xnew - Math.pow(xnew, 2.0);
            ea = Math.abs((xnew - x0) / xnew) * 100;
            newtonTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold), decimalpoint(fdashxold), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //adding data to Table Model
            raphsonData.add(new Variables(i, xnew, (fxnew), ea)); //save data to ArrayList
            x0 = xnew;
            i++;
        }

        //Set JLabel in case of roots are found 
        Raphsonlabel.setText(decimalpoint(xnew));
        Raphsonlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Raphsonlabel.setForeground(Color.RED);
        }

        //Save root for plotting
        graphdata = xnew;
    }

    private void newton2() {

        raphsonData = new ArrayList<>();
        double xnew = 0, fxold, fxnew = 0, fdashxold, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel in case of any error in convergence 
                Raphsonlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Raphsonlabel.setForeground(Color.RED);
                return;
            }

            fxold = (Math.log(Math.abs(x0 + 1.0))) + 1.0;
            fdashxold = (1.0 / (x0 + 1.0));
            xnew = x0 - (fxold / fdashxold);
            fxnew = xnew - Math.pow(xnew, 2.0);
            ea = Math.abs((xnew - x0) / xnew) * 100;
            newtonTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold), decimalpoint(fdashxold), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            raphsonData.add(new Variables(i, xnew, (fxnew), ea)); //save data to ArrayList
            x0 = xnew;
            i++;
        }

        //Set JLabel in case of roots are found 
        Raphsonlabel.setText(decimalpoint(xnew));
        Raphsonlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Raphsonlabel.setForeground(Color.RED);
        }

        //Save root for plotting
        graphdata = xnew;
    }

    private void newton3() {

        raphsonData = new ArrayList<>();
        double xnew = 0, fxold, fxnew = 0, fdashxold, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel in case of any error in convergence 
                Raphsonlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Raphsonlabel.setForeground(Color.RED);
                return;
            }

            fxold = (Math.exp(x0)) - (3.0 * x0);
            fdashxold = (Math.exp(x0) - 3.0);
            xnew = x0 - (fxold / fdashxold);
            fxnew = xnew - Math.pow(xnew, 2.0);
            ea = Math.abs((xnew - x0) / xnew) * 100;
            newtonTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold), decimalpoint(fdashxold), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            raphsonData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            x0 = xnew;
            i++;
        }

        //Set JLabel in case of roots are found 
        Raphsonlabel.setText(decimalpoint(xnew));
        Raphsonlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Raphsonlabel.setForeground(Color.RED);
        }

        //Save root for plotting
        graphdata = xnew;
    }

    private void secant1() {
        secantData = new ArrayList<>();

        int i = 1;
        double x0, x1, xnew = 0, fxold1, fxold2, fxnew = 0, ea;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());

        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Secantlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Secantlabel.setForeground(Color.RED);
                return;
            }

            //Calculate
            fxold1 = x0 - Math.pow(x0, 2);
            fxold2 = x1 - Math.pow(x1, 2);
            xnew = x0 - (fxold1 * (x0 - x1)) / (fxold1 - fxold2);
            fxnew = xnew - Math.pow(xnew, 2);
            if (i == 1) {
                secantTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold1), decimalpoint(x1), decimalpoint(fxold2), decimalpoint(xnew), decimalpoint(fxnew), "-"});
                secantData.add(new Variables(i, xnew, fxnew, Double.NaN)); //save data to ArrayList
            } else {
                ea = Math.abs((xnew - x1) / xnew) * 100;
                secantTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold1), decimalpoint(x1), decimalpoint(fxold2), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
                secantData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            }

            x0 = x1;
            x1 = xnew;

            i++;
        }

        //Set JLabelOP
        Secantlabel.setText(decimalpoint(xnew));
        Secantlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Secantlabel.setForeground(Color.RED);
        }
    }

    private void secant2() {
        secantData = new ArrayList<>();

        int i = 1;
        double xnew = 0, fxold1, fxold2, fxnew = 0, ea;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());

        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Secantlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Secantlabel.setForeground(Color.RED);
                return;
            }

            //Calculate
            fxold1 = (Math.log(x0 + 1.0)) + 1.0;
            fxold2 = (Math.log(x1 + 1.0)) + 1.0;
            xnew = x0 - (fxold1 * (x0 - x1)) / (fxold1 - fxold2);
            fxnew = (Math.log(xnew + 1.0)) + 1.0;
            if (i == 1) {
                secantTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold1), decimalpoint(x1), decimalpoint(fxold2), decimalpoint(xnew), decimalpoint(fxnew), "-"});
                secantData.add(new Variables(i, xnew, fxnew, Double.NaN)); //save data to ArrayList
            } else {
                ea = Math.abs((xnew - x1) / xnew) * 100;
                secantTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold1), decimalpoint(x1), decimalpoint(fxold2), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
                secantData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            }

            x0 = x1;
            x1 = xnew;

            i++;
        }

        //Set JLabelOP
        Secantlabel.setText(decimalpoint(xnew));
        Secantlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Secantlabel.setForeground(Color.RED);
        }

    }

    private void secant3() {
        secantData = new ArrayList<>();

        int i = 1;
        double xnew = 0, fxold1, fxold2, fxnew = 0, ea;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Secantlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Secantlabel.setForeground(Color.RED);
                return;
            }

            //Calculate
            fxold1 = (Math.abs(x0)) - (3.0 * x0);
            fxold2 = (Math.abs(x1)) - (3.0 * x1);
            xnew = x0 - (fxold1 * (x0 - x1)) / (fxold1 - fxold2);
            fxnew = (Math.abs(xnew)) - (3 * xnew);
            if (i == 1) {
                secantTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold1), decimalpoint(x1), decimalpoint(fxold2), decimalpoint(xnew), decimalpoint(fxnew), "-"});
                secantData.add(new Variables(i, xnew, fxnew, Double.NaN)); //save data to ArrayList
            } else {
                ea = Math.abs((xnew - x1) / xnew) * 100;
                secantTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxold1), decimalpoint(x1), decimalpoint(fxold2), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
                secantData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            }

            x0 = x1;
            x1 = xnew;

            i++;
        }

        //Set JLabelOP
        Secantlabel.setText(decimalpoint(xnew));
        Secantlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Secantlabel.setForeground(Color.RED);
        }
    }

    private void bisection1() {

        bisectionData = new ArrayList<>();

        double xnew = 0, fxlower, fxupper, fxnew = 0, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        fxlower = x0 - Math.pow(x0, 2);
        fxupper = x1 - Math.pow(x1, 2);

        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Bisectionlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Bisectionlabel.setForeground(Color.RED);
                return;
            }

            xnew = (x0 + x1) / 2.0;
            fxnew = xnew - Math.pow(xnew, 2);
            ea = Math.abs((xnew - x0) / xnew) * 100;
            bisectionTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxlower), decimalpoint(x1), decimalpoint(fxupper), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            bisectionData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            if (fxlower * fxnew > 0) {
                x0 = xnew;
                fxlower = fxnew;
            } else if (fxupper * fxnew > 0) {
                x1 = xnew;
                fxupper = fxnew;
            }
            i++;

        }
        Bisectionlabel.setText(decimalpoint(xnew));
        Bisectionlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Bisectionlabel.setForeground(Color.RED);
        }
        graphdata = xnew;
    }

    private void bisection2() {

        bisectionData = new ArrayList<>();

        double xnew = 0, fxlower, fxupper, fxnew = 0, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Bisectionlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Bisectionlabel.setForeground(Color.RED);
                return;
            }
            fxlower = (Math.log(Math.abs(x0 + 1.0))) + 1.0;
            fxupper = (Math.log(x1 + 1.0)) + 1.0;
            xnew = (x0 + x1) / 2.0;
            fxnew = (Math.log(Math.abs(xnew + 1.0))) + 1.0;
            ea = Math.abs((xnew - x0) / xnew) * 100;
            bisectionTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxlower), decimalpoint(x1), decimalpoint(fxupper), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            bisectionData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            if (fxlower * fxnew > 0) {
                x0 = xnew;
                fxlower = fxnew;
            } else if (fxupper * fxnew > 0) {
                x1 = xnew;
                fxupper = fxnew;
            }
            i++;

        }
        Bisectionlabel.setText(decimalpoint(xnew));
        Bisectionlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Bisectionlabel.setForeground(Color.RED);
        }
    }

    private void bisection3() {
        bisectionData = new ArrayList<>();

        double xnew = 0, fxlower, fxupper, fxnew = 0, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());
        x1 = Double.parseDouble(txtX1.getText());
        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Bisectionlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Bisectionlabel.setForeground(Color.RED);
                return;
            }
            fxlower = (Math.abs(x0)) - (3.0 * x0);
            fxupper = (Math.abs(x1)) - (3.0 * x1);
            xnew = (x0 + x1) / 2.0;
            fxnew = (Math.abs(xnew)) - (3.0 * xnew);
            ea = Math.abs((xnew - x0) / xnew) * 100;
            bisectionTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fxlower), decimalpoint(x1), decimalpoint(fxupper), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            bisectionData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            if (fxlower * fxnew > 0) {
                x0 = xnew;
                fxlower = fxnew;
            } else if (fxupper * fxnew > 0) {
                x1 = xnew;
                fxupper = fxnew;
            }

            i++;

        }
        Bisectionlabel.setText(decimalpoint(xnew));
        Bisectionlabel.setForeground(Color.BLUE);
        if (Math.abs((fxnew)) > 1) {
            Bisectionlabel.setForeground(Color.RED);
        }
    }

    private void steffensen1() {
        steffensenData = new ArrayList<>();

        double fx0, fdx0, fxnew = 0, xnew = 0, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());

        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Steffensenlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Steffensenlabel.setForeground(Color.RED);
                return;
            }

            fx0 = x0 - (Math.pow(x0, 2.0));
            fdx0 = fx0 - (Math.pow(x0, 2.0));
            xnew = x0 - (Math.pow(fx0 - x0, 2.0));
            fxnew = xnew - (Math.pow(xnew, 2.0));
            ea = Math.abs((xnew - x0) / xnew) * 100;

            steffensenTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fx0), decimalpoint(fdx0), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            steffensenData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            x0 = xnew;
            i++;
        }

        //Set JLabel
        Steffensenlabel.setText(decimalpoint(xnew));
        Steffensenlabel.setForeground(Color.BLUE);
        if (Math.abs(fxnew) > 1) {
            Steffensenlabel.setForeground(Color.RED);
        }
    }

    private void steffensen2() {
        steffensenData = new ArrayList<>();

        double fx0, fdx0, fxnew = 0, xnew = 0, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());

        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Steffensenlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Steffensenlabel.setForeground(Color.RED);
                return;
            }

            fx0 = (Math.log(x0 + 1.0)) + 1.0;
            fdx0 = (Math.log(fx0 + 1)) + 1.0;
            xnew = x0 - (Math.pow((fx0 - x0), 2.0)) / ((fx0 - 2.0) * (fx0 + x0));
            fxnew = xnew - (Math.pow(xnew, 2.0));
            ea = Math.abs((xnew - x0) / xnew) * 100;

            steffensenTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fx0), decimalpoint(fdx0), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            steffensenData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            x0 = xnew;
            i++;
        }

        //Set JLabel
        Steffensenlabel.setText(decimalpoint(xnew));
        Steffensenlabel.setForeground(Color.BLUE);
        if (Math.abs(fxnew) > 1) {
            Steffensenlabel.setForeground(Color.RED);
        }

    }

    private void steffensen3() {
        steffensenData = new ArrayList<>();

        double fx0, fdx0, fxnew = 0, xnew = 0, ea;
        int i = 1;
        ea = 100;
        x0 = Double.parseDouble(txtX0.getText());

        while (ea > es) {
            if (i > maxIteration) {
                //Set JLabel
                Steffensenlabel.setText("Failed to converge in " + maxIteration + " iterations");
                Steffensenlabel.setForeground(Color.RED);
                return;
            }

            fx0 = (Math.pow(Math.E, x0 - 3 * x0));
            fdx0 = (Math.pow(Math.E, fx0 - 3 * fx0));
            xnew = x0 - Math.pow(fx0 - x0, 2.0) / (fdx0 - 2 * fx0 + x0);
            fxnew = x0 - Math.pow(fx0 - x0, 2.0);
            ea = Math.abs((xnew - x0) / xnew) * 100;

            steffensenTableModel.addRow(new Object[]{i, decimalpoint(x0), decimalpoint(fx0), decimalpoint(fdx0), decimalpoint(xnew), decimalpoint(fxnew), decimalpoint(ea)}); //add data to Table Model
            steffensenData.add(new Variables(i, xnew, fxnew, ea)); //save data to ArrayList
            x0 = xnew;
            i++;
        }

        //Set JLabel
        Steffensenlabel.setText(decimalpoint(xnew));
        Steffensenlabel.setForeground(Color.BLUE);
        if (Math.abs(fxnew) > 1) {
            Steffensenlabel.setForeground(Color.RED);
        }
    }

    private void emptyFieldCheck() {
        //default values for all the Jtextfields
        es = 5;
        maxIteration = 100;

        if (!txtEs.getText().isEmpty()) {
            es = Double.parseDouble(txtEs.getText());
        }
        if (!txtIteration.getText().isEmpty()) {
            maxIteration = Integer.parseInt(txtIteration.getText());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        raphsonTable = new javax.swing.JTable();
        btnCalc = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        secantTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        steffensenTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtEs = new javax.swing.JTextField();
        txtIteration = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtRange = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtX0 = new javax.swing.JTextField();
        txtX1 = new javax.swing.JTextField();
        methodcmb = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        roundcmb = new javax.swing.JComboBox<>();
        functioncmb = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        bisectionTable = new javax.swing.JTable();
        btnPlot = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        Steffensenlabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Secantlabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        Raphsonlabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        Bisectionlabel = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btninfo = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Root Calculator");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        raphsonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Iterations", "X0", "fxold", "Fdxold", "xnew", "fxnew", "Ea (%)"
            }
        ));
        raphsonTable.setToolTipText("");
        jScrollPane1.setViewportView(raphsonTable);

        btnCalc.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCalc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Calculate.png"))); // NOI18N
        btnCalc.setText("Calculate");
        btnCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcActionPerformed(evt);
            }
        });

        secantTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Iterations", "X0", "f(X0)", "X1", "f(X1)", "Xnew", "f(xnew)", "ea (%)"
            }
        ));
        jScrollPane2.setViewportView(secantTable);

        steffensenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Iterations", "p0", "p1", "p2", "p", "f(x)", "ea (%)"
            }
        ));
        jScrollPane3.setViewportView(steffensenTable);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setText("Bisection");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel7.setText("Steffensen");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel8.setText("Newton-Raphson");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel9.setText("Secant");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Optional Features", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N
        jPanel1.setToolTipText("");
        jPanel1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Max Iteration");
        jLabel6.setToolTipText("");

        txtEs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEsActionPerformed(evt);
            }
        });

        txtIteration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIterationActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Error threshold:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("(default value: 5%)");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("(default value: 100)");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Graph Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Graph range:");

        txtRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(txtRange, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtRange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel16)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(86, 86, 86)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIteration, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIteration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        txtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("F(x):");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("X2:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("X1:");

        txtX0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtX0ActionPerformed(evt);
            }
        });

        txtX1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtX1ActionPerformed(evt);
            }
        });

        methodcmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Select Method]", "All", "1.Newton", "2.Secant", "3.Bisection", "4.Steffensen" }));
        methodcmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                methodcmbActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setText("Decimal Points :");

        roundcmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Standard", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        roundcmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundcmbActionPerformed(evt);
            }
        });

        functioncmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Select Function]", "1.x-x^2", "2.ln(x+1)+1", "3.e^x-3x" }));
        functioncmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                functioncmbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(functioncmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(methodcmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtX1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtF, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(txtX0)
                    .addComponent(roundcmb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(functioncmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(methodcmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtX0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundcmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        bisectionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Iterations", "x0", "fxlower", "x1", "fxupper", "xnew", "f(xnew) ", "ea (%)"
            }
        ));
        jScrollPane5.setViewportView(bisectionTable);

        btnPlot.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPlot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph.png"))); // NOI18N
        btnPlot.setText("Plot Graph");
        btnPlot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlotActionPerformed(evt);
            }
        });

        btnStop.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Exit.png"))); // NOI18N
        btnStop.setText("Exit");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Root:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        Steffensenlabel.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Steffensenlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Steffensenlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Root:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        Secantlabel.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(Secantlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(Secantlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Root:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N

        Raphsonlabel.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Raphsonlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Raphsonlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Root:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        Bisectionlabel.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Bisectionlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Bisectionlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/InsertData.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btninfo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btninfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info.png"))); // NOI18N
        btninfo.setText("Info");
        btninfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jLabel8)
                                .addGap(52, 52, 52)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(189, 189, 189)
                                .addComponent(jLabel9)
                                .addGap(51, 51, 51)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(btnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(66, 66, 66)
                                        .addComponent(btnPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(454, 454, 454)
                                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(40, 40, 40)
                                .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btninfo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(179, 179, 179)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(213, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(313, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btninfo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        mainFrame.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void resetField() {
        newtonTableModel.setRowCount(0);
        secantTableModel.setRowCount(0);
        bisectionTableModel.setRowCount(0);
        steffensenTableModel.setRowCount(0);

        Raphsonlabel.setText("");
        Secantlabel.setText("");
        Bisectionlabel.setText("");
        Steffensenlabel.setText("");
    }

    private void btnCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcActionPerformed
        try {
            //Call the resetField method and then obtains the x0 and x1 values 
            resetField();
            //Get data
            x0 = Double.parseDouble(txtX0.getText());
            x1 = Double.parseDouble(txtX1.getText());
            emptyFieldCheck();

            //this Constructs the expressionF for the variables
            // exprF = new ExpressionBuilder(txtF.getText()).variables("x", "e", "ln").build();
            //calling the  methods
            if (x0 > x1) {
                JOptionPane.showMessageDialog(this, " The value of  X1 is bigger than X2", "Important Message",
                        JOptionPane.WARNING_MESSAGE);
                setDefaultText();
            }
            if (functioncmb.getSelectedItem().equals("[Select Function]") && methodcmb.getSelectedItem().equals("[Select Method]")) {

                JOptionPane.showMessageDialog(null, "Please select a Function and Method to begin", "Important Message",
                        JOptionPane.WARNING_MESSAGE);
            } else if (methodcmb.getSelectedItem().equals("[Select Method]")) {

                JOptionPane.showMessageDialog(this, "Please select a method ", "Important Message",
                        JOptionPane.WARNING_MESSAGE);

            } else if (functioncmb.getSelectedItem().equals("[Select Function]")) {

                JOptionPane.showMessageDialog(this, "Please select a function", "Important Message",
                        JOptionPane.WARNING_MESSAGE);

            }

            /* if (functioncmb.getSelectedItem().equals("1.x-x^2") && methodcmb.getSelectedItem().equals("All")) {
                newton1();
                secant1();
                bisection1();
                // steffensen1();
            }
            else if (functioncmb.getSelectedItem().equals("2.ln(x+1)+1") && methodcmb.getSelectedItem().equals("All")) {
                newton2();
                secant2();
                bisection2();
                //steffensen();
            }
           else if (functioncmb.getSelectedItem().equals("3.e^x-3x") && methodcmb.getSelectedItem().equals("All")) {
                newton3();
                secant3();
                bisection3();
                steffensen1();
            }
             */
            switch (functioncmb.getSelectedItem().toString()) {// Switch is implemented  to check what function and method was selected by user
                case "1.x-x^2":
                    if (null != methodcmb.getSelectedItem().toString()) {
                        switch (methodcmb.getSelectedItem().toString()) {
                            case "All":
                                newton1();
                                secant1();
                                bisection1();
                                steffensen1();
                                break;
                            case "1.Newton":
                                newton1();
                                break;

                            case "2.Secant":
                                secant1();
                                break;
                            case "3.Bisection":
                                bisection1();
                                break;
                            case "4.Steffensen":
                                steffensen1();
                                break;
                        }
                    }
                    break;
                case "2.ln(x+1)+1":
                    if (null != methodcmb.getSelectedItem().toString()) {
                        switch (methodcmb.getSelectedItem().toString()) {
                            case "All":
                                newton2();
                                secant2();
                                bisection2();
                                steffensen2();

                                break;
                            case "1.Newton":
                                newton2();
                                //newtongraph();
                                break;
                            case "2.Secant":
                                secant2();
                                break;
                            case "3.Bisection":
                                bisection2();
                                break;
                            case "4.Steffensen":
                                steffensen2();
                                break;
                        }
                    }
                    break;
                case "3.e^x-3x":
                    if (null != methodcmb.getSelectedItem().toString()) {
                        switch (methodcmb.getSelectedItem().toString()) {
                            case "All":
                                newton3();
                                secant3();
                                bisection3();
                                steffensen1();
                                break;
                            case "1.Newton":
                                newton3();
                                break;
                            case "2.Secant":
                                secant3();
                                break;
                            case "3.Bisection":
                                bisection3();
                                break;
                            case "4.Steffensen":
                                steffensen3();
                                break;
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid Numerical Value to Continue");
            Logger
                    .getLogger(CourseworkGUI.class
                            .getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnCalcActionPerformed

    private void btnPlotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlotActionPerformed
        //if plot gragh button is pressed it hides all previous plots
        if (plotF != null) {
            plotF.setVisible(false);
            plotG.setVisible(false);
            plotError.setVisible(false);
            plotFx.setVisible(false);
        }

        //Getting the plot range from the optional pane
        graphrange = Double.parseDouble(txtRange.getText());

        //plotF = new Graph(strF, graphdata);
        // plotF.setVisible(true); //display f(x) plot
        //plotG = new Graph("x", strG, graphdata);
        // plotG.setVisible(true); //display g(x) plot
        plotError = new Graph(1);
        plotError.setVisible(true); //display error plot
        plotFx = new Graph(2);
        plotFx.setVisible(true); //display error plot

        graphdata = 0; //reset answer 
    }//GEN-LAST:event_btnPlotActionPerformed
    private void newtongraph() {

    }

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        // TODO add your handling code here:
        System.exit(0); // Simple for exiting the program without cancelling
    }//GEN-LAST:event_btnStopActionPerformed

    private void txtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFActionPerformed
        // TODO add your handling code here:

        btnCalc.doClick();
    }//GEN-LAST:event_txtFActionPerformed

    private void txtX0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtX0ActionPerformed
        // TODO add your handling code here:
        btnCalc.doClick();
    }//GEN-LAST:event_txtX0ActionPerformed

    private void txtX1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtX1ActionPerformed
        // TODO add your handling code here:
        btnCalc.doClick();
    }//GEN-LAST:event_txtX1ActionPerformed

    private void txtEsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEsActionPerformed
        // TODO add your handling code here:
        btnCalc.doClick();
    }//GEN-LAST:event_txtEsActionPerformed

    private void txtIterationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIterationActionPerformed
        // TODO add your handling code here:
        btnCalc.doClick();
    }//GEN-LAST:event_txtIterationActionPerformed

    private void txtRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRangeActionPerformed
        // TODO add your handling code here:
        btnCalc.doClick();
    }//GEN-LAST:event_txtRangeActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        resetField();
        setDefaultText();
    }//GEN-LAST:event_btnResetActionPerformed

    private void methodcmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_methodcmbActionPerformed

        if (methodcmb.getSelectedItem().equals("1.Newton") || methodcmb.getSelectedItem().equals("4.Steffensen")) {
            txtX1.setVisible(false);
            jLabel3.setVisible(false);
            this.setVisible(true);
        } else {
            txtX1.setVisible(true);
            jLabel3.setVisible(true);
            this.setVisible(true);
        }


    }//GEN-LAST:event_methodcmbActionPerformed

    private void btninfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninfoActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Hello and welcome to Ma2367y`s Root finding Method Calculator,"
                + "You are provided with a friendly GUI program, it allows you to select from four different methods with an allowance of upto 10 decimal points"
                + "");
    }//GEN-LAST:event_btninfoActionPerformed

    private void functioncmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_functioncmbActionPerformed
        // TODO add your handling code here:
        if (functioncmb.getSelectedItem().equals("[Select Function]")) {

            txtF.setText("");
        }  if (functioncmb.getSelectedItem().equals("1.x-x^2")) {
            txtF.setText("x-x^2");
        } else if (functioncmb.getSelectedItem().equals("2.ln(x+1)+1")) {
            txtF.setText("ln(x+1)+1");
        } else if (functioncmb.getSelectedItem().equals("3.e^x-3x")) {
            txtF.setText("e^x-3x");
        }

    }//GEN-LAST:event_functioncmbActionPerformed

    private void roundcmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundcmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundcmbActionPerformed

    public static void main(String args[]) {

        // This look and feel has been taken from this  http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CourseworkGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CourseworkGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bisectionlabel;
    private javax.swing.JLabel Raphsonlabel;
    private javax.swing.JLabel Secantlabel;
    private javax.swing.JLabel Steffensenlabel;
    private javax.swing.JTable bisectionTable;
    private javax.swing.JButton btnCalc;
    private javax.swing.JButton btnPlot;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btninfo;
    private javax.swing.JComboBox<String> functioncmb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JComboBox<String> methodcmb;
    private javax.swing.JTable raphsonTable;
    private javax.swing.JComboBox<String> roundcmb;
    private javax.swing.JTable secantTable;
    private javax.swing.JTable steffensenTable;
    private javax.swing.JTextField txtEs;
    private javax.swing.JTextField txtF;
    private javax.swing.JTextField txtIteration;
    private javax.swing.JTextField txtRange;
    private javax.swing.JTextField txtX0;
    private javax.swing.JTextField txtX1;
    // End of variables declaration//GEN-END:variables
}
