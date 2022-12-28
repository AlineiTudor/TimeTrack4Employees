import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ClientMenu extends JFrame {
    private JComboBox employeeNameBox;
    private JButton loadEmpButton;
    private Client client = new Client();
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JButton submitClocking;
    private JLabel checkDateLabel;
    private JTextField checkDateField;

    public ClientMenu() {
        setBounds(100, 100, 700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);


        /**ComboBox initializer*/
        employeeNameBox = new JComboBox();
        employeeNameBox.setBounds(30, 30, 200, 30);
        employeeNameBox.addActionListener(e -> enableTimeFields());
        add(employeeNameBox);

        /**Load Employees button*/
        loadEmpButton = new JButton("Load Employees");
        loadEmpButton.setBounds(240, 30, 150, 30);
        loadEmpButton.addActionListener(e -> {
            try {
                fillEmployeesComboBox();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, ioException.getMessage());
            } catch (JSONException jsonException) {
                JOptionPane.showMessageDialog(this, jsonException.getMessage());
                ;
            }
        });
        add(loadEmpButton);

        /**Check in zone*/
        checkInLabel = new JLabel("Check in (hh:mm:ss):");
        checkInLabel.setBounds(30, 90, 140, 30);
        add(checkInLabel);
        checkInField = new JTextField();
        checkInField.setBounds(30, 120, 140, 30);
        checkInField.setEditable(false);
        add(checkInField);

        /**Check out zone*/
        checkOutLabel = new JLabel("Check out (hh:mm:ss):");
        checkOutLabel.setBounds(200, 90, 150, 30);
        add(checkOutLabel);
        checkOutField = new JTextField();
        checkOutField.setBounds(200, 120, 150, 30);
        checkOutField.setEditable(false);
        add(checkOutField);

        /**Check zone*/
        checkDateLabel = new JLabel("Check date(optional: yyyy-mm-dd):");
        checkDateLabel.setBounds(380, 90, 250, 30);
        add(checkDateLabel);
        checkDateField = new JTextField();
        checkDateField.setBounds(380, 120, 150, 30);
        checkDateField.setEditable(false);
        add(checkDateField);


        /**Submit clocking button*/
        submitClocking = new JButton("Submit");
        submitClocking.setBounds(200, 200, 150, 30);
        submitClocking.addActionListener(e -> {
            try {
                insertNewClocking();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, ioException.getMessage());
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(this, parseException.getMessage());
            } catch (JSONException jsonException) {
                JOptionPane.showMessageDialog(this, jsonException.getMessage());
            } catch (DateFormatException dateFormatException) {
                JOptionPane.showMessageDialog(this, dateFormatException.getMessage());
            }
        });
        submitClocking.setEnabled(false);
        add(submitClocking);

        setVisible(true);

    }

    public void fillEmployeesComboBox() throws IOException, JSONException {
        ArrayList<String> enames = client.getAllEmployeesNames();
        if (enames.size() != employeeNameBox.getItemCount()) {
            employeeNameBox.removeAllItems();
            for (String name : enames) {
                employeeNameBox.addItem(name);
            }
        }
    }

    public void enableTimeFields() {
        checkOutField.setEditable(true);
        checkInField.setEditable(true);
        submitClocking.setEnabled(true);
        checkDateField.setEditable(true);
    }

    public void insertNewClocking() throws IOException, ParseException, JSONException, DateFormatException {
        String cIn = checkInField.getText();
        String cOut = checkOutField.getText();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Time cInTime = new Time(formatter.parse(cIn).getTime());
        Time cOutTime = new Time(formatter.parse(cOut).getTime());
        boolean validTime = cOutTime.after(cInTime);

        String text = checkDateField.getText();
        long millis = System.currentTimeMillis();
        Date checkDate = new Date(millis);
        if (text.matches("\\d{4}-\\d{2}-\\d{2}") || text.equals("")) {
            if (!text.equals("")) {
                checkDate = Date.valueOf(text);
            }
        } else {
            throw new DateFormatException("Invalid date format:\nRequired: yyyy-MM-dd");
        }


        System.out.println(employeeNameBox.getSelectedItem().toString());
        if (validTime) {
            JSONObject object = client.JSONClocking(employeeNameBox.getSelectedItem().toString(), cInTime, cOutTime, checkDate);
            client.insertNewClocking(object);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid time: Make sure\nchech in is after the check out");
        }
    }

    public static void main(String[] args) {
        new ClientMenu();
    }
}
